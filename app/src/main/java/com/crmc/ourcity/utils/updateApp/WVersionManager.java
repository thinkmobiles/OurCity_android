package com.crmc.ourcity.utils.updateApp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.crmc.ourcity.BuildConfig;
import com.crmc.ourcity.R;

import java.util.Calendar;


public class WVersionManager implements IWVersionManager {
    private static final String TAG = "WVersionManager";

    private static final int MODE_CHECK_VERSION = 100;
    private static final int MODE_ASK_FOR_RATE = 200;

    private CustomTagHandler customTagHandler;

    private String PREF_IGNORE_VERSION_CODE = "w.ignore.version.code";
    private String PREF_IGNORE_VERSION_NAME = "w.ignore.version.name";
    private String PREF_REMINDER_TIME = "w.reminder.time";

    private Activity activity;
    private Drawable icon;
    private String title;
    private String message;
    private String updateNowLabel;
    private String remindMeLaterLabel;
    private String ignoreThisVersionLabel;
    private String updateUrl;
    private String versionContentUrl;
    private int reminderTimer;
    private int mVersionCode;
    private String mVersionName;
    private int mStatusCode;
    private AlertDialogButtonListener listener;
    private boolean mDialogCancelable = true;
    private boolean mIsAskForRate = false;
    private String mAskForRatePositiveLabel;
    private String mAskForRateNegativeLabel;
    private int mMode = 100; // default c
    private OnReceiveListener mOnReceiveListener;
    private String mResult;

    public WVersionManager(Activity act) {
        this.activity = act;
        this.listener = new AlertDialogButtonListener();
        this.customTagHandler = new CustomTagHandler();
    }

    private Drawable getDefaultAppIcon() {
//        Drawable d = activity.getApplicationInfo().loadIcon(activity.getPackageManager());
        Drawable d = ContextCompat.getDrawable(activity, R.drawable.logo);
        return d;
    }

    /**
     * check version of app on market
     * and show dialog for update or remind me later
     * if current app version lower than version on market
     */
    public void checkVersion() {
        mMode = MODE_CHECK_VERSION;
        String versionContentUrl = getVersionContentUrl();
        if (versionContentUrl == null) {
            Log.e(TAG, "Please set versionContentUrl first");
            return;
        }

        Calendar c = Calendar.getInstance();
        long currentTimeStamp = c.getTimeInMillis();
        long reminderTimeStamp = getReminderTime();
        if (BuildConfig.DEBUG) {
            Log.v(TAG, "currentTimeStamp=" + currentTimeStamp);
            Log.v(TAG, "reminderTimeStamp=" + reminderTimeStamp);
        }

        if (currentTimeStamp > reminderTimeStamp) {
            // fire request to get update version content
            if (BuildConfig.DEBUG) {
                Log.v(TAG, "getting update content...");
            }

            Bundle args = new Bundle();
            args.putString(VersionManagerConstants.BUNDLE_URL, versionContentUrl);
            AppInfoLoader loader = new AppInfoLoader(activity, args);
            loader.startLoading();
            loader.registerListener(1, (loader1, data) -> {
                try {
                    if (data.currentVersion != null) {
                        mVersionName = data.currentVersion;
                        mStatusCode = data.statusCode;
                        if (BuildConfig.DEBUG) {
                            //Log.d(TAG, "status = " + data.currentVersion);
                            Log.d(TAG, "result = " + mResult);
                        }

                        if (mOnReceiveListener == null || mOnReceiveListener.onReceive(mStatusCode, mResult)) {

                            String currentVersionName = getCurrentVersionName();
                            if (getInt(currentVersionName) < getInt(mVersionName)) {
                                // new versionName will always higher than
                                // currentVersionName
                                if (getInt(mVersionName) != getIgnoreVersionCode()) {
                                    // set dialog message
                                    //setMessage(content);
                                    // show update dialog
                                    showDialog();
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * convert version name string into version integer
     * @param currentVersionName
     * @return int version code
     */
    private int getInt(String currentVersionName) {
        return Integer.parseInt(currentVersionName.replaceAll("\\.", ""));
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setIcon(getIcon());
        builder.setTitle(getTitle());
        //builder.setMessage(Html.fromHtml(getMessage(), null, getCustomTagHandler()));

        switch (mMode) {
            case MODE_CHECK_VERSION:
                builder.setPositiveButton(getRemindMeLaterLabel(), listener);
                builder.setNegativeButton(getUpdateNowLabel(), listener);
//				builder.setNegativeButton(getIgnoreThisVersionLabel(), listener);
                break;
            case MODE_ASK_FOR_RATE:
                builder.setPositiveButton(getAskForRatePositiveLabel(), listener);
                builder.setNegativeButton(getAskForRateNegativeLabel(), listener);
                break;
            default:
                return;
        }

        builder.setCancelable(false);


        AlertDialog dialog = builder.create();

        if (activity != null && !activity.isFinishing()) {
            dialog.show();
        }
    }

    @Override
    public String getUpdateNowLabel() {
        return updateNowLabel != null ? updateNowLabel : "Update now";
    }

    @Override
    public void setUpdateNowLabel(String updateNowLabel) {
        this.updateNowLabel = updateNowLabel;
    }

    @Override
    public String getRemindMeLaterLabel() {
        return remindMeLaterLabel != null ? remindMeLaterLabel : "Remind me later";
    }

    @Override
    public void setRemindMeLaterLabel(String remindMeLaterLabel) {
        this.remindMeLaterLabel = remindMeLaterLabel;
    }

    @Override
    public String getIgnoreThisVersionLabel() {
        return ignoreThisVersionLabel != null ? ignoreThisVersionLabel : "Ignore this version";
    }

    @Override
    public void setIgnoreThisVersionLabel(String ignoreThisVersionLabel) {
        this.ignoreThisVersionLabel = ignoreThisVersionLabel;
    }

    @Override
    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        String defaultMessage = null;
        switch (mMode) {
            case MODE_CHECK_VERSION:
                defaultMessage = "What's new in this version";
                break;
            case MODE_ASK_FOR_RATE:
                defaultMessage = "Please rate us!";
                break;
        }

        return message != null ? message : defaultMessage;
    }

    @Override
    public String getTitle() {
        String defaultTitle = null;
        switch (mMode) {
            case MODE_CHECK_VERSION:
                defaultTitle = "New Update Available";
                break;
            case MODE_ASK_FOR_RATE:
                defaultTitle = "Rate this app";
                break;
        }
        return title != null ? title : defaultTitle;
    }

    @Override
    public Drawable getIcon() {
        return icon != null ? icon : getDefaultAppIcon();
    }

    @Override
    public String getUpdateUrl() {
        return updateUrl != null ? updateUrl : getGooglePlayStoreUrl();
    }

    @Override
    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }

    @Override
    public String getVersionContentUrl() {
        return versionContentUrl;
    }

    @Override
    public void setVersionContentUrl(String versionContentUrl) {
        this.versionContentUrl = versionContentUrl;
    }

    @Override
    public int getReminderTimer() {
        return reminderTimer > 0 ? reminderTimer : (1 * 60); // default value 60
        // minutes
    }

    @Override
    public void setReminderTimer(int minutes) {
        if (minutes > 0) {
            reminderTimer = minutes;
        }
    }

    private void updateNow(String url) {
        if (url != null) {
            try {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                activity.startActivity(intent);
            } catch (Exception e) {
                Log.e(TAG, "is update url correct?" + e);
            }
        }
    }

    private void remindMeLater(int reminderTimer) {
        Calendar c = Calendar.getInstance();
        long currentTimeStamp = c.getTimeInMillis();

        c.add(Calendar.MINUTE, reminderTimer);
        long reminderTimeStamp = c.getTimeInMillis();

        if (BuildConfig.DEBUG) {
            Log.v(TAG, "currentTimeStamp=" + currentTimeStamp);
            Log.v(TAG, "reminderTimeStamp=" + reminderTimeStamp);
        }

        setReminderTime(reminderTimeStamp);
    }

    private void setReminderTime(long reminderTimeStamp) {
        PreferenceManager.getDefaultSharedPreferences(activity).edit().putLong(PREF_REMINDER_TIME, reminderTimeStamp)
                .commit();
    }

    private long getReminderTime() {
        return PreferenceManager.getDefaultSharedPreferences(activity).getLong(PREF_REMINDER_TIME, 0);
    }

    private void ignoreThisVersion() {
        PreferenceManager.getDefaultSharedPreferences(activity).edit().putString(PREF_IGNORE_VERSION_NAME, mVersionName)
                .commit();
    }

    private String getGooglePlayStoreUrl() {
        String id = activity.getApplicationInfo().packageName; // current google
        // play is using
        // package name
        // as id
        return "market://details?id=" + id;
    }

    private class AlertDialogButtonListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case AlertDialog.BUTTON_POSITIVE:
                    remindMeLater(getReminderTimer());
                    break;
                case AlertDialog.BUTTON_NEGATIVE:
                    updateNow(getUpdateUrl());
                    break;
//				case AlertDialog.BUTTON_NEUTRAL:
//					remindMeLater(getReminderTimer());
//					break;
//				case AlertDialog.BUTTON_NEGATIVE:
//					ignoreThisVersion();
//					break;
            }
        }
    }

    @Override
    public int getCurrentVersionCode() {
        int currentVersionCode = 0;
        PackageInfo pInfo;
        try {
            pInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
            currentVersionCode = pInfo.versionCode;
        } catch (NameNotFoundException e) {
            // return 0
        }
        return currentVersionCode;
    }

    @Override
    public String getCurrentVersionName() {
        PackageInfo pInfo;
        try {
            pInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            return "-1";
        }
        return pInfo.versionName;
    }

    @Override
    public int getIgnoreVersionCode() {
        return PreferenceManager.getDefaultSharedPreferences(activity).getInt(PREF_IGNORE_VERSION_CODE, 1);
    }

    @Override
    public String getIngnoreVersionName() {
        return PreferenceManager.getDefaultSharedPreferences(activity).getString(PREF_IGNORE_VERSION_NAME, "0");
    }

    @Override
    public CustomTagHandler getCustomTagHandler() {
        return customTagHandler;
    }

    @Override
    public void setCustomTagHandler(CustomTagHandler customTagHandler) {
        this.customTagHandler = customTagHandler;
    }

    public boolean isDialogCancelable() {
        return mDialogCancelable;
    }

    public void setDialogCancelable(boolean dialogCancelable) {
        mDialogCancelable = dialogCancelable;
    }

    public void askForRate() {
        mMode = MODE_ASK_FOR_RATE;
        showDialog();
    }

    public String getAskForRatePositiveLabel() {
        return mAskForRatePositiveLabel == null ? "OK" : mAskForRatePositiveLabel;
    }

    public void setAskForRatePositiveLabel(String askForRatePositiveLabel) {
        mAskForRatePositiveLabel = askForRatePositiveLabel;
    }

    public String getAskForRateNegativeLabel() {
        return mAskForRateNegativeLabel == null ? "Not now" : mAskForRateNegativeLabel;
    }

    public void setAskForRateNegativeLabel(String askForRateNegativeLabel) {
        mAskForRateNegativeLabel = askForRateNegativeLabel;
    }

    @Override
    public void setOnReceiveListener(OnReceiveListener listener) {
        this.mOnReceiveListener = listener;
    }
}