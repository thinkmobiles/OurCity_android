package com.crmc.ourcity.loader;

import android.content.Context;
import android.os.Bundle;

import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.RestClientApi;
import com.crmc.ourcity.rest.api.CityApi;
import com.crmc.ourcity.rest.request.streets.StreetsDetails;
import com.crmc.ourcity.rest.request.streets.StreetsModel;
import com.crmc.ourcity.rest.responce.address.StreetsFull;

import java.util.ArrayList;

import retrofit.RetrofitError;

/**
 * Created by SetKrul on 31.07.2015.
 */
public class StreetsLoader extends BaseLoader<StreetsFull> {

    private int clientId;
    public String userName;
    public String password;

    public StreetsLoader(Context _context, Bundle _args) {
        super(_context);
        clientId = _args.getInt(Constants.BUNDLE_CONSTANT_CLIENT_ID);
        userName = _args.getString(Constants.BUNDLE_CONSTANT_USER_NAME);
        password = _args.getString(Constants.BUNDLE_CONSTANT_PASSWORD);
    }

    @Override
    public StreetsFull loadInBackground() {
        CityApi api = RestClientApi.getCityApi();
        StreetsFull mStreetsFull;
        try {
            mStreetsFull =  api.getStreets(new StreetsModel(new StreetsDetails(clientId, userName, password)));
        } catch (RetrofitError _e) {
            mStreetsFull = new StreetsFull();
            mStreetsFull.streetsList = new ArrayList<>();
            //mStreetsFull.streetsList.add(new StreetsItem());
        }
        return mStreetsFull;
    }
}