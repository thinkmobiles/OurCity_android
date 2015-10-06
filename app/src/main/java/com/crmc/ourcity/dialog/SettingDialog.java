package com.crmc.ourcity.dialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
public class SettingDialog extends BaseFourStatesFragment {

    private RelativeLayout login;
    private RelativeLayout logout;
    private RelativeLayout hotCalls;
    private RelativeLayout interestingAreas;
    private RelativeLayout updateResidentInfo;
    private OnActionDialogListener mCallback;

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);

        try {
            mCallback = (OnActionDialogListener) _activity;
        } catch (ClassCastException _e) {
            throw new ClassCastException(_activity.toString()
                    + " must implement OnActionDialogListener");
        }
    }

    @Override
    public void onDetach() {
        mCallback = null;
        super.onDetach();
    }

    @Override
    protected void initViews() {
        login = findView(R.id.rlSignIn_SetDFrgmt);
        logout = findView(R.id.rlLogout_FDS);
        hotCalls = findView(R.id.rlHotCalls_SetDFrgmt);
        interestingAreas = findView(R.id.rlInterestAreas_SetDFrgmt);
        updateResidentInfo = findView(R.id.rlUpdateResidentInfo_SetDFrgmt);
        boolean isFromMainActivity = getActivity().getIntent().getBooleanExtra(Constants.IS_FROM_MAIN_ACTIVITY, false);
        if (isFromMainActivity) {
            if (SPManager.getInstance(getActivity()).getIsLoggedStatus()) {
                logout.setVisibility(View.VISIBLE);
                login.setVisibility(View.GONE);
                interestingAreas.setVisibility(View.VISIBLE);
                updateResidentInfo.setVisibility(View.VISIBLE);
            } else {
                logout.setVisibility(View.GONE);
                interestingAreas.setVisibility(View.GONE);
                updateResidentInfo.setVisibility(View.GONE);
            }
        } else {
            if (SPManager.getInstance(getActivity()).getIsLoggedStatus()) {
                login.setVisibility(View.GONE);
                interestingAreas.setVisibility(View.VISIBLE);
                updateResidentInfo.setVisibility(View.VISIBLE);
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
        login.setOnClickListener(handleClicks());
        logout.setOnClickListener(handleClicks());
        hotCalls.setOnClickListener(handleClicks());
        interestingAreas.setOnClickListener(handleClicks());
        updateResidentInfo.setOnClickListener(handleClicks());
    }

    @NonNull
    private View.OnClickListener handleClicks() {
        return v -> {
            switch (v.getId()) {
                case R.id.rlHotCalls_SetDFrgmt:
                    mCallback.onActionDialogSelected(DialogType.HOT_CALLS_EDITABLE);
                    break;
                case R.id.rlSignIn_SetDFrgmt:
                    mCallback.onActionDialogSelected(DialogType.LOGIN);
                    break;
                case R.id.rlLogout_FDS:
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.BUNDLE_CONSTANT_AUTH_TOKEN, SPManager.getInstance(getActivity()).getAuthToken());
                    getLoaderManager().initLoader(1, bundle, mLogoutCallback);
                    break;
                case R.id.rlInterestAreas_SetDFrgmt:
                    mCallback.onActionDialogSelected(DialogType.INTEREST_AREAS);
                    break;
                case R.id.rlUpdateResidentInfo_SetDFrgmt:
                    mCallback.onActionDialogSelected(DialogType.UPDATE_RESIDENT_INFO);
                    break;
            }
        };
    }

    private LoaderManager.LoaderCallbacks<Boolean> mLogoutCallback = new LoaderManager.LoaderCallbacks<Boolean>() {
        @Override
        public Loader<Boolean> onCreateLoader(int id, Bundle args) {
            return new LogoutLoader(getActivity(), args);
        }

        @Override
        public void onLoadFinished(Loader<Boolean> loader, Boolean data) {
            if (data) {
                SPManager.getInstance(getActivity()).setIsLoggedStatus(false);
                SPManager.getInstance(getActivity()).deleteResidentInformation();
                getActivity().stopService(new Intent(getActivity(), RegistrationIntentService.class));
                logout.setVisibility(View.GONE);
                login.setVisibility(View.VISIBLE);
                interestingAreas.setVisibility(View.GONE);
                updateResidentInfo.setVisibility(View.GONE);
            } else {
                Toast.makeText(getActivity(), R.string.connection_error, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onLoaderReset(Loader<Boolean> loader) {}
    };

    @Override
    public void onRetryClick() {}
}
