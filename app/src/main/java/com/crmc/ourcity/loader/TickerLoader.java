package com.crmc.ourcity.loader;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.RestClientApi;
import com.crmc.ourcity.rest.api.CityApi;
import com.crmc.ourcity.rest.request.base.BaseCity;
import com.crmc.ourcity.rest.request.base.BaseModel;
import com.crmc.ourcity.rest.responce.ticker.TickerModel;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;

public class TickerLoader extends BaseLoader<ArrayList<TickerModel>> {

    private int cityNumber;

    public TickerLoader(Context _context, Bundle _args) {
        super(_context);
        cityNumber = _args.getInt(Constants.BUNDLE_CONSTANT_CITY_NUMBER);
    }

    @Override
    public ArrayList<TickerModel> loadInBackground() {
        CityApi api = RestClientApi.getCityApi();
        ArrayList<TickerModel> tickers;

        try {
            tickers = api.getTickers(new BaseModel(new BaseCity(cityNumber)));
        } catch (RetrofitError e) {
            tickers = new ArrayList<>();
        }
        return tickers;
    }
}
