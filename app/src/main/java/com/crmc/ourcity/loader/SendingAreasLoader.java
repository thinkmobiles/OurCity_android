package com.crmc.ourcity.loader;

import android.content.Context;
import android.os.Bundle;

import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.RestClientApi;
import com.crmc.ourcity.rest.api.CityApi;
import com.crmc.ourcity.rest.request.resident.ResidentDetails;
import com.crmc.ourcity.rest.request.resident.ResidentModel;
import com.crmc.ourcity.rest.responce.interestAreas.InterestingArea;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;

public class SendingAreasLoader extends BaseLoader<Boolean> {
    private ResidentModel resident;
    private ResidentDetails residentDetails;
    private int cityNumber;
    private int residentId;


    public SendingAreasLoader(Context _context, Bundle bundle) {
        super(_context);

        cityNumber = bundle.getInt(Constants.BUNDLE_CONSTANT_CITY_NUMBER);
        residentId = bundle.getInt(Constants.BUNDLE_CONSTANT_RESIDENT_ID);
        residentDetails = new ResidentDetails(residentId, cityNumber);
        residentDetails.interestAreasList = (List)bundle.getParcelableArrayList(Constants.BUNDLE_SELECTED_AREAS);
        resident = new ResidentModel(residentDetails);
    }

    @Override
    public Boolean loadInBackground() {
        CityApi api = RestClientApi.getCityApi();
        boolean response;
        try {
             response = api.sendSelectedInterestingAreas(resident);
        } catch (RetrofitError e) {
            response = false;
        }
        return response;
    }
}
