package com.crmc.ourcity.loader;

import android.content.Context;
import android.os.Bundle;

import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.RestClientApi;
import com.crmc.ourcity.rest.api.CityApi;
import com.crmc.ourcity.rest.request.vote.VoteDetails;
import com.crmc.ourcity.rest.request.vote.VoteModel;

import retrofit.RetrofitError;

/**
 * Created by SetKrul on 30.07.2015.
 */
public class VoteReplyLoader extends BaseLoader<String> {

    private int selectedOptionId;
    private int age;
    private int gender;

    public VoteReplyLoader(Context context, Bundle args) {
        super(context);
        selectedOptionId = args.getInt(Constants.BUNDLE_CONSTANT_SELECTED_OPTION_ID);
        age = args.getInt(Constants.BUNDLE_CONSTANT_AGE);
        gender = args.getInt(Constants.BUNDLE_CONSTANT_GENDER);
    }

    @Override
    public String loadInBackground() {
        CityApi api = RestClientApi.getCityApi();
        String result;
        try {
            result =  api.replyVote(new VoteModel(new VoteDetails(selectedOptionId, age, gender)));
        } catch (RetrofitError e) {
            result = null;
        }
        return result;
    }
}