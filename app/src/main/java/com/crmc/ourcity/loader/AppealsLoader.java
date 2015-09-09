package com.crmc.ourcity.loader;

import android.content.Context;
import android.os.Bundle;

import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.RestClientApi;
import com.crmc.ourcity.rest.api.CityApi;
import com.crmc.ourcity.rest.responce.appeals.WSResult;

import java.io.UnsupportedEncodingException;

import retrofit.RetrofitError;
import retrofit.mime.TypedByteArray;

/**
 * Created by podo on 04.09.15.
 */
public class AppealsLoader extends BaseLoader<WSResult>{

    private String json;
    private String route;



    public AppealsLoader(Context _context, Bundle _args) {
        super(_context);
        json = _args.getString(Constants.BUNDLE_CONSTANT_REQUEST_JSON);
        route = _args.getString(Constants.BUNDLE_CONSTANT_REQUEST_ROUTE);
    }

    @Override
    public WSResult loadInBackground() {
        CityApi api = RestClientApi.getCityApi();
        WSResult wsResult;

        try {
            wsResult = api.getAppeals(route, new TypedByteArray("application/json", json.getBytes("UTF-8")));
        } catch (RetrofitError | UnsupportedEncodingException _e) {
            wsResult = null;
        }
        return wsResult;
    }
}
