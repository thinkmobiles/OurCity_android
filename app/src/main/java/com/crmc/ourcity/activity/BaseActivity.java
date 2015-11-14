package com.crmc.ourcity.activity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.crmc.ourcity.utils.SPManager;

import java.util.Locale;

public abstract class BaseActivity extends AppCompatActivity {

    private Configuration config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @SuppressWarnings("unchecked")
    public final <T extends View> T findView(@IdRes int _id) {
        return (T) findViewById(_id);
    }
}
