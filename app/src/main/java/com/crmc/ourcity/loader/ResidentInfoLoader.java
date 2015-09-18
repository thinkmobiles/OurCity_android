package com.crmc.ourcity.loader;

import android.content.Context;
import android.os.Bundle;

import com.crmc.ourcity.global.Constants;

import com.crmc.ourcity.rest.RestClientApi;
import com.crmc.ourcity.rest.api.CityApi;
import com.crmc.ourcity.rest.request.registration.ResidentDetails;
import com.crmc.ourcity.rest.request.resident.ResidentModel;

import retrofit.RetrofitError;

/**
 * Created by andrei on 18.09.15.
 */
public class ResidentInfoLoader extends BaseLoader<ResidentDetails> {

    private ResidentModel resident;

    public ResidentInfoLoader(Context _context, Bundle bundle) {
        super(_context);
        int cityNumber = bundle.getInt(Constants.BUNDLE_CONSTANT_CITY_NUMBER);
        int residentId = bundle.getInt(Constants.BUNDLE_CONSTANT_RESIDENT_ID);
        resident = new ResidentModel(new com.crmc.ourcity.rest.request.resident.ResidentDetails(residentId, cityNumber));
    }

    @Override
    public ResidentDetails loadInBackground() {
        CityApi api = RestClientApi.getCityApi();
        ResidentDetails residentDetails;

        try {
            residentDetails = api.getResidentInfo(resident);
        } catch (RetrofitError e) {
            residentDetails = null;
        }
        return residentDetails;
    }
}
