package com.crmc.ourcity.rest.request.vote;

/**
 * Created by SetKrul on 30.07.2015.
 */
public class VoteDetails {
    public int selectedOptionId;
    public int age;
    public int gender;

    public VoteDetails(int selectedOptionId, int age, int gender) {
        this.selectedOptionId = selectedOptionId;
        this.age = age;
        this.gender = gender;
    }
}
