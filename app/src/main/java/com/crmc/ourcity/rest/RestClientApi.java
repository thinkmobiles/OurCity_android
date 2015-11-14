package com.crmc.ourcity.rest;

import com.crmc.ourcity.BuildConfig;
import com.crmc.ourcity.rest.api.CityApi;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

public class RestClientApi {
    private static final String BASE_URL = BuildConfig.BASE_URL;

    private CityApi cityApi;
    private static RestClientApi mInstance;

    private RestClientApi() {

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setClient(new OkClient(new OkHttpClient()))
                .setEndpoint(BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        cityApi = restAdapter.create(CityApi.class);
    }

    public static RestClientApi getInstance() {
        if (mInstance == null) {
            mInstance = new RestClientApi();
        }
        return mInstance;
    }

    public static CityApi getCityApi() {
        return getInstance().cityApi;
    }
}
