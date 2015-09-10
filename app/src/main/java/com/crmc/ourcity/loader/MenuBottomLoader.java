package com.crmc.ourcity.loader;

import android.content.Context;
import android.os.Bundle;

import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.RestClientApi;
import com.crmc.ourcity.rest.api.CityApi;
import com.crmc.ourcity.rest.request.menu.CityModel;
import com.crmc.ourcity.rest.request.menu.Resident;
import com.crmc.ourcity.rest.responce.menu.MenuFull;

import retrofit.RetrofitError;

/**
 * Created by SetKrul on 26.08.2015.
 */
public class MenuBottomLoader extends BaseLoader<MenuFull> {

    private int cityNumber;
    private String lng;
    private int residentId;

    public MenuBottomLoader(Context _context, Bundle _args) {
        super(_context);
        cityNumber = _args.getInt(Constants.BUNDLE_CONSTANT_CITY_NUMBER);
        lng = _args.getString(Constants.BUNDLE_CONSTANT_LANG);
        residentId = _args.getInt(Constants.BUNDLE_CONSTANT_RESIDENT_ID);
    }

    @Override
    public MenuFull loadInBackground() {
        CityApi api = RestClientApi.getCityApi();
        MenuFull menuNodes;
        try {
            menuNodes =  api.getMenuBottom(new CityModel(new Resident(residentId, cityNumber), lng));
        } catch (RetrofitError e) {
            menuNodes = null;
        }
        return menuNodes;
    }
}
