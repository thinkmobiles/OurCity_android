package com.crmc.ourcity.dialog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.crmc.ourcity.R;
import com.crmc.ourcity.fragment.BaseFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.LoginLoader;
import com.crmc.ourcity.loader.RegisterLoader;
import com.crmc.ourcity.loader.StreetsLoader;
import com.crmc.ourcity.notification.RegistrationIntentService;
import com.crmc.ourcity.rest.request.streets.StreetsModel;
import com.crmc.ourcity.rest.responce.address.StreetsFull;
import com.crmc.ourcity.rest.responce.address.StreetsItem;
import com.crmc.ourcity.rest.responce.login.LoginResponse;
import com.crmc.ourcity.utils.SPManager;
import com.crmc.ourcity.view.EditTextStreetAutoComplete;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import static com.crmc.ourcity.global.Constants.LOADER_REGISTER_NEW_RESIDENT_ID;
import static com.crmc.ourcity.global.Constants.LOGIN_ID;

/**
 * Created by podo on 19.08.15.
 */
public class SignUpDialog extends BaseFragment implements View.OnClickListener, View.OnFocusChangeListener, LoaderManager.LoaderCallbacks {
    private View root;
    private EditText etLastName, etFirstName, etUsername, etPassword, etPhoneNumber, etMobileNumber, etEmail,
            etHouseNumber, etCityName;
    private EditTextStreetAutoComplete etStreet;
    private CheckBox chbGlobalNotifications, chbPersonalNotifications;
    private Button btnSignUpOrEdit;
    private int residentId;
    private StreetsItem[] streets;
    private int selectedStreetID = -1;
    ProgressDialog dialogLoading;


    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_JSON, "{\"getStreetListWrapper\":{\"clientId\":\"1\"," +
                "\"userName\":\"Webit\",\"password\":\"HdrMoked                                          \"}}");
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_ROUTE, "GetCRMCStreetList");
        getLoaderManager().initLoader(Constants.LOADER_STREETS_ID, bundle, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_dialog_sign_up, container, false);
        findUI(root);
        setListeners();
        dialogLoading = new ProgressDialog(getActivity());
        return root;
    }

    private void findUI(View _root) {
        etLastName = (EditText) _root.findViewById(R.id.etLastName_SUDF);
        etFirstName = (EditText) _root.findViewById(R.id.etFirstName_SUDF);
        etUsername = (EditText) _root.findViewById(R.id.etUsername_SUDF);
        etPassword = (EditText) _root.findViewById(R.id.etPassword_SUDF);
        etPhoneNumber = (EditText) _root.findViewById(R.id.etPhoneNumber_SUDF);
        etMobileNumber = (EditText) _root.findViewById(R.id.etMobileNumber_SUDF);
        etEmail = (EditText) _root.findViewById(R.id.etEmail_SUDF);
        etHouseNumber = (EditText) _root.findViewById(R.id.etHouseNumber_SUDF);
        etStreet = (EditTextStreetAutoComplete) _root.findViewById(R.id.etStreetName_SUDF);
        etCityName = (EditText) _root.findViewById(R.id.etCityName_SUDF);
        etCityName.setText(getResources().getString(R.string.app_name));
        chbGlobalNotifications = (CheckBox) _root.findViewById(R.id.chbGlobalNotifications_SUDF);
        chbPersonalNotifications = (CheckBox) _root.findViewById(R.id.chbPersonalNotifications_SUDF);
        btnSignUpOrEdit = (Button) _root.findViewById(R.id.btnSignUpOrEdit_SUDF);
    }

    private void setListeners() {
        btnSignUpOrEdit.setOnClickListener(this);
    }

    private Bundle createBundleForResident() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_CONSTANT_FIRST_NAME, etFirstName.getText().toString());
        bundle.putString(Constants.BUNDLE_CONSTANT_LAST_NAME, etLastName.getText().toString());
        bundle.putString(Constants.BUNDLE_CONSTANT_USER_NAME, etUsername.getText().toString());
        bundle.putString(Constants.BUNDLE_CONSTANT_PASSWORD, etPassword.getText().toString());
        bundle.putString(Constants.BUNDLE_CONSTANT_EMAIL, etEmail.getText().toString());
        bundle.putString(Constants.BUNDLE_CONSTANT_PHONE_NUMBER, etPhoneNumber.getText().toString());
        bundle.putString(Constants.BUNDLE_CONSTANT_MOBILE_NUMBER, etMobileNumber.getText().toString());
        bundle.putString(Constants.BUNDLE_CONSTANT_HOUSE_NUMBER, etHouseNumber.getText().toString());
        bundle.putInt(Constants.BUNDLE_CONSTANT_STREET_ID, selectedStreetID);
//        bundle.putInt(Constants.BUNDLE_CONSTANT_STREET_ID, 654);
        //TODO: uncomment when write street loader
        bundle.putBoolean(Constants.BUNDLE_CONSTANT_GLOBAL_NOTIFICATION_NEEDED, chbGlobalNotifications.isChecked());
        bundle.putBoolean(Constants.BUNDLE_CONSTANT_PERSONAL_NOTIFICATION_NEEDED, chbPersonalNotifications.isChecked());
        bundle.putString(Constants.BUNDLE_CONSTANT_AUTH_TOKEN, SPManager.getInstance(getActivity()).getAuthToken());
        bundle.putString(Constants.BUNDLE_CONSTANT_PUSH_TOKEN, SPManager.getInstance(getActivity()).getPushToken());
        //TODO: убрать hardcoded сити_ид
        bundle.putInt(Constants.BUNDLE_CONSTANT_CITY_ID, 1);

        return bundle;
    }

    @Override
    public void onClick(View v) {
        if (checkValidation()) {
           registerResident();
        }
    }

    private void registerResident() {
        Bundle args = createBundleForResident();

        if (residentId == -3) {
            getLoaderManager().restartLoader(LOADER_REGISTER_NEW_RESIDENT_ID, args, this);
        } else {
            getLoaderManager().initLoader(LOADER_REGISTER_NEW_RESIDENT_ID, args, this);
        }

    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
        if (resultCode != ConnectionResult.SUCCESS) {
            return false;
        }
        return true;
    }


    private boolean checkValidation() {
        boolean isValid = true;
        boolean isOptionalFieldValid = true;
        if (TextUtils.isEmpty(etFirstName.getText().toString())) {
            etFirstName.setError(getResources().getString(R.string.sign_up_dialog_error_text));
            isValid = false;
        }
        if (TextUtils.isEmpty(etLastName.getText().toString())) {
            etLastName.setError(getResources().getString(R.string.sign_up_dialog_error_text));
            isValid = false;
        }
        if (TextUtils.isEmpty(etUsername.getText().toString())) {
            etUsername.setError(getResources().getString(R.string.sign_up_dialog_error_text));
            isValid = false;
        }
        if (TextUtils.isEmpty(etPassword.getText().toString())) {
            etPassword.setError(getResources().getString(R.string.sign_up_dialog_error_text));
            isValid = false;
        }
        if (!TextUtils.isEmpty(etEmail.getText().toString())) {
            if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()) {
                etEmail.setError(getResources().getString(R.string.sign_up_dialog_incorrect_email));
                isValid = false;
            }
        } else {
            etEmail.setError(getResources().getString(R.string.sign_up_dialog_error_text));
            isValid = false;
        }

        if (!TextUtils.isEmpty(etMobileNumber.getText().toString())) {
            if (!Patterns.PHONE.matcher(etMobileNumber.getText().toString()).matches()) {
                etMobileNumber.setError(getResources().getString(R.string.sign_up_dialog_incorrect_phone_number));
                isValid = false;
            }
        } else {
            etMobileNumber.setError(getResources().getString(R.string.sign_up_dialog_error_text));
            isValid = false;
        }

        if (!TextUtils.isEmpty(etPhoneNumber.getText().toString())) {
            if (!Patterns.PHONE.matcher(etPhoneNumber.getText().toString()).matches()) {
                etPhoneNumber.setError(getResources().getString(R.string.sign_up_dialog_incorrect_phone_number));
                isOptionalFieldValid = false;
            }
        }

        if (TextUtils.isEmpty(etHouseNumber.getText().toString())) {
            etHouseNumber.setError(getResources().getString(R.string.sign_up_dialog_error_text));
            isValid = false;
        }

        String street = etStreet.getText().toString();
        for (int i = 0; i < streets.length; i++) {
            if (street.equals(streets[i].streetName)) {
                selectedStreetID = streets[i].streetId;
                break;
            }
        }

        if (selectedStreetID == -1) {
            etStreet.setError(getResources(). getString(R.string.sign_up_dialog_error_text));
            isValid = false;
        }

        return isValid && isOptionalFieldValid;
    }

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity
                .INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            hideKeyboard(v);
        }
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        Loader loader = null;
        dialogLoading.setMessage("Loading...");
        dialogLoading.show();
        switch (id) {
            case LOADER_REGISTER_NEW_RESIDENT_ID:
                loader = new RegisterLoader(getActivity(), args);
                break;
            case LOGIN_ID:
                loader = new LoginLoader(getActivity(), args);
                break;
            case Constants.LOADER_STREETS_ID:
                loader = new StreetsLoader(getActivity(), args);
                break;

        }
        return loader;
    }

    private void login() {
        Bundle bundle = createBundleForResident();
        getLoaderManager().initLoader(LOGIN_ID, bundle, this);

    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        if (dialogLoading.isShowing())
            dialogLoading.dismiss();
        switch (loader.getId()) {
            case LOADER_REGISTER_NEW_RESIDENT_ID:
                residentId = (Integer) data;

                if (residentId == -3) {
                    root.findViewById(R.id.tvErrorMessage_SUDF).setVisibility(View.VISIBLE);

                } else if (residentId > 0) {
                    SPManager.getInstance(getActivity()).setResidentId(residentId);
                    SPManager.getInstance(getActivity()).setUserName(etUsername.getText().toString());
                    SPManager.getInstance(getActivity()).setPassword(etPassword.getText().toString());

                    login();
                }
                break;
            case LOGIN_ID:
                LoginResponse dataLogin = (LoginResponse) data;
                SPManager.getInstance(getActivity()).setAuthToken(dataLogin.authToken);
                SPManager.getInstance(getActivity()).setCRMCUsername(dataLogin.crmcUsername);
                SPManager.getInstance(getActivity()).setCRMCPassword(dataLogin.crmcPassword);
                SPManager.getInstance(getActivity()).setIsLoggedStatus(true);
                if ((chbGlobalNotifications.isChecked() || chbPersonalNotifications.isChecked()) && checkPlayServices()) {
                    Intent intent = new Intent(getActivity(), RegistrationIntentService.class);
                    getActivity().startService(intent);
                }
                getActivity().finish();

                break;
            case Constants.LOADER_STREETS_ID:
                StreetsFull streetData = (StreetsFull) data;
                if (streetData != null) {
                    int numbersStreets = streetData.streetsList.size();
                    streets = new StreetsItem[numbersStreets];
                    String []streetNames = new String[numbersStreets];
                    for (int i = 0; i < numbersStreets; i++) {
                        streets[i] = streetData.streetsList.get(i);
                        streetNames[i] = streetData.streetsList.get(i).streetName;
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,
                            streetNames);
                    etStreet.setAdapter(adapter);
                } else {
                    popBackStack();
                }

                break;
        }

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
