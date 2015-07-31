package com.crmc.ourcity.rest.responce.address;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SetKrul on 31.07.2015.
 */
public class StreetsItem {

    @SerializedName("StreetID")
    public Integer streetId;
    @SerializedName("StreetName")
    public String streetName;
}
