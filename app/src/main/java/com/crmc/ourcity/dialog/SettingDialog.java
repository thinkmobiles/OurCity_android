package com.crmc.ourcity.dialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.crmc.ourcity.R;
import com.crmc.ourcity.activity.Application;
import com.crmc.ourcity.callback.OnActionDialogListener;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.LogoutLoader;
import com.crmc.ourcity.notification.RegistrationIntentService;
import com.crmc.ourcity.utils.SPManager;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by podo on 21.07.15.
 */
public class SettingDialog extends BaseFourStatesFragment {

    private RelativeLayout login;
    private RelativeLayout logout;
    private RelativeLayout hotCalls;
    private RelativeLayout interestingAreas;
    private RelativeLayout updateResidentInfo;
    private RelativeLayout visibleTickets;
    private OnActionDialogListener mCallback;
    private FragmentActivity mActivity;
    int showInterestAreas;
    int showHotCalls;

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

        mActivity = getActivity();

        showHotCalls = SPManager.getInstance(mActivity).getShowHotCalls();
        showInterestAreas = SPManager.getInstance(mActivity).getShowInterestAreas();

        login = findView(R.id.rlSignIn_SetDFrgmt);
        logout = findView(R.id.rlLogout_FDS);
        hotCalls = findView(R.id.rlHotCalls_SetDFrgmt);
        interestingAreas = findView(R.id.rlInterestAreas_SetDFrgmt);
        updateResidentInfo = findView(R.id.rlUpdateResidentInfo_SetDFrgmt);
        visibleTickets = findView(R.id.rlVisibleTickets_SetDFrgmt);

//        boolean isFromMainActivity = mActivity.getIntent().getBooleanExtra(Constants.IS_FROM_MAIN_ACTIVITY, false);
//        if (isFromMainActivity) {
            if (SPManager.getInstance(mActivity).getIsLoggedStatus()) {
                logout.setVisibility(View.VISIBLE);
                login.setVisibility(View.GONE);
                interestingAreas.setVisibility(View.VISIBLE);
                updateResidentInfo.setVisibility(View.VISIBLE);
                visibleTickets.setVisibility(View.VISIBLE);
            } else {
                logout.setVisibility(View.GONE);
                interestingAreas.setVisibility(View.GONE);
                updateResidentInfo.setVisibility(View.GONE);
                visibleTickets.setVisibility(View.GONE);
            }
//        } else {
//            if (SPManager.getInstance(mActivity).getIsLoggedStatus()) {
//                login.setVisibility(View.GONE);
//                visibleTickets.setVisibility(View.VISIBLE);
//                interestingAreas.setVisibility(View.VISIBLE);
//                updateResidentInfo.setVisibility(View.VISIBLE);
//            }
//        }

       if(showInterestAreas == 0) {
           interestingAreas.setVisibility(View.GONE);
       }
       if(showHotCalls == 0) {
           hotCalls.setVisibility(View.GONE);
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
        visibleTickets.setOnClickListener(handleClicks());
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
                    bundle.putString(Constants.BUNDLE_CONSTANT_AUTH_TOKEN, SPManager.getInstance(mActivity).getAuthToken());
                    getLoaderManager().initLoader(1, bundle, mLogoutCallback);
                    break;
                case R.id.rlInterestAreas_SetDFrgmt:
                    mCallback.onActionDialogSelected(DialogType.INTEREST_AREAS);
                    break;
                case R.id.rlUpdateResidentInfo_SetDFrgmt:
                    mCallback.onActionDialogSelected(DialogType.UPDATE_RESIDENT_INFO);
                    break;
                case R.id.rlVisibleTickets_SetDFrgmt:
                    mCallback.onActionDialogSelected(DialogType.VISIBLE_TICKETS);
            }
        };
    }

    private LoaderManager.LoaderCallbacks<Boolean> mLogoutCallback = new LoaderManager.LoaderCallbacks<Boolean>() {
        @Override
        public Loader<Boolean> onCreateLoader(int id, Bundle args) {
            return new LogoutLoader(mActivity, args);
        }

        @Override
        public void onLoadFinished(Loader<Boolean> loader, Boolean data) {
            if (data) {
                SPManager.getInstance(mActivity).setIsLoggedStatus(false);
                SPManager.getInstance(mActivity).deleteResidentInformation();
                mActivity.stopService(new Intent(mActivity, RegistrationIntentService.class));
                logout.setVisibility(View.GONE);
                login.setVisibility(View.VISIBLE);
                interestingAreas.setVisibility(View.GONE);
                updateResidentInfo.setVisibility(View.GONE);
                visibleTickets.setVisibility(View.GONE);
                mCallback.onLogoutListener();
            } else {
                Toast.makeText(mActivity, R.string.connection_error, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onLoaderReset(Loader<Boolean> loader) {
        }
    };

    @Override
    public void onRetryClick() {
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        RefWatcher refWatcher = Application.getRefWatcher(getActivity());
//        refWatcher.watch(this);
//    }
}
