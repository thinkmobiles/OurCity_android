package com.crmc.ourcity.loader;

import android.content.Context;
import android.os.Bundle;

import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.RestClientApi;
import com.crmc.ourcity.rest.api.CityApi;
import com.crmc.ourcity.rest.request.base.BaseCity;
import com.crmc.ourcity.rest.request.base.BaseModel;
import com.crmc.ourcity.rest.responce.vote.VoteFull;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;

/**
 * Created by SetKrul on 30.07.2015.
 */
public class VoteLoader extends BaseLoader<List<VoteFull>> {

    private int cityNumber;

    public VoteLoader(Context context, Bundle args) {
        super(context);
        cityNumber = args.getInt(Constants.BUNDLE_CONSTANT_CITY_NUMBER);
    }

    @Override
    public List<VoteFull> loadInBackground() {
        CityApi api = RestClientApi.getCityApi();
        List<VoteFull> mVote;
        try {
            mVote =  api.getVote(new BaseModel(new BaseCity(cityNumber)));
        } catch (RetrofitError e) {
            mVote = new ArrayList<>();
            mVote.add(new VoteFull());
        }
        return mVote;
    }
}