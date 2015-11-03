package com.crmc.ourcity.loader;

import android.content.Context;
import android.os.Bundle;

import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.RestClientApi;
import com.crmc.ourcity.rest.api.CityApi;
import com.crmc.ourcity.rest.request.logout.LogoutModel;
import com.crmc.ourcity.utils.SPManager;

import retrofit.RetrofitError;

public class LogoutLoader extends BaseLoader<Boolean> {

    private LogoutModel logoutModel;

    public LogoutLoader(Context _context, Bundle _bundle) {
        super(_context);
        logoutModel = new LogoutModel();
        logoutModel.authToken = _bundle.getString(Constants.BUNDLE_CONSTANT_AUTH_TOKEN);
    }

    @Override
    public Boolean loadInBackground() {
        CityApi cityApi = RestClientApi.getCityApi();
        Boolean answerFromServerWhenLogout;

        try {
            answerFromServerWhenLogout = cityApi.logout(logoutModel);
        } catch (RetrofitError e) {
            answerFromServerWhenLogout = false;
        }
        return answerFromServerWhenLogout;
    }
}
