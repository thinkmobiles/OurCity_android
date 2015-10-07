package com.crmc.ourcity.loader;

import android.content.Context;
import android.os.Bundle;

import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.RestClientApi;
import com.crmc.ourcity.rest.api.CityApi;
import com.crmc.ourcity.rest.request.vote.VoteAlready;

import retrofit.RetrofitError;

/**
 * Created by SetKrul on 11.09.2015.
 */
public class VoteAlreadyLoader extends BaseLoader<String> {

    private int residentId;
    private int surveyId;

    public VoteAlreadyLoader(Context _context, Bundle _args) {
        super(_context);
        residentId = _args.getInt(Constants.BUNDLE_CONSTANT_RESIDENT_ID);
        surveyId = _args.getInt(Constants.BUNDLE_CONSTANT_SURVEY_ID);
    }

    @Override
    public String loadInBackground() {
        CityApi api = RestClientApi.getCityApi();
        String result;
        try {
            result =  api.alreadyVoted(new VoteAlready(residentId, surveyId));
        } catch (RetrofitError _e) {
            result = null;
        }
        return result;
    }
}