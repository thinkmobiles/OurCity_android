package com.crmc.ourcity.rest.responce.events;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SetKrul on 30.07.2015.
 */
public class Phones {

    @SerializedName("details")
    public String details;
    @SerializedName("emailAddress")
    public String emailAddress;
    @SerializedName("entityId")
    public Integer entityId;
    @SerializedName("entityName")
    public String entityName;
    @SerializedName("information")
    public String information;
    @SerializedName("phoneNumber")
    public String phoneNumber;
}
