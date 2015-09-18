package com.crmc.ourcity.loader;

import android.content.Context;
import android.os.Bundle;

import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.RestClientApi;
import com.crmc.ourcity.rest.api.CityApi;
import com.crmc.ourcity.rest.request.login.ResidentSignInDetails;
import com.crmc.ourcity.rest.responce.login.LoginResponse;

import retrofit.RetrofitError;

/**
 * Created by podo on 20.08.15.
 */
public class LoginLoader extends BaseLoader<LoginResponse> {

    private ResidentSignInDetails residentSignInDetails;

    public LoginLoader(Context _context, Bundle _bundle) {
        super(_context);
        residentSignInDetails = new ResidentSignInDetails();
        residentSignInDetails.userName = _bundle.getString(Constants.BUNDLE_CONSTANT_USER_NAME, "default value");
        residentSignInDetails.password = _bundle.getString(Constants.BUNDLE_CONSTANT_PASSWORD, "default value");
        residentSignInDetails.cityId = _bundle.getInt(Constants.BUNDLE_CONSTANT_CITY_NUMBER, 1);
        residentSignInDetails.authToken = _bundle.getString(Constants.BUNDLE_CONSTANT_AUTH_TOKEN, "");
        residentSignInDetails.pushToken = _bundle.getString(Constants.BUNDLE_CONSTANT_PUSH_TOKEN, "");
    }

    @Override
    public LoginResponse loadInBackground() {

        CityApi cityApi = RestClientApi.getCityApi();
        LoginResponse response;

        try {
            response = cityApi.loginResidentAndGetAuthToken(residentSignInDetails);
            if (response == null) {
                response = new LoginResponse();
            }
        } catch (RetrofitError e) {
            e.printStackTrace();
            response = null;
        }
        return  response;
    }
}
