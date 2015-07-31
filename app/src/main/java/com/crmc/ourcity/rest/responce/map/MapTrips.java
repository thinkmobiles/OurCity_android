package com.crmc.ourcity.rest.responce.map;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by SetKrul on 30.07.2015.
 */
public class MapTrips {

    @SerializedName("latLngList")
    public List<MapTripsDetails> mapTripsDetails;

    @SerializedName("description")
    public String description;
    @SerializedName("tripId")
    public Integer tripId;
    @SerializedName("tripName")
    public String tripName;
}
