package com.crmc.ourcity.rest.responce.map;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SetKrul on 29.07.2015.
 */
public class MapInterestedPoint {

    @SerializedName("description")
    public String description;
    @SerializedName("interestPointId")
    public Integer interestPointId;
    @SerializedName("lat")
    public String lat;
    @SerializedName("lon")
    public String lon;
}
