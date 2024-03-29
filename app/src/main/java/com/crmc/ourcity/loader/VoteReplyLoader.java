package com.crmc.ourcity.loader;

import android.content.Context;
import android.os.Bundle;

import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.RestClientApi;
import com.crmc.ourcity.rest.api.CityApi;
import com.crmc.ourcity.rest.request.vote.VoteDetails;
import com.crmc.ourcity.rest.request.vote.VoteModel;

import retrofit.RetrofitError;

public class VoteReplyLoader extends BaseLoader<String> {

    private int selectedOptionId;
    private int age;
    private int gender;
    private int lastModifyUserId;

    public VoteReplyLoader(Context _context, Bundle _args) {
        super(_context);
        selectedOptionId = _args.getInt(Constants.BUNDLE_CONSTANT_SELECTED_OPTION_ID);
        age = _args.getInt(Constants.BUNDLE_CONSTANT_AGE);
        gender = _args.getInt(Constants.BUNDLE_CONSTANT_GENDER);
        lastModifyUserId = _args.getInt(Constants.BUNDLE_CONSTANT_LAST_USER);
    }

    @Override
    public String loadInBackground() {
        CityApi api = RestClientApi.getCityApi();
        String result;
        try {
            result =  api.replyVote(new VoteModel(new VoteDetails(selectedOptionId, age, gender, lastModifyUserId)));
        } catch (RetrofitError _e) {
            result = null;
        }
        return result;
    }
}