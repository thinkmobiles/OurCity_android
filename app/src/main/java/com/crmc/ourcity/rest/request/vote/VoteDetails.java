package com.crmc.ourcity.rest.request.vote;

public class VoteDetails {
    public int selectedOptionId;
    public int age;
    public int gender;
    public int lastModifyUserId;

    public VoteDetails(int selectedOptionId, int age, int gender, int lastModifyUserId) {
        this.selectedOptionId = selectedOptionId;
        this.age = age;
        this.gender = gender;
        this.lastModifyUserId = lastModifyUserId;
    }
}
