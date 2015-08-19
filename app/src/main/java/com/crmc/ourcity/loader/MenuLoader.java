package com.crmc.ourcity.loader;

import android.content.Context;
import android.os.Bundle;

import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.RestClientApi;
import com.crmc.ourcity.rest.api.CityApi;
import com.crmc.ourcity.rest.request.menu.CityModel;
import com.crmc.ourcity.rest.request.menu.Resident;
import com.crmc.ourcity.rest.responce.menu.MenuFull;

import java.util.ArrayList;

import retrofit.RetrofitError;

/**
 * Created by SetKrul on 29.07.2015.
 */
public class MenuLoader extends BaseLoader<MenuFull> {

    private int cityNumber;
    private String lng;

    public MenuLoader(Context _context, Bundle _args) {
        super(_context);
        cityNumber = _args.getInt(Constants.BUNDLE_CONSTANT_CITY_NUMBER);
        lng = _args.getString(Constants.BUNDLE_CONSTANT_LANG);
    }

    @Override
    public MenuFull loadInBackground() {
        CityApi api = RestClientApi.getCityApi();
        MenuFull menuNodes;
        try {
            menuNodes =  api.getMenu(new CityModel(new Resident(0, cityNumber), lng));
        } catch (RetrofitError e) {
            menuNodes = new MenuFull();
            menuNodes.nodes = new ArrayList<>();
            //menuNodes.nodes.add(new MenuModel());
        }
        return menuNodes;
    }
}