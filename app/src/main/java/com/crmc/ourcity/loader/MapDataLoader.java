package com.crmc.ourcity.loader;

import android.content.Context;
import android.os.Bundle;

import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.RestClientApi;
import com.crmc.ourcity.rest.api.CityApi;
import com.crmc.ourcity.rest.request.base.BaseCity;
import com.crmc.ourcity.rest.request.base.BaseModel;
import com.crmc.ourcity.rest.responce.map.MapCategory;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;

/**
 * Created by SetKrul on 29.07.2015.
 */
public class MapDataLoader extends BaseLoader<List<MapCategory>> {

    private int cityNumber;

    public MapDataLoader(Context context, Bundle args) {
        super(context);
        cityNumber = args.getInt(Constants.BUNDLE_CONSTANT_CITY_NUMBER);
    }

    @Override
    public List<MapCategory> loadInBackground() {
        CityApi api = RestClientApi.getCityApi();
        List<MapCategory> mapCategories;
        try {
            mapCategories =  api.getMapDetails(new BaseModel(new BaseCity(cityNumber)));
        } catch (RetrofitError e) {
            mapCategories = new ArrayList<>();
            mapCategories.add(new MapCategory());
        }
        return mapCategories;
    }
}