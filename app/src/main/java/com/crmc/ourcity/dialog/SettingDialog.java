package com.crmc.ourcity.dialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.crmc.ourcity.R;
import com.crmc.ourcity.callback.OnActionDialogListener;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.LogoutLoader;
import com.crmc.ourcity.notification.RegistrationIntentService;
import com.crmc.ourcity.utils.SPManager;

/**
 * Created by podo on 21.07.15.
 */
public class SettingDialog extends BaseFourStatesFragment implements View.OnClickListener {

    private RelativeLayout register;
    private RelativeLayout confirmation;
    private RelativeLayout logout;
    private RelativeLayout hotCalls;



    private OnActionDialogListener mCallback;

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);

        try{
            mCallback = (OnActionDialogListener) _activity;
        } catch (ClassCastException _e) {
            throw new ClassCastException(_activity.toString()
                            + " must implement OnActionDialogListener");
        }
    }

    @Override
    protected void initViews() {
        register = findView(R.id.rlSignUp_SetDFrgmt);
        confirmation = findView(R.id.confirmation);
        logout = findView(R.id.rlLogout_FDS);
        hotCalls = findView(R.id.rlHotCalls_SetDFrgmt);
        boolean isFromMainActivity = getActivity().getIntent().getBooleanExtra(Constants.IS_FROM_MAIN_ACTIVITY, false);
        if (isFromMainActivity) {
            if (SPManager.getInstance(getActivity()).getIsLoggedStatus()) {
                logout.setVisibility(View.VISIBLE);
            } else {
                logout.setVisibility(View.GONE);
            }
        }
        showContent();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_dialog_settings;
    }

    @Override
    protected void setListeners() {
        register.setOnClickListener(this);
        confirmation.setOnClickListener(this);
        logout.setOnClickListener(this);
        hotCalls.setOnClickListener(this);
    }

    @Override
    public void onClick(View _view) {
        switch (_view.getId()){
            case R.id.rlHotCalls_SetDFrgmt:
                mCallback.onActionDialogSelected(DialogType.HOT_CALLS_EDITABLE);
                break;
            case R.id.rlSignUp_SetDFrgmt:
                mCallback.onActionDialogSelected(DialogType.REGISTER);
                break;
            case R.id.confirmation:
                Toast.makeText(getActivity().getApplicationContext(), "Clicked confirmation", Toast.LENGTH_SHORT).show();
                mCallback.onActionDialogSelected(DialogType.CONFIRMATION);
                break;
            case R.id.rlLogout_FDS:
                Bundle bundle = new Bundle();
                bundle.putString(Constants.BUNDLE_CONSTANT_AUTH_TOKEN, SPManager.getInstance(getActivity()).getAuthToken());
                getLoaderManager().initLoader(1, bundle, mLogoutCallback);
                break;
//            case R.id.button2:
//                break;
//            case R.id.button3:
//                break;
        }
    }

    private LoaderManager.LoaderCallbacks<Boolean> mLogoutCallback = new LoaderManager.LoaderCallbacks<Boolean>() {
        @Override
        public Loader<Boolean> onCreateLoader(int id, Bundle args) {
            return new LogoutLoader(getActivity(), args);
        }

        @Override
        public void onLoadFinished(Loader<Boolean> loader, Boolean data) {
            SPManager.getInstance(getActivity()).setIsLoggedStatus(false);
            if (data) {

                SPManager.getInstance(getActivity()).deleteResidentInformation();
                getActivity().stopService(new Intent(getActivity(), RegistrationIntentService.class));
                logout.setVisibility(View.GONE);
            }
        }

        @Override
        public void onLoaderReset(Loader<Boolean> loader) {

        }
    };

    @Override
    public void onRetryClick() {

    }
}
