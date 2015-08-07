package com.crmc.ourcity.loader;

import android.content.Context;
import android.os.Bundle;

import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.RestClientApi;
import com.crmc.ourcity.rest.api.CityApi;
import com.crmc.ourcity.rest.request.events.EventsModel;
import com.crmc.ourcity.rest.request.events.EventsType;
import com.crmc.ourcity.rest.responce.events.Site;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;

/**
 * Created by SetKrul on 30.07.2015.
 */
public class SiteLoader extends BaseLoader<List<Site>> {

    private int cityNumber;
    private String type;

    public SiteLoader(Context _context, Bundle _args) {
        super(_context);
        cityNumber = _args.getInt(Constants.BUNDLE_CONSTANT_CITY_NUMBER);
        type = _args.getString(Constants.BUNDLE_CONSTANT_TYPE);
    }

    @Override
    public List<Site> loadInBackground() {
        CityApi api = RestClientApi.getCityApi();
        List<Site> mSite;
        try {
            mSite =  api.getSites(new EventsModel(new EventsType(cityNumber, type)));
        } catch (RetrofitError _e) {
            mSite = new ArrayList<>();
            mSite.add(new Site());
        }
        return mSite;
    }
}