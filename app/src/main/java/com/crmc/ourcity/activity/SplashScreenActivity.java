package com.crmc.ourcity.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.RelativeLayout;

import com.crmc.ourcity.R;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.ImageLoader;
import com.crmc.ourcity.loader.TickerLoader;
import com.crmc.ourcity.rest.responce.ticker.TickerModel;
import com.crmc.ourcity.utils.Image;

import java.util.ArrayList;


public class SplashScreenActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks {


    private RelativeLayout rlBackground;
    private Handler mHandler = new Handler();
    private int cityNumber;
    private Drawable drawable;
    private ArrayList<TickerModel> tickers;
    private Bundle loaderBundle;

    private int SPLASH_DELAY;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SPLASH_DELAY = getResources().getInteger(R.integer.banner_delay); //seconds
        cityNumber = getResources().getInteger(R.integer.city_id);
        drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.splash, null);
        rlBackground = (RelativeLayout) findViewById(R.id.rlSplashScreen_SPA);
        rlBackground.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.splash, null));
        new Handler().postDelayed(() -> {
            buildLoaderBundle();
            loadTicker();
            loadBackgroundImage();
        }, 3000);

    }

    private void loadTicker() {
        getSupportLoaderManager().initLoader(Constants.LOADER_TICKERS_ID, loaderBundle, this);

    }

    private void loadBackgroundImage() {
        getSupportLoaderManager().initLoader(Constants.LOADER_BACKGROUND_IMAGE_ID, loaderBundle, this);
    }

    private void buildLoaderBundle() {
        loaderBundle = new Bundle();
        loaderBundle.putInt(Constants.BUNDLE_CONSTANT_CITY_NUMBER, cityNumber);
        loaderBundle.putInt(Constants.BUNDLE_CONSTANT_LOAD_IMAGE_TYPE, Constants
                .BUNDLE_CONSTANT_LOAD_IMAGE_TYPE_PROMOTIONAL);
    }

    private Runnable mEndSplash = new Runnable() {
        public void run() {
            if (!isFinishing()) {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
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
            case Constants.LOADER_BACKGROUND_IMAGE_ID:
                loader = new ImageLoader(SplashScreenActivity.this, _args);
                break;
            case Constants.LOADER_TICKERS_ID:
                loader = new TickerLoader(SplashScreenActivity.this, _args);
                break;
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader _loader, Object _data) {
        switch (_loader.getId()) {
            case Constants.LOADER_BACKGROUND_IMAGE_ID:

                String dataString = (String) _data;
                if (!TextUtils.isEmpty(dataString)) {
                    drawable = new BitmapDrawable(getResources(), Image.convertBase64ToBitmap(dataString));
                    rlBackground.setBackground(drawable);

                }
                mHandler.postDelayed(mEndSplash, SPLASH_DELAY * 1000);
                break;
            case Constants.LOADER_TICKERS_ID:
                tickers = (ArrayList<TickerModel>) _data;
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader _loader) {

    }

}
