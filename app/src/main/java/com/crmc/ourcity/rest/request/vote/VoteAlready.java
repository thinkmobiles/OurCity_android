package com.crmc.ourcity.rest.request.vote;

/**
 * Created by SetKrul on 11.09.2015.
 */
public class VoteAlready {
    public Integer residentId;
    public Integer surveyId;

    public VoteAlready(int _residentId, int _surveyId){
        this.residentId = _residentId;
        this.surveyId = _surveyId;
    }
}
