package com.crmc.ourcity.loader;

import android.content.Context;
import android.os.Bundle;

import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.RestClientApi;
import com.crmc.ourcity.rest.api.CityApi;
import com.crmc.ourcity.rest.responce.events.PhoneBook;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.mime.TypedByteArray;

public class PhoneBookLoader extends BaseLoader<List<PhoneBook>> {

    private String json;
    private String route;

    public PhoneBookLoader(Context _context, Bundle _args) {
        super(_context);
        json = _args.getString(Constants.BUNDLE_CONSTANT_REQUEST_JSON);
        route = _args.getString(Constants.BUNDLE_CONSTANT_REQUEST_ROUTE);
    }

    @Override
    public List<PhoneBook> loadInBackground() {
        CityApi api = RestClientApi.getCityApi();
        List<PhoneBook> mPhoneBooks;
        try {
            mPhoneBooks =  api.getPhoneBook(route, new TypedByteArray("application/json", json.getBytes("UTF-8")));
        } catch (RetrofitError | UnsupportedEncodingException _e) {
            mPhoneBooks = new ArrayList<>();
        }
        return mPhoneBooks;
    }
}
