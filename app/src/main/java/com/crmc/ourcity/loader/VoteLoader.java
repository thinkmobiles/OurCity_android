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
import retrofit.mime.TypedInput;

/**
 * Created by SetKrul on 30.07.2015.
 */
public class VoteLoader extends BaseLoader<List<VoteFull>> {

    private int cityNumber;

    public VoteLoader(Context _context, Bundle _args) {
        super(_context);
        cityNumber = _args.getInt(Constants.BUNDLE_CONSTANT_CITY_NUMBER);
    }

    @Override
    public List<VoteFull> loadInBackground() {
        CityApi api = RestClientApi.getCityApi();
        List<VoteFull> mVote = null;
        try {

            String json = "{\"city\":{\"cityId\":1,\"cityName\":null,\"lastModifyUserId\":0}}";
            TypedInput in = new TypedByteArray("application/json", json.getBytes("UTF-8"));
            mVote =  api.getVote(in);
        } catch (RetrofitError _e) {
            mVote = new ArrayList<>();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return mVote;
    }
//    public class TypedJsonString extends TypedString {
//        public TypedJsonString(String body) {
//            super(body);
//        }
//
//        @Override public String mimeType() {
//            return "application/json";
//        }
//    }
}