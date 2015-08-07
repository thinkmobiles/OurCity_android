package com.crmc.ourcity.loader;

import android.content.Context;
import android.os.Bundle;

import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.RestClientApi;
import com.crmc.ourcity.rest.api.CityApi;
import com.crmc.ourcity.rest.request.base.BaseCity;
import com.crmc.ourcity.rest.request.base.BaseModel;
import com.crmc.ourcity.rest.responce.events.Phones;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;

/**
 * Created by SetKrul on 30.07.2015.
 */
public class PhonesLoader extends BaseLoader<List<Phones>> {

    private int cityNumber;

    public PhonesLoader(Context _context, Bundle _args) {
        super(_context);
        cityNumber = _args.getInt(Constants.BUNDLE_CONSTANT_CITY_NUMBER);
    }

    @Override
    public List<Phones> loadInBackground() {
        CityApi api = RestClientApi.getCityApi();
        List<Phones> mPhones;
        try {
            mPhones =  api.getPhones(new BaseModel(new BaseCity(cityNumber)));
        } catch (RetrofitError e) {
            mPhones = new ArrayList<>();
            mPhones.add(new Phones());
        }
        return mPhones;
    }
}