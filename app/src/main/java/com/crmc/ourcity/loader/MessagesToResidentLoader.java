package com.crmc.ourcity.loader;

import android.content.Context;
import android.os.Bundle;

import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.RestClientApi;
import com.crmc.ourcity.rest.api.CityApi;
import com.crmc.ourcity.rest.request.resident.ResidentDetails;
import com.crmc.ourcity.rest.request.resident.ResidentModel;
import com.crmc.ourcity.rest.responce.events.MassageToResident;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;

/**
 * Created by SetKrul on 30.07.2015.
 */
public class MessagesToResidentLoader extends BaseLoader<List<MassageToResident>> {

    private int residentId;

    public MessagesToResidentLoader(Context _context, Bundle _args) {
        super(_context);
        residentId = _args.getInt(Constants.BUNDLE_CONSTANT_RESIDENT_ID);
    }

    @Override
    public List<MassageToResident> loadInBackground() {
        CityApi api = RestClientApi.getCityApi();
        List<MassageToResident> mMassageToResident;
        try {
            mMassageToResident =  api.getMessagesToResident(new ResidentModel(new ResidentDetails(residentId)));
        } catch (RetrofitError e) {
            mMassageToResident = new ArrayList<>();
            mMassageToResident.add(new MassageToResident());
        }
        return mMassageToResident;
    }
}