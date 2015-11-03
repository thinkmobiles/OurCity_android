package com.crmc.ourcity.loader;

import android.content.Context;
import android.os.Bundle;

import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.RestClientApi;
import com.crmc.ourcity.rest.api.CityApi;
import com.crmc.ourcity.rest.request.appeals.CreateNewTicketWrapper;
import com.crmc.ourcity.rest.request.base.BaseCity;
import com.crmc.ourcity.rest.request.base.BaseModel;
import com.crmc.ourcity.rest.request.menu.Resident;
import com.crmc.ourcity.rest.request.resident.ResidentDetails;
import com.crmc.ourcity.rest.request.resident.ResidentModel;
import com.crmc.ourcity.rest.responce.interestAreas.InterestingArea;
import com.crmc.ourcity.rest.responce.interestAreas.ResidentResponse;

import java.util.List;

import retrofit.RetrofitError;

public class InterestingAreaLoader extends BaseLoader<ResidentResponse> {

    private ResidentModel resident;
    private int cityNumber;
    private int residentId;


    public InterestingAreaLoader(Context _context, Bundle _args) {
        super(_context);
        cityNumber = _args.getInt(Constants.BUNDLE_CONSTANT_CITY_NUMBER);
        residentId = _args.getInt(Constants.BUNDLE_CONSTANT_RESIDENT_ID);
        resident = new ResidentModel(new ResidentDetails(residentId, cityNumber));
    }

    @Override
    public ResidentResponse loadInBackground() {
        CityApi api = RestClientApi.getCityApi();
        ResidentResponse response;


        try {
            response  = api.getResidentById(resident);
        } catch (RetrofitError e) {
            response = null;
        }

        return response;
    }
}
