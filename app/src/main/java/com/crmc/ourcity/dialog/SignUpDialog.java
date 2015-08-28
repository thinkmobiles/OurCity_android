package com.crmc.ourcity.dialog;

import android.app.Activity;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.crmc.ourcity.R;
import com.crmc.ourcity.fragment.BaseFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.LoginLoader;
import com.crmc.ourcity.loader.RegisterLoader;
import com.crmc.ourcity.rest.responce.login.LoginResponse;
import com.crmc.ourcity.utils.SPManager;
import com.crmc.ourcity.view.EditTextStreetAutoComplete;

/**
 * Created by podo on 19.08.15.
 */
public class SignUpDialog extends BaseFragment implements View.OnClickListener, View.OnFocusChangeListener {

    private EditText etLastName, etFirstName, etUsername,
                     etPassword, etPhoneNumber, etMobileNumber,
                     etEmail, etHouseNumber, etCityName;
    private EditTextStreetAutoComplete etStreet;
    private CheckBox chbGlobalNotifications, chbPersonalNotifications;
    private Button btnSignUpOrEdit;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dialog_sign_up, container, false);
        findUI(root);
        setListeners();
        return root;
    }

    private void findUI(View _root) {
        etLastName = (EditText) _root. findViewById(R.id.etLastName_SUDF);
        etFirstName = (EditText) _root. findViewById(R.id.etFirstName_SUDF);
        etUsername = (EditText) _root. findViewById(R.id.etUsername_SUDF);
        etPassword = (EditText) _root. findViewById(R.id.etPassword_SUDF);
        etPhoneNumber = (EditText) _root. findViewById(R.id.etPhoneNumber_SUDF);
        etMobileNumber = (EditText) _root. findViewById(R.id.etMobileNumber_SUDF);
        etEmail = (EditText) _root. findViewById(R.id.etEmail_SUDF);
        etHouseNumber = (EditText) _root. findViewById(R.id.etHouseNumber_SUDF);
        etStreet = (EditTextStreetAutoComplete) _root. findViewById(R.id.etStreetName_SUDF);
        etCityName = (EditText) _root. findViewById(R.id.etCityName_SUDF);
        etCityName.setText(getResources().getString(R.string.app_name));
        chbGlobalNotifications = (CheckBox) _root. findViewById(R.id.chbGlobalNotifications_SUDF);
        chbPersonalNotifications = (CheckBox) _root. findViewById(R.id.chbPersonalNotifications_SUDF);
        btnSignUpOrEdit = (Button) _root .findViewById(R.id.btnSignUpOrEdit_SUDF);
    }

    private void setListeners() {
        btnSignUpOrEdit.setOnClickListener(this);
        etFirstName.setOnFocusChangeListener(this);
        etLastName.setOnFocusChangeListener(this);
        etUsername.setOnFocusChangeListener(this);
        etPassword.setOnFocusChangeListener(this);
        etPhoneNumber.setOnFocusChangeListener(this);
        etMobileNumber.setOnFocusChangeListener(this);
        etEmail.setOnFocusChangeListener(this);
        etHouseNumber.setOnFocusChangeListener(this);
        etStreet.setOnFocusChangeListener(this);
        etCityName.setOnFocusChangeListener(this);

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
       // bundle.putInt(Constants.BUNDLE_CONSTANT_STREET_ID, Integer.parseInt(etStreet.getText().toString()));
        bundle.putInt(Constants.BUNDLE_CONSTANT_STREET_ID, 654);
        //TODO: uncomment when write street loader
        bundle.putBoolean(Constants.BUNDLE_CONSTANT_GLOBAL_NOTIFICATION_NEEDED, chbGlobalNotifications.isChecked());
        bundle.putBoolean(Constants.BUNDLE_CONSTANT_PERSONAL_NOTIFICATION_NEEDED, chbPersonalNotifications.isChecked());
        bundle.putString(Constants.BUNDLE_CONSTANT_AUTH_TOKEN, SPManager.getInstance(getActivity()).getAuthToken());
        //TODO: убрать hardcoded сити_ид
        bundle.putInt(Constants.BUNDLE_CONSTANT_CITY_ID, 1);

        return bundle;
    }

    @Override
    public void onClick(View v) {
        if (checkValidation()) {
            Bundle args = createBundleForResident();
            getLoaderManager().initLoader(1, args, mRegisterCallback);
        }
    }

    private boolean checkValidation() {
        boolean isValid = true;
        boolean isOptionalFieldValid = true;
        if (TextUtils.isEmpty(etFirstName.getText().toString())) {
            etFirstName.setError(getResources(). getString(R.string.sign_up_dialog_error_text));
            isValid = false;
        }
        if (TextUtils.isEmpty(etLastName.getText().toString())) {
            etLastName.setError(getResources(). getString(R.string.sign_up_dialog_error_text));
            isValid = false;
        }
        if (TextUtils.isEmpty(etUsername.getText().toString())) {
            etUsername.setError(getResources(). getString(R.string.sign_up_dialog_error_text));
            isValid = false;
        }
        if (TextUtils.isEmpty(etPassword.getText().toString())) {
            etPassword.setError(getResources(). getString(R.string.sign_up_dialog_error_text));
            isValid = false;
        }
        if (!TextUtils.isEmpty(etEmail.getText().toString())) {
            if(!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()) {
                etEmail.setError(getResources().getString(R.string.sign_up_dialog_incorrect_email));
                isValid = false;
            }
        } else {
            etEmail.setError(getResources(). getString(R.string.sign_up_dialog_error_text));
            isValid = false;
        }

        if (!TextUtils.isEmpty(etMobileNumber.getText().toString())) {
            if (!Patterns.PHONE.matcher(etMobileNumber.getText().toString()).matches()) {
                etMobileNumber.setError(getResources().getString(R.string.sign_up_dialog_incorrect_phone_number));
                isValid = false;
            }
        } else {
            etMobileNumber.setError(getResources(). getString(R.string.sign_up_dialog_error_text));
            isValid = false;
        }

        if (!TextUtils.isEmpty(etPhoneNumber.getText().toString())) {
            if (!Patterns.PHONE.matcher(etPhoneNumber.getText().toString()).matches()) {
                etPhoneNumber.setError(getResources().getString(R.string.sign_up_dialog_incorrect_phone_number));
                isOptionalFieldValid = false;
            }
        }

        if (TextUtils.isEmpty(etHouseNumber.getText().toString())) {
            etHouseNumber.setError(getResources(). getString(R.string.sign_up_dialog_error_text));
            isValid = false;
        }
//        if (TextUtils.isEmpty(etStreet.getText().toString())) {
//            etStreet.setError(getResources(). getString(R.string.sign_up_dialog_error_text));
//            isValid = false;
//        } TODO: uncomment when write logic for street loader

        return isValid && isOptionalFieldValid;
    }

    private LoaderManager.LoaderCallbacks<Integer> mRegisterCallback = new LoaderManager.LoaderCallbacks<Integer>() {
        @Override
        public Loader<Integer> onCreateLoader(int id, Bundle args) {
            return new RegisterLoader(getActivity(), args);
        }

        @Override
        public void onLoadFinished(Loader<Integer> loader, Integer residentId) {

            if (residentId > 0) {
            SPManager.getInstance(getActivity()).setResidentId(residentId);
            SPManager.getInstance(getActivity()).setUserName(etUsername.getText().toString());
            SPManager.getInstance(getActivity()).setPassword(etPassword.getText().toString());

                Bundle bundle = createBundleForResident();

            getLoaderManager().initLoader(12, bundle, mLoginCallback);

            }

        }

        @Override
        public void onLoaderReset(Loader<Integer> loader) {

        }
    };

    private LoaderManager.LoaderCallbacks<LoginResponse> mLoginCallback = new LoaderManager.LoaderCallbacks<LoginResponse>() {
        @Override
        public Loader<LoginResponse> onCreateLoader(int id, Bundle args) {
            return new LoginLoader(getActivity(), args);
        }

        @Override
        public void onLoadFinished(Loader<LoginResponse> loader, LoginResponse data) {
            SPManager.getInstance(getActivity()).setAuthToken(data.authToken);
            SPManager.getInstance(getActivity()).setIsLoggedStatus(true);
            getActivity().finish();
        }

        @Override
        public void onLoaderReset(Loader<LoginResponse> loader) {

        }
    };

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getActivity(). getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(!hasFocus) {
            hideKeyboard(v);
        }
    }
}
