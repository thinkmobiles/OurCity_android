package com.crmc.ourcity.loader;

import android.content.Context;
import android.os.Bundle;

import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.RestClientApi;
import com.crmc.ourcity.rest.api.CityApi;
import com.crmc.ourcity.rest.request.login.PushTokenUpdatingModel;

import retrofit.RetrofitError;

/**
 * Created by podo on 21.08.15.
 */
public class PushTokenLoader extends BaseLoader<Boolean> {

    //private PushTokenUpdatingModel pushTokenUpdatingModel;

    public PushTokenLoader(Context _context, Bundle _bundle) {
        super(_context);
//        pushTokenUpdatingModel = new PushTokenUpdatingModel();
//
//        pushTokenUpdatingModel.authToken = _bundle.getString(Constants.BUNDLE_CONSTANT_AUTH_TOKEN);
//        pushTokenUpdatingModel.pushToken = _bundle.getString(Constants.BUNDLE_CONSTANT_PUSH_TOKEN);
    }

    @Override
    public Boolean loadInBackground() {

//        CityApi cityApi = RestClientApi.getCityApi();
//        Boolean isTokenUpdated;
//        try {
//            isTokenUpdated = cityApi.updatePushTokenOnWS(pushTokenUpdatingModel);
//        } catch (RetrofitError e) {
//            isTokenUpdated = false;
//        }
//        return isTokenUpdated;
        return null;
    }
}
