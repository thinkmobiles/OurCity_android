package com.crmc.ourcity.dialog;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crmc.ourcity.R;
import com.crmc.ourcity.callback.OnActionDialogListener;
import com.crmc.ourcity.fragment.BaseFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.LoginLoader;
import com.crmc.ourcity.rest.responce.login.LoginResponse;
import com.crmc.ourcity.utils.SPManager;

/**
 * Created by podo on 19.08.15.
 */
public class SignInDialog extends BaseFragment implements View.OnClickListener, LoaderManager.LoaderCallbacks<LoginResponse>, View.OnFocusChangeListener {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnSignIn;
    private TextView tvSignUp;
    private OnActionDialogListener mCallback;

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);

        try{
            mCallback = (OnActionDialogListener) _activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(_activity.toString()
                    + " must implement OnActionDialogListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dialog_sign_in, container, false);
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
        btnSignIn.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
        etPassword.setOnFocusChangeListener(this);
        etUsername.setOnFocusChangeListener(this);
    }

    private Bundle createBundleForResident() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_CONSTANT_USER_NAME, etUsername.getText().toString());
        bundle.putString(Constants.BUNDLE_CONSTANT_PASSWORD, etPassword.getText().toString());
        bundle.putString(Constants.BUNDLE_CONSTANT_PUSH_TOKEN, SPManager.getInstance(getActivity()).getPushToken());
        return bundle;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignIn_SIDF :
                if(checkValidation()) {
                    Bundle bundle = createBundleForResident();
                getLoaderManager().initLoader(1, bundle, this);
                }
                break;
            case R.id.tvSignUp_SIDF :
                mCallback.onActionDialogSelected(DialogType.REGISTER);
                break;
        }
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
    public Loader<LoginResponse> onCreateLoader(int id, Bundle args) {
        return new LoginLoader(getActivity(), args);
    }

    @Override
    public void onLoadFinished(Loader<LoginResponse> loader, LoginResponse data) {
        SPManager.getInstance(getActivity()).setAuthToken(data.authToken);
        SPManager.getInstance(getActivity()).setResidentId(data.residentId);
        SPManager.getInstance(getActivity()).setIsLogInStatus(true);
        getActivity().finish();
    }

    @Override
    public void onLoaderReset(Loader<LoginResponse> loader) {

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(!hasFocus) {
            hideKeyboard(v);
        }
    }

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getActivity(). getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
