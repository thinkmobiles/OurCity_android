package com.crmc.ourcity.loader;

import android.content.Context;
import android.os.Bundle;

import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.RestClientApi;
import com.crmc.ourcity.rest.api.CityApi;
import com.crmc.ourcity.rest.request.base.BaseCity;
import com.crmc.ourcity.rest.request.base.BaseModel;
import com.crmc.ourcity.rest.responce.map.MapTrips;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;

/**
 * Created by SetKrul on 30.07.2015.
 */
public class MapTripsLoader extends BaseLoader<List<MapTrips>> {

    private int cityNumber;

    public MapTripsLoader(Context context, Bundle args) {
        super(context);
        cityNumber = args.getInt(Constants.BUNDLE_CONSTANT_CITY_NUMBER);
    }

    @Override
    public List<MapTrips> loadInBackground() {
        CityApi api = RestClientApi.getCityApi();
        List<MapTrips> mapTrips;
        try {
            mapTrips =  api.getMapTrips(new BaseModel(new BaseCity(cityNumber)));
        } catch (RetrofitError e) {
            mapTrips = new ArrayList<>();
            mapTrips.add(new MapTrips());
        }
        return mapTrips;
    }
}