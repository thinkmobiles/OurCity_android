package com.crmc.ourcity.loader;

import android.content.Context;
import android.os.Bundle;

import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.RestClientApi;
import com.crmc.ourcity.rest.api.CityApi;
import com.crmc.ourcity.rest.responce.events.Documents;

import java.io.UnsupportedEncodingException;

import retrofit.RetrofitError;
import retrofit.mime.TypedByteArray;

/**
 * Created by SetKrul on 21.08.2015.
 */
public class DocumentsLoader extends BaseLoader<Documents> {

    private String json;
    private String route;

    public DocumentsLoader(Context _context, Bundle _args) {
        super(_context);
        json = _args.getString(Constants.BUNDLE_CONSTANT_REQUEST_JSON);
        route = _args.getString(Constants.BUNDLE_CONSTANT_REQUEST_ROUTE);
    }

    @Override
    public Documents loadInBackground() {
        CityApi api = RestClientApi.getCityApi();
        Documents mDocuments;
        try {
            mDocuments =  api.getDocuments(route, new TypedByteArray("application/json", json.getBytes("UTF-8")));
        } catch (RetrofitError | UnsupportedEncodingException _e) {
            mDocuments = null;
        }
        return mDocuments;
    }
}