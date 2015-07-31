package com.crmc.ourcity.rest.responce.map;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SetKrul on 30.07.2015.
 */
public class MapTripsDetails {

    @SerializedName("lat")
    public String lat;
    @SerializedName("latLangInfo")
    public String latLangInfo;
    @SerializedName("latLngId")
    public Integer latLngId;
    @SerializedName("latLngName")
    public String latLngName;
    @SerializedName("latLngType")
    public Integer latLngType;
    @SerializedName("lng")
    public String lng;
    @SerializedName("tripId")
    public Integer tripId;
}
