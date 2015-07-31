package com.crmc.ourcity.rest.responce.vote;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by SetKrul on 30.07.2015.
 */
public class VoteFull {

    @SerializedName("optionsList")
    public List<VoteDetails> optionsList;

    @SerializedName("imagePath")
    public String imagePath;
    @SerializedName("isActive")
    public Boolean isActive;
    @SerializedName("surveyId")
    public Integer surveyId;
    @SerializedName("surveyTitle")
    public String surveyTitle;
}
