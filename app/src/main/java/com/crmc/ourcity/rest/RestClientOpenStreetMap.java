package com.crmc.ourcity.rest;

import com.crmc.ourcity.rest.api.AddressApi;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

public class RestClientOpenStreetMap {
    private static final String BASE_URL = "http://nominatim.openstreetmap.org";

    private AddressApi mAddressApi;
    private static RestClientOpenStreetMap mInstance;

    private RestClientOpenStreetMap() {

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setClient(new OkClient(new OkHttpClient()))
                .setEndpoint(BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        mAddressApi = restAdapter.create(AddressApi.class);
    }

    public static RestClientOpenStreetMap getInstance() {
        if (mInstance == null) {
            mInstance = new RestClientOpenStreetMap();
        }
        return mInstance;
    }

    public static AddressApi getAddressApi() {
        return getInstance().mAddressApi;
    }
}

