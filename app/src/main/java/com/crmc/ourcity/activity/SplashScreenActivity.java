package com.crmc.ourcity.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.RelativeLayout;

import com.crmc.ourcity.R;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.ImageLoader;
import com.crmc.ourcity.utils.Image;


public class SplashScreenActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private RelativeLayout rlBackground;
    private static final int SPLASH_DURATION_MS = 2000;
    private Handler mHandler = new Handler();
    private int cityNumber;
    private Drawable drawable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        cityNumber = getResources().getInteger(R.integer.city_id);
        drawable = getResources().getDrawable(R.drawable.splash);
        rlBackground = (RelativeLayout) findViewById(R.id.rlSplashScreen_SPA);
        rlBackground.setBackground(getResources().getDrawable(R.drawable.splash));


        Bundle bundle = new Bundle();
        bundle.putInt(Constants.BUNDLE_CONSTANT_CITY_NUMBER, cityNumber);
        bundle.putInt(Constants.BUNDLE_CONSTANT_LOAD_IMAGE_TYPE, Constants.BUNDLE_CONSTANT_LOAD_IMAGE_TYPE_PROMOTIONAL);
        getSupportLoaderManager().initLoader(Constants.LOADER_SPLASH_CITY_ID, bundle, this);


    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        mEndSplash.run();
//        return super.onTouchEvent(event);
//    }

    private Runnable mEndSplash = new Runnable() {
        public void run() {
            if (!isFinishing()) {
                mHandler.removeCallbacks(this);
                getSupportLoaderManager().destroyLoader(Constants.LOADER_SPLASH_CITY_ID);
                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));

                finish();
            }
        }
    };


    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new ImageLoader(this, args);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        if (!TextUtils.isEmpty(data)) {
            drawable = new BitmapDrawable(getResources(), Image.convertBase64ToBitmap(data));
            rlBackground.setBackground(drawable);
            mHandler.postDelayed(mEndSplash, SPLASH_DURATION_MS);
        } else {
            startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
