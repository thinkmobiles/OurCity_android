package com.crmc.ourcity.rest.responce.map;

import com.google.gson.annotations.SerializedName;

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
