package com.crmc.ourcity.loader;

import android.content.Context;
import android.os.Bundle;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.RestClientApi;
import com.crmc.ourcity.rest.api.CityApi;
import com.crmc.ourcity.rest.request.base.BaseCity;
import com.crmc.ourcity.rest.request.base.BaseModel;
import com.crmc.ourcity.rest.responce.crmcCredentials.CRMCCredentials;

import retrofit.RetrofitError;

/**
 * Created by andrei on 07.10.15.
 */
public class CrmcCredentialsLoader extends BaseLoader<CRMCCredentials> {

    private int cityNumber;

    public CrmcCredentialsLoader(Context _context, Bundle _args) {
        super(_context);
        cityNumber = _args.getInt(Constants.BUNDLE_CONSTANT_CITY_NUMBER);
    }

    @Override
    public CRMCCredentials loadInBackground() {
        CityApi api = RestClientApi.getCityApi();
        CRMCCredentials credentials;
        try {
            credentials = api.getCrmcCredentials(new BaseModel(new BaseCity(cityNumber)));
        } catch (RetrofitError e ) {
            credentials = new CRMCCredentials();
        }
        return credentials;
    }
}
