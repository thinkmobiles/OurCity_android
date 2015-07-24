package com.crmc.ourcity.rest;

import com.crmc.ourcity.rest.api.AddressApi;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by SetKrul on 24.07.2015.
 */
public class RestClientOpenStreetMap {
    private static final String BASE_URL = "http://nominatim.openstreetmap.org";

    private AddressApi mAddressApi;
    private static RestClientOpenStreetMap mInstance;

    private RestClientOpenStreetMap() {

//        Gson gson = new GsonBuilder().registerTypeAdapter(AddressFull.class, new AddressDeserializer()).create();

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

    public static AddressApi getWeatherApi() {
        return getInstance().mAddressApi;
    }
}

