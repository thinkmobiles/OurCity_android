package com.crmc.ourcity.loader;

import android.content.Context;
import android.os.Bundle;

import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.RestClientApi;
import com.crmc.ourcity.rest.api.CityApi;
import com.crmc.ourcity.rest.responce.vote.VoteFull;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.mime.TypedByteArray;

/**
 * Created by SetKrul on 30.07.2015.
 */
public class VoteLoader extends BaseLoader<List<VoteFull>> {

    private String json;
    private String route;

    public VoteLoader(Context _context, Bundle _args) {
        super(_context);
        json = _args.getString(Constants.BUNDLE_CONSTANT_REQUEST_JSON);
        route = _args.getString(Constants.BUNDLE_CONSTANT_REQUEST_ROUTE);
    }

    @Override
    public List<VoteFull> loadInBackground() {
        CityApi api = RestClientApi.getCityApi();
        List<VoteFull> mVote;
        try {
            mVote =  api.getVote(route, new TypedByteArray("application/json", json.getBytes("UTF-8")));
        } catch (RetrofitError | UnsupportedEncodingException _e) {
            mVote = new ArrayList<>();
        }
        return mVote;
    }
}