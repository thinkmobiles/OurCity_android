package com.crmc.ourcity.loader;

import android.content.Context;
import android.os.Bundle;

import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.RestClientApi;
import com.crmc.ourcity.rest.api.CityApi;
import com.crmc.ourcity.rest.request.registration.ResidentDetails;
import com.crmc.ourcity.rest.request.registration.ResidentModel;

import retrofit.RetrofitError;

/**
 * Created by andrei on 18.09.15.
 */
public class UpdateResidentInfoLoader extends BaseLoader<Boolean> {
    private ResidentDetails residentDetails;

    public UpdateResidentInfoLoader(Context _context, Bundle _bundle) {
        super(_context);
        residentDetails = new ResidentDetails();

        residentDetails.firstName = _bundle.getString(Constants.BUNDLE_CONSTANT_FIRST_NAME, "default value");
        residentDetails.lastName = _bundle.getString(Constants.BUNDLE_CONSTANT_LAST_NAME, "default value");
        residentDetails.userName = _bundle.getString(Constants.BUNDLE_CONSTANT_USER_NAME, "default value");
        residentDetails.password = _bundle.getString(Constants.BUNDLE_CONSTANT_PASSWORD, "default value");
        residentDetails.email = _bundle.getString(Constants.BUNDLE_CONSTANT_EMAIL, "default value");
        residentDetails.phoneNumber = _bundle.getString(Constants.BUNDLE_CONSTANT_PHONE_NUMBER, "default value");
        residentDetails.mobileNumber = _bundle.getString(Constants.BUNDLE_CONSTANT_MOBILE_NUMBER, "default value");
        residentDetails.houseNumber = _bundle.getString(Constants.BUNDLE_CONSTANT_HOUSE_NUMBER, "default value");
        residentDetails.isGetGlobalNotification = _bundle.getBoolean(Constants.BUNDLE_CONSTANT_GLOBAL_NOTIFICATION_NEEDED, false);
        residentDetails.isGetPersonalNotification = _bundle.getBoolean(Constants.BUNDLE_CONSTANT_PERSONAL_NOTIFICATION_NEEDED, false);
        residentDetails.streetId = _bundle.getInt(Constants.BUNDLE_CONSTANT_STREET_ID, 0);
        residentDetails.residentId = _bundle.getInt(Constants.BUNDLE_CONSTANT_RESIDENT_ID, 0);
        residentDetails.cityId = _bundle.getInt(Constants.BUNDLE_CONSTANT_CITY_ID, 1);
    }

    @Override
    public Boolean loadInBackground() {
        CityApi api = RestClientApi.getCityApi();
        boolean response;
        try {
            response = api.updateResidentInfo(new ResidentModel(residentDetails));
        } catch (RetrofitError e) {
            response = false;
        }
        return response;
    }
}
