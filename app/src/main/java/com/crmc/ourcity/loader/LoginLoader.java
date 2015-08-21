package com.crmc.ourcity.loader;

import android.content.Context;
import android.os.Bundle;

import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.RestClientApi;
import com.crmc.ourcity.rest.api.CityApi;
import com.crmc.ourcity.rest.request.login.ResidentSignInDetails;

import retrofit.RetrofitError;

/**
 * Created by podo on 20.08.15.
 */
public class LoginLoader extends BaseLoader<String> {

    private ResidentSignInDetails residentSignInDetails;

    public LoginLoader(Context _context, Bundle _bundle) {
        super(_context);
        residentSignInDetails = new ResidentSignInDetails();
        residentSignInDetails.userName = _bundle.getString(Constants.BUNDLE_CONSTANT_USER_NAME, "default value");
        residentSignInDetails.password = _bundle.getString(Constants.BUNDLE_CONSTANT_PASSWORD, "default value");
        residentSignInDetails.authToken = _bundle.getString(Constants.BUNDLE_CONSTANT_AUTH_TOKEN, "");
        residentSignInDetails.pushToken = "APA124324";

    }

    @Override
    public String loadInBackground() {

        CityApi cityApi = RestClientApi.getCityApi();
        String authToken;

        try {
            authToken = cityApi.loginResidentAndGetAuthToken(residentSignInDetails);
        } catch (RetrofitError e) {
            e.printStackTrace();
            authToken = "not so good";
        }
        return  authToken;
    }
}
