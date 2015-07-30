package com.crmc.ourcity.rest.responce.vote;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SetKrul on 30.07.2015.
 */
public class VoteItem {

    @SerializedName("image")
    public String image;
    @SerializedName("optionDescroption")
    public String optionDescription;
    @SerializedName("optionId")
    public Integer optionId;
    @SerializedName("surveyId")
    public Integer surveyId;
    @SerializedName("surveyOptionId")
    public Integer surveyOptionId;
    @SerializedName("votePercent")
    public Integer votePercent;
}
