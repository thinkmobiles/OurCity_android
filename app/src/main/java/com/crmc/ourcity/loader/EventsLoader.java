package com.crmc.ourcity.loader;

import android.content.Context;
import android.os.Bundle;

import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.RestClientApi;
import com.crmc.ourcity.rest.api.CityApi;
import com.crmc.ourcity.rest.responce.events.Events;

import java.io.UnsupportedEncodingException;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.mime.TypedByteArray;

/**
 * Created by SetKrul on 30.07.2015.
 */
public class EventsLoader extends BaseLoader<List<Events>> {

    private String json;
    private String route;

    public EventsLoader(Context _context, Bundle _args) {
        super(_context);
        json = _args.getString(Constants.BUNDLE_CONSTANT_REQUEST_JSON);
        route = _args.getString(Constants.BUNDLE_CONSTANT_REQUEST_ROUTE);
    }

    @Override
    public List<Events> loadInBackground() {
        CityApi api = RestClientApi.getCityApi();
        List<Events> mEvents;
        try {
            mEvents =  api.getEvents(route, new TypedByteArray("application/json", json.getBytes("UTF-8")));
        } catch (RetrofitError | UnsupportedEncodingException _e) {
            mEvents = null;
        }
        return mEvents;
    }
}