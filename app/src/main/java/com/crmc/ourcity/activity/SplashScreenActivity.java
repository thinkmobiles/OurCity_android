package com.crmc.ourcity.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RelativeLayout;

import com.crmc.ourcity.BuildConfig;
import com.crmc.ourcity.R;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.CrmcCredentialsLoader;
import com.crmc.ourcity.loader.ImageLoader;
import com.crmc.ourcity.loader.MobileUISettingsLoader;
import com.crmc.ourcity.loader.TickerLoader;
import com.crmc.ourcity.rest.responce.crmcCredentials.CRMCCredentials;
import com.crmc.ourcity.rest.responce.mobileUISettings.MobileUISettings;
import com.crmc.ourcity.rest.responce.mobileUISettings.SettingItem;
import com.crmc.ourcity.rest.responce.ticker.TickerModel;
import com.crmc.ourcity.utils.Image;
import com.crmc.ourcity.utils.SPManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class SplashScreenActivity extends BaseActivity implements LoaderManager.LoaderCallbacks {


    private RelativeLayout rlBackground;
    private Handler mHandler = new Handler();
    private Drawable drawable;
    private ArrayList<TickerModel> tickers;
    private WeakReference<SplashScreenActivity> mActivity = new WeakReference<>(this);
    private static final int SPLASH_DELAY = 1;
    private Resources resources;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        resources = getResources();
        drawable = ResourcesCompat.getDrawable(resources, R.drawable.splash, null);
        rlBackground = (RelativeLayout) findViewById(R.id.rlSplashScreen_SPA);
        rlBackground.setBackgroundDrawable(ResourcesCompat.getDrawable(resources, R.drawable.splash, null));
        new Handler().postDelayed(() -> {
            Bundle loaderBundle = buildLoaderBundle();
            loadMobileUISettings(loaderBundle);
            loadCrmcCredential(loaderBundle);
            loadTicker(loaderBundle);
            loadBackgroundImage(loaderBundle);
        }, 3000);
    }

    private void loadMobileUISettings(Bundle args) {
        getSupportLoaderManager().initLoader(Constants.LOADER_MOBILE_UI_SETTINGS_ID, args, this);
    }

    private void loadCrmcCredential(Bundle args) {
        getSupportLoaderManager().initLoader(Constants.LOADER_CRMC_CREDENTIAL_ID, args, this);
    }

    private void loadTicker(Bundle args) {
        getSupportLoaderManager().initLoader(Constants.LOADER_TICKERS_ID, args, this);
    }

    private void loadBackgroundImage(Bundle args) {
        getSupportLoaderManager().initLoader(Constants.LOADER_BACKGROUND_IMAGE_ID, args, this);
    }

    private Bundle buildLoaderBundle() {
       Bundle loaderBundle = new Bundle();
        loaderBundle.putInt(Constants.BUNDLE_CONSTANT_CITY_NUMBER, BuildConfig.CITY_ID);
        loaderBundle.putInt(Constants.BUNDLE_CONSTANT_LOAD_IMAGE_TYPE, Constants
                .BUNDLE_CONSTANT_LOAD_IMAGE_TYPE_PROMOTIONAL);
        return loaderBundle;
    }

    private Runnable mEndSplash = new Runnable() {
        public void run() {
            if (!isFinishing()) {
                Intent intent = new Intent(mActivity.get(), MainActivity.class);
                intent.putParcelableArrayListExtra(Constants.BUNDLE_TICKERS_LIST, tickers);
                getSupportLoaderManager().destroyLoader(Constants.LOADER_BACKGROUND_IMAGE_ID);
                getSupportLoaderManager().destroyLoader(Constants.LOADER_TICKERS_ID);
                startActivity(intent);
                finish();
            }
        }
    };

    @Override
    public Loader onCreateLoader(int _id, Bundle _args) {
        Loader loader = null;
        switch (_id) {
            case Constants.LOADER_MOBILE_UI_SETTINGS_ID:
                loader = new MobileUISettingsLoader(mActivity.get(), _args);
                break;
            case Constants.LOADER_CRMC_CREDENTIAL_ID:
                loader = new CrmcCredentialsLoader(mActivity.get(), _args);
                break;
            case Constants.LOADER_BACKGROUND_IMAGE_ID:
                loader = new ImageLoader(mActivity.get(), _args);
                break;
            case Constants.LOADER_TICKERS_ID:
                loader = new TickerLoader(mActivity.get(), _args);
                break;
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader _loader, Object _data) {
        switch (_loader.getId()) {
            case Constants.LOADER_MOBILE_UI_SETTINGS_ID:
                MobileUISettings settings = (MobileUISettings) _data;

                if (settings != null) {
                    if (settings.properties != null) {
                        if (settings.properties.size() > 0) {
                            saveMobileUISettings(settings);
                        }
                    }
                }
//                if (settings != null & settings.properties != null & settings.properties.size() > 0) {
//                    saveMobileUISettings(settings);
//                }
                break;

            case Constants.LOADER_CRMC_CREDENTIAL_ID:
                CRMCCredentials credentials = (CRMCCredentials) _data;
                if (credentials != null) {
                    saveCredentials(credentials);
                }
                break;
            case Constants.LOADER_BACKGROUND_IMAGE_ID:

                String dataString = (String) _data;
                if (!TextUtils.isEmpty(dataString)) {
                    setBackgroundImage(dataString);
                }
                mHandler.postDelayed(mEndSplash, SPLASH_DELAY * 1000);
                break;
            case Constants.LOADER_TICKERS_ID:
                tickers = (ArrayList<TickerModel>) _data;
                break;
        }
    }

    private void saveMobileUISettings(MobileUISettings settings) {

        for (SettingItem item : settings.properties) {
            if(item.propertyName.contains("ShowIntrestAreasId")) {
                SPManager.getInstance(this).setShowInterestAreas(item.propertyValue);
            }
            if (item.propertyName.contains("ShowHotCallsId")) {
                SPManager.getInstance(this).setShowHotCalls(item.propertyValue);
            }
        }
    }

    private void setBackgroundImage(String dataString) {
        drawable = new BitmapDrawable(resources, Image.convertBase64ToBitmap(dataString));
        rlBackground.setBackgroundDrawable(drawable);
    }

    private void saveCredentials(CRMCCredentials credentials) {
        SPManager.getInstance(this).setCRMCUsername(credentials.login);
        SPManager.getInstance(this).setCRMCPassword(credentials.password);
    }

    @Override
    public void onLoaderReset(Loader _loader) {}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivity.clear();
    }
}
