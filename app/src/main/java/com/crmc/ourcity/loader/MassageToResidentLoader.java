package com.crmc.ourcity.loader;

import android.content.Context;
import android.os.Bundle;

import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.RestClientApi;
import com.crmc.ourcity.rest.api.CityApi;
import com.crmc.ourcity.rest.responce.events.MassageToResident;
import com.crmc.ourcity.utils.SPManager;

import java.io.UnsupportedEncodingException;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.mime.TypedByteArray;

public class MassageToResidentLoader extends BaseLoader<List<MassageToResident>> {

    private String json;
    private String route;

    public MassageToResidentLoader(Context _context, Bundle _args) {
        super(_context);
        json = _args.getString(Constants.BUNDLE_CONSTANT_REQUEST_JSON);
        route = _args.getString(Constants.BUNDLE_CONSTANT_REQUEST_ROUTE);
        json = changeResidentIdInJson(json, SPManager.getInstance(_context).getResidentId());
    }

    @Override
    public List<MassageToResident> loadInBackground() {
        CityApi api = RestClientApi.getCityApi();
        List<MassageToResident> mMassageToResident;
        try {
            mMassageToResident = api.getMessagesToResident(route, new TypedByteArray("application/json", json.getBytes("UTF-8")));
        } catch (RetrofitError | UnsupportedEncodingException e) {
            mMassageToResident = null;
        }
        return mMassageToResident;
    }
}