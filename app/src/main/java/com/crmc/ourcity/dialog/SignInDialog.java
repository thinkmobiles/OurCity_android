package com.crmc.ourcity.dialog;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crmc.ourcity.R;
import com.crmc.ourcity.callback.OnActionDialogListener;
import com.crmc.ourcity.fragment.BaseFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.LoginLoader;
import com.crmc.ourcity.notification.RegistrationIntentService;
import com.crmc.ourcity.rest.responce.login.LoginResponse;
import com.crmc.ourcity.utils.SPManager;

/**
 * Created by podo on 19.08.15.
 */
public class SignInDialog extends BaseFragment implements LoaderManager.LoaderCallbacks<LoginResponse> {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnSignIn;
    private TextView tvSignUp;
    private OnActionDialogListener mCallback;

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);

        try {
            mCallback = (OnActionDialogListener) _activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(_activity.toString()
                    + " must implement OnActionDialogListener");
        }
    }

    @Override
    public void onDetach() {
        mCallback = null;
        super.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater _inflater, ViewGroup _container, Bundle _savedInstanceState) {
        if(SPManager.getInstance(getActivity()).getIsLoggedStatus()) {
            getActivity().finish();
        }
        View root = _inflater.inflate(R.layout.fragment_dialog_sign_in, _container, false);
        findUI(root);
        setListeners();
        return root;
    }

    private void findUI(View _root) {
        etUsername = (EditText) _root.findViewById(R.id.etUsername_SIDF);
        etPassword = (EditText) _root.findViewById(R.id.etPassword_SIDF);
        btnSignIn = (Button) _root.findViewById(R.id.btnSignIn_SIDF);
        tvSignUp = (TextView) _root.findViewById(R.id.tvSignUp_SIDF);
    }

    private void setListeners() {
        btnSignIn.setOnClickListener(handleClicks());
        tvSignUp.setOnClickListener(handleClicks());
        etPassword.setOnFocusChangeListener(handleFocusChanging());
        etUsername.setOnFocusChangeListener(handleFocusChanging());
    }

    @NonNull
    private View.OnClickListener handleClicks() {
        return v -> {
            switch (v.getId()) {
                case R.id.btnSignIn_SIDF:
                    hideKeyboard(getActivity());
                    if (checkValidation()) {
                        Bundle bundle = createBundleForResident();
                        getLoaderManager().restartLoader(1, bundle, this);
                    }
                    break;
                case R.id.tvSignUp_SIDF:
                    mCallback.onActionDialogSelected(DialogType.REGISTER);
                    hideKeyboard(getActivity());
                    break;
            }
        };
    }

    @NonNull
    private View.OnFocusChangeListener handleFocusChanging() {
        return (v, hasFocus) -> {
            if (!hasFocus) hideKeyboard(getActivity());
        };
    }

    private Bundle createBundleForResident() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_CONSTANT_USER_NAME, etUsername.getText().toString());
        bundle.putString(Constants.BUNDLE_CONSTANT_PASSWORD, etPassword.getText().toString());
        bundle.putInt(Constants.BUNDLE_CONSTANT_CITY_ID, getResources().getInteger(R.integer.city_id));
        bundle.putString(Constants.BUNDLE_CONSTANT_PUSH_TOKEN, SPManager.getInstance(getActivity()).getPushToken());
        return bundle;
    }

    private boolean checkValidation() {
        boolean isValid = true;

        if (TextUtils.isEmpty(etUsername.getText().toString())) {
            etUsername.setError(getResources().getString(R.string.sign_up_dialog_error_text));
            isValid = false;
        }
        if (TextUtils.isEmpty(etPassword.getText().toString())) {
            etPassword.setError(getResources().getString(R.string.sign_up_dialog_error_text));
            isValid = false;
        }

        return isValid;
    }

    @Override
    public Loader<LoginResponse> onCreateLoader(int _id, Bundle _args) {
        return new LoginLoader(getActivity(), _args);
    }

    @Override
    public void onLoadFinished(Loader<LoginResponse> _loader, LoginResponse _data) {
        if (_data != null) {
            if (_data.authToken != null) {
                SPManager.getInstance(getActivity()).setAuthToken(_data.authToken);
                SPManager.getInstance(getActivity()).setResidentId(_data.residentId);
                SPManager.getInstance(getActivity()).setCRMCUsername(_data.crmcUsername);
                SPManager.getInstance(getActivity()).setCRMCPassword(_data.crmcPassword);
                SPManager.getInstance(getActivity()).setIsLoggedStatus(true);
                getActivity().startService(new Intent(getActivity(), RegistrationIntentService.class));
                getActivity().finish();
            } else {
                Toast.makeText(getActivity(), R.string.incorrect_credentials, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), R.string.connection_error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<LoginResponse> _loader) {}
}
