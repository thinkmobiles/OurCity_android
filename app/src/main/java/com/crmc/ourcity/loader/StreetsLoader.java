package com.crmc.ourcity.loader;

import android.content.Context;
import android.os.Bundle;

import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.RestClientApi;
import com.crmc.ourcity.rest.api.CityApi;
import com.crmc.ourcity.rest.responce.address.StreetsFull;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import retrofit.RetrofitError;
import retrofit.mime.TypedByteArray;

public class StreetsLoader extends BaseLoader<StreetsFull> {

    private String json;
    private String route;

    public StreetsLoader(Context _context, Bundle _args) {
        super(_context);
        json = _args.getString(Constants.BUNDLE_CONSTANT_REQUEST_JSON);
        route = _args.getString(Constants.BUNDLE_CONSTANT_REQUEST_ROUTE);
    }

    @Override
    public StreetsFull loadInBackground() {
        CityApi api = RestClientApi.getCityApi();
        StreetsFull mStreetsFull;
        try {
            mStreetsFull =  api.getStreets(route, new TypedByteArray("application/json", json.getBytes("UTF-8")));
        } catch (RetrofitError | UnsupportedEncodingException _e) {
            mStreetsFull = new StreetsFull();
            mStreetsFull.streetsList = new ArrayList<>();
        }
        return mStreetsFull;
    }
}