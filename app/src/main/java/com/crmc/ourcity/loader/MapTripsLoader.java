package com.crmc.ourcity.loader;

import android.content.Context;
import android.os.Bundle;

import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.RestClientApi;
import com.crmc.ourcity.rest.api.CityApi;
import com.crmc.ourcity.rest.responce.map.MapTrips;

import java.io.UnsupportedEncodingException;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.mime.TypedByteArray;

/**
 * Created by SetKrul on 30.07.2015.
 */
public class MapTripsLoader extends BaseLoader<List<MapTrips>> {

    private String json;
    private String route;

    public MapTripsLoader(Context _context, Bundle _args) {
        super(_context);
        json = _args.getString(Constants.BUNDLE_CONSTANT_REQUEST_JSON);
        route = _args.getString(Constants.BUNDLE_CONSTANT_REQUEST_ROUTE);
    }

    @Override
    public List<MapTrips> loadInBackground() {
        CityApi api = RestClientApi.getCityApi();
        List<MapTrips> mapTrips;
        try {
            mapTrips =  api.getMapTrips(route, new TypedByteArray("application/json", json.getBytes("UTF-8")));
        } catch (RetrofitError | UnsupportedEncodingException e) {
            mapTrips = null;
        }
        return mapTrips;
    }
}