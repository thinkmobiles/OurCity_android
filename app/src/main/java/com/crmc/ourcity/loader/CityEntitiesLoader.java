package com.crmc.ourcity.loader;

import android.content.Context;
import android.os.Bundle;

import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.RestClientApi;
import com.crmc.ourcity.rest.api.CityApi;
import com.crmc.ourcity.rest.responce.events.CityEntities;

import java.io.UnsupportedEncodingException;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.mime.TypedByteArray;

public class CityEntitiesLoader extends BaseLoader<List<CityEntities>> {

    private String json;
    private String route;

    public CityEntitiesLoader(Context _context, Bundle _args) {
        super(_context);
        json = _args.getString(Constants.BUNDLE_CONSTANT_REQUEST_JSON);
        route = _args.getString(Constants.BUNDLE_CONSTANT_REQUEST_ROUTE);
    }

    @Override
    public List<CityEntities> loadInBackground() {
        CityApi api = RestClientApi.getCityApi();
        List<CityEntities> mCityEntities;
        try {
            mCityEntities = api.getCityEntities(route, new TypedByteArray("application/json", json.getBytes("UTF-8")));
        } catch (RetrofitError | UnsupportedEncodingException _e) {
            mCityEntities = null;
        }
        return mCityEntities;
    }
}