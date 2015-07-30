package com.crmc.ourcity.loader;

import android.content.Context;
import android.os.Bundle;

import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.RestClientApi;
import com.crmc.ourcity.rest.api.CityApi;
import com.crmc.ourcity.rest.request.base.BaseCity;
import com.crmc.ourcity.rest.request.base.BaseModel;
import com.crmc.ourcity.rest.responce.events.News;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;

/**
 * Created by SetKrul on 30.07.2015.
 */
public class NewsLoader extends BaseLoader<List<News>> {

    private int cityNumber;

    public NewsLoader(Context context, Bundle args) {
        super(context);
        cityNumber = args.getInt(Constants.BUNDLE_CONSTANT_CITY_NUMBER);
    }

    @Override
    public List<News> loadInBackground() {
        CityApi api = RestClientApi.getCityApi();
        List<News> mNews;
        try {
            mNews =  api.getNews(new BaseModel(new BaseCity(cityNumber)));
        } catch (RetrofitError e) {
            mNews = new ArrayList<>();
            mNews.add(new News());
        }
        return mNews;
    }
}