package com.crmc.ourcity.activity;

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
        String lang = SPManager.getInstance(getApplicationContext()).getApplicationLanguage();
        String country = SPManager.getInstance(getApplicationContext()).getApplicationCountry();
        Locale locale = new Locale(lang, country);
        Locale.setDefault(locale);

        config = new Configuration(getBaseContext(). getResources().getConfiguration());
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        super.onCreate(savedInstanceState);
    }

    @SuppressWarnings("unchecked")
    public final <T extends View> T findView(@IdRes int _id) {
        return (T) findViewById(_id);
    }
}
