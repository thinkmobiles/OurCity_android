package com.crmc.ourcity.dialog;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;

import com.annimon.stream.Optional;
import com.annimon.stream.Stream;
import com.crmc.ourcity.R;
import com.crmc.ourcity.fragment.BaseFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.LoginLoader;
import com.crmc.ourcity.loader.RegisterLoader;
import com.crmc.ourcity.loader.ResidentInfoLoader;
import com.crmc.ourcity.loader.StreetsLoader;
import com.crmc.ourcity.loader.UpdateResidentInfoLoader;
import com.crmc.ourcity.notification.RegistrationIntentService;
import com.crmc.ourcity.rest.request.registration.ResidentDetails;
import com.crmc.ourcity.rest.responce.address.StreetsFull;
import com.crmc.ourcity.rest.responce.address.StreetsItem;
import com.crmc.ourcity.rest.responce.login.LoginResponse;
import com.crmc.ourcity.utils.SPManager;
import com.crmc.ourcity.view.EditTextStreetAutoComplete;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import static com.crmc.ourcity.global.Constants.LOADER_GET_RESIDENT_INFO_ID;
import static com.crmc.ourcity.global.Constants.LOADER_LOGIN_ID;
import static com.crmc.ourcity.global.Constants.LOADER_REGISTER_NEW_RESIDENT_ID;
import static com.crmc.ourcity.global.Constants.LOADER_UPDATE_RESIDENT_INFO;

/**
 * Created by podo on 19.08.15.
 */

//TODO: CHECk ON SERVER global notification value
public class SignUpDialog extends BaseFragment implements View.OnFocusChangeListener, LoaderManager.LoaderCallbacks {

    private View root;
    private EditText etLastName, etFirstName, etUsername, etPassword, etPhoneNumber, etMobileNumber,
            etEmail, etHouseNumber, etCityName;
    private EditTextStreetAutoComplete etStreet;
    private CheckBox chbGlobalNotifications, chbPersonalNotifications;
    private Button btnSignUpOrEdit;
    private int residentId;
    private StreetsItem[] streets;
    private int selectedStreetID;
    private ProgressDialog dialogLoading;
    private boolean isEditable;
    private Handler mHandler;

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        int cityNumber = getResources().getInteger(R.integer.city_id);
        String crmcUsername = SPManager.getInstance(getActivity()).getCRMCUsername();
        String crmcPassword = SPManager.getInstance(getActivity()).getCRMCPassword();
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_JSON, "{\"getStreetListWrapper\":{\"clientId\":\"" + cityNumber + "\"," +
                "\"userName\":\"" + crmcUsername + "\",\"password\":\"" + crmcPassword + "\"}}");
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_ROUTE, "GetCRMCStreetList");
        getLoaderManager().initLoader(Constants.LOADER_STREETS_ID, bundle, this);

    }

    @Override
    public void onCreate(@Nullable Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        isEditable = getArguments().getBoolean(Constants.BUNDLE_CONSTANT_EDITABLE_RESIDENT, false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater _inflater, ViewGroup _container, Bundle _savedInstanceState) {
        root = _inflater.inflate(R.layout.fragment_dialog_sign_up, _container, false);
        findUI(root);
        setListeners();
        mHandler = new Handler(getActivity().getMainLooper());
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
        ScrollView view = (ScrollView) _root.findViewById(R.id.scrollView);
        view.setVerticalScrollBarEnabled(false);
        view.setHorizontalScrollBarEnabled(false);
    }

    private void setListeners() {
        btnSignUpOrEdit.setOnClickListener(handleClick());
        etStreet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etStreet.getText().length() > 0) {
                    etStreet.setError(null);
                }
            }
        });
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
        bundle.putBoolean(Constants.BUNDLE_CONSTANT_GLOBAL_NOTIFICATION_NEEDED, chbGlobalNotifications.isChecked());
        bundle.putBoolean(Constants.BUNDLE_CONSTANT_PERSONAL_NOTIFICATION_NEEDED, chbPersonalNotifications.isChecked());
        bundle.putString(Constants.BUNDLE_CONSTANT_AUTH_TOKEN, SPManager.getInstance(getActivity()).getAuthToken());
        bundle.putString(Constants.BUNDLE_CONSTANT_PUSH_TOKEN, SPManager.getInstance(getActivity()).getPushToken());
        bundle.putInt(Constants.BUNDLE_CONSTANT_CITY_ID, getResources().getInteger(R.integer.city_id));

        return bundle;
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
                isOptionalFieldValid = false;
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

        if (etHouseNumber.isEnabled() & TextUtils.isEmpty(etHouseNumber.getText().toString())) {
            etHouseNumber.setError(getResources().getString(R.string.sign_up_dialog_error_text));
            isValid = false;
        }

        getSelectedStreetId();

        if (etStreet.isEnabled() & selectedStreetID == -1) {
            etStreet.setError(getResources().getString(R.string.sign_up_dialog_error_text));
            isValid = false;
        }

        return isValid && isOptionalFieldValid;
    }

    @Override
    public void onFocusChange(View _v, boolean _hasFocus) {
        if (!_hasFocus) {
            hideKeyboard(getActivity());
        }
    }

    @Override
    public Loader onCreateLoader(int _id, Bundle _args) {
        Loader loader = null;
        dialogLoading.setMessage(getResources().getString(R.string.loading_string_sign_up_dialog));
        dialogLoading.show();
        switch (_id) {

            case LOADER_GET_RESIDENT_INFO_ID:
                loader = new ResidentInfoLoader(getActivity(), _args);
                break;

            case LOADER_UPDATE_RESIDENT_INFO:
                loader = new UpdateResidentInfoLoader(getActivity(), _args);
                break;

            case LOADER_REGISTER_NEW_RESIDENT_ID:
                loader = new RegisterLoader(getActivity(), _args);
                break;

            case LOADER_LOGIN_ID:
                loader = new LoginLoader(getActivity(), _args);
                break;

            case Constants.LOADER_STREETS_ID:
                loader = new StreetsLoader(getActivity(), _args);
                break;
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader _loader, Object _data) {
        if (dialogLoading.isShowing())
            dialogLoading.dismiss();
        switch (_loader.getId()) {
            case LOADER_REGISTER_NEW_RESIDENT_ID:
                registerResidentAndAutologin((Integer) _data);
                break;

            case LOADER_UPDATE_RESIDENT_INFO:
                boolean result = (Boolean) _data;

                if (result) {
                    startLoginLoader();
                } else {
                    //TODO: do something
                }
                break;
            case LOADER_LOGIN_ID:
                mHandler.postAtFrontOfQueue(() -> {
                    LoginResponse dataLogin = (LoginResponse) _data;
                    SPManager.getInstance(getActivity()).setAuthToken(dataLogin.authToken);
                    SPManager.getInstance(getActivity()).setCRMCUsername(dataLogin.crmcUsername);
                    SPManager.getInstance(getActivity()).setCRMCPassword(dataLogin.crmcPassword);
                    SPManager.getInstance(getActivity()).setIsLoggedStatus(true);
                    if ((chbGlobalNotifications.isChecked() || chbPersonalNotifications.isChecked()) && checkPlayServices()) {
                        Intent intent = new Intent(getActivity(), RegistrationIntentService.class);
                        getActivity().startService(intent);
                    }
                    hideKeyboard(getActivity());
                    popBackStack();

                });

                break;
            case Constants.LOADER_STREETS_ID:
                StreetsFull streetData = (StreetsFull) _data;
                int numbersStreets = 0;

                if (streetData != null) {
                    if (streetData.streetsList != null) {
                         numbersStreets = streetData.streetsList.size();
                    }
                    if (numbersStreets == 0) {
                        etStreet.setEnabled(false);
                        etHouseNumber.setEnabled(false);
                    } else {
                        etStreet.setEnabled(true);
                        etHouseNumber.setEnabled(true);
                    }
                    streets = new StreetsItem[numbersStreets];
                    String[] streetNames = new String[numbersStreets];
                    for (int i = 0; i < numbersStreets; i++) {
                        streets[i] = streetData.streetsList.get(i);
                        streetNames[i] = streetData.streetsList.get(i).streetName;
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,
                            streetNames);
                    etStreet.setAdapter(adapter);

                    if (isEditable) {
                        etUsername.setEnabled(false);
                        etPassword.setEnabled(false);
                        etUsername.setTextColor(Color.GRAY);
                        etPassword.setTextColor(Color.GRAY);
                        Bundle args = new Bundle();
                        args.putInt(Constants.BUNDLE_CONSTANT_CITY_NUMBER, getResources().getInteger(R.integer.city_id));
                        args.putInt(Constants.BUNDLE_CONSTANT_RESIDENT_ID, SPManager.getInstance(getActivity()).getResidentId());
                        getLoaderManager().initLoader(Constants.LOADER_GET_RESIDENT_INFO_ID, args, this);
                    } else {
                        etUsername.setEnabled(true);
                        etPassword.setEnabled(true);
                    }

                } else {
                    popBackStack();
                }
                break;

            case Constants.LOADER_GET_RESIDENT_INFO_ID:
                if (_data != null) {
                    ResidentDetails residentInfo = (ResidentDetails) _data;
                    setResidentInfo(residentInfo);
                }
                break;
        }
    }

    private void registerResidentAndAutologin(Integer _data) {
        residentId = _data;

        if (residentId == -3) {
            root.findViewById(R.id.tvErrorMessage_SUDF).setVisibility(View.VISIBLE);

        } else if (residentId > 0) {
            SPManager.getInstance(getActivity()).setResidentId(residentId);
            SPManager.getInstance(getActivity()).setUserName(etUsername.getText().toString());
            SPManager.getInstance(getActivity()).setPassword(etPassword.getText().toString());

            startLoginLoader();
        }
    }

    private void startRegisterResidentLoader() {
        Bundle args = createBundleForResident();

        if (residentId == -3) {
            getLoaderManager().restartLoader(LOADER_REGISTER_NEW_RESIDENT_ID, args, this);
        } else {
            getLoaderManager().initLoader(LOADER_REGISTER_NEW_RESIDENT_ID, args, this);
        }
    }

    private void startLoginLoader() {
        Bundle bundle = createBundleForResident();
        getLoaderManager().initLoader(LOADER_LOGIN_ID, bundle, this);
    }

    private void startUpdateResidentLoader() {
        getSelectedStreetId();
        Bundle args = createBundleForResident();
        args.putInt(Constants.BUNDLE_CONSTANT_RESIDENT_ID, SPManager.getInstance(getActivity()).getResidentId());
        getLoaderManager().restartLoader(LOADER_UPDATE_RESIDENT_INFO, args, this);
    }


    private void setResidentInfo(ResidentDetails _residentInfo) {
        etLastName.setText(_residentInfo.lastName);
        etFirstName.setText(_residentInfo.firstName);
        etUsername.setText(_residentInfo.userName);
        etPassword.setText(_residentInfo.password);
        etPhoneNumber.setText(_residentInfo.phoneNumber != null ? _residentInfo.phoneNumber : "");
        etMobileNumber.setText(_residentInfo.mobileNumber);
        etEmail.setText(_residentInfo.email);
        etHouseNumber.setText(_residentInfo.houseNumber);
        setStreetName(_residentInfo);
        etCityName.setText(getResources().getString(R.string.app_name));
        chbGlobalNotifications.setChecked(_residentInfo.isGetGlobalNotification);
        chbPersonalNotifications.setChecked(_residentInfo.isGetPersonalNotification);
    }

    private void getSelectedStreetId() {
        String street = etStreet.getText().toString();
        Optional<Integer> set = Stream.of(streets)
                .filter(item -> street.equals(item.streetName))
                .map(item -> selectedStreetID = item.streetId).findFirst();

        if (set.isPresent()) {
            selectedStreetID = set.get();
        } else {
            selectedStreetID = -1;
        }
    }

    private void setStreetName(ResidentDetails _residentInfo) {
        Optional<String> streetName = Stream.of(streets).filter(item -> _residentInfo.streetId.equals(item.streetId))
                .map(item -> item.streetName).findFirst();
        if(streetName.isPresent()) {
            etStreet.setText(streetName.get());
        } else {
            etStreet.setText("");
        }
    }

    @Override
    public void onLoaderReset(Loader _loader) {
    }

    @NonNull
    private View.OnClickListener handleClick() {
        return v -> {
            if (checkValidation()) {
                if (isEditable) {
                    startUpdateResidentLoader();
                } else {
                    startRegisterResidentLoader();
                }
            }
        };
    }
}
