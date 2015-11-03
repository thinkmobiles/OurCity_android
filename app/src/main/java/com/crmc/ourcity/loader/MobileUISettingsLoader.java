package com.crmc.ourcity.loader;

import android.content.Context;
import android.os.Bundle;

import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.RestClientApi;
import com.crmc.ourcity.rest.api.CityApi;
import com.crmc.ourcity.rest.request.base.BaseCity;
import com.crmc.ourcity.rest.request.base.BaseModel;
import com.crmc.ourcity.rest.responce.mobileUISettings.MobileUISettings;

import retrofit.RetrofitError;

public class MobileUISettingsLoader extends BaseLoader<MobileUISettings> {

    private int cityNumber;

    public MobileUISettingsLoader(Context _context, Bundle _args) {
        super(_context);
        cityNumber = _args.getInt(Constants.BUNDLE_CONSTANT_CITY_NUMBER);
    }

    @Override
    public MobileUISettings loadInBackground() {
        CityApi api = RestClientApi.getCityApi();
        MobileUISettings settings;

        try {
            settings = api.getMobileUISettings(new BaseModel(new BaseCity(cityNumber)));
        } catch (RetrofitError e) {
            settings = null;
        }

        return settings;
    }
}
