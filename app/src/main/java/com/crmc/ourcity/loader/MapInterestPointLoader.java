package com.crmc.ourcity.loader;

import android.content.Context;
import android.os.Bundle;

import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.RestClientApi;
import com.crmc.ourcity.rest.api.CityApi;
import com.crmc.ourcity.rest.responce.map.MapCategory;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.mime.TypedByteArray;

/**
 * Created by SetKrul on 29.07.2015.
 */
public class MapInterestPointLoader extends BaseLoader<List<MapCategory>> {

    private String json;
    private String route;

    public MapInterestPointLoader(Context _context, Bundle _args) {
        super(_context);
        json = _args.getString(Constants.BUNDLE_CONSTANT_REQUEST_JSON);
        route = _args.getString(Constants.BUNDLE_CONSTANT_REQUEST_ROUTE);
    }

    @Override
    public List<MapCategory> loadInBackground() {
        CityApi api = RestClientApi.getCityApi();
        List<MapCategory> mapCategories;
        try {
            mapCategories =  api.getMapDetails(route, new TypedByteArray("application/json", json.getBytes("UTF-8")));
        } catch (RetrofitError | UnsupportedEncodingException _e) {
            mapCategories = new ArrayList<>();
            mapCategories.add(new MapCategory());
        }
        return mapCategories;
    }
}