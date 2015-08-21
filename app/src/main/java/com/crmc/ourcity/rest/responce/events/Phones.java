package com.crmc.ourcity.rest.responce.events;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SetKrul on 30.07.2015.
 */
public class Phones {

    @SerializedName("email")
    public String emailAddress;
    @SerializedName("entityId")
    public Integer entityId;
    @SerializedName("name")
    public String entityName;
    @SerializedName("address")
    public String address;
    @SerializedName("phoneNumber")
    public String phoneNumber;
}
