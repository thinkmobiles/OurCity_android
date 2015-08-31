package com.crmc.ourcity.rest.responce.events;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SetKrul on 31.08.2015.
 */
public class CityEntities {
    @SerializedName("cityId")
    public Integer cityId;
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
