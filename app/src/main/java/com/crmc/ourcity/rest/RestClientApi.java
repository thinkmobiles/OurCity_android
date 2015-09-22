package com.crmc.ourcity.rest;

import com.crmc.ourcity.rest.api.CityApi;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by SetKrul on 28.07.2015.
 */
public class RestClientApi {
    private static final String BASE_URL = "http://134.249.164.30:8081/OurCityWS/Service.svc";
//    private static final String BASE_URL = "http://192.168.88.26:8081/OurCityWS/Service.svc";

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
