package com.crmc.ourcity.rest.responce.address;

import com.google.gson.annotations.SerializedName;

public class StreetsItem {

    @SerializedName("StreetID")
    public Integer streetId;
    @SerializedName("StreetName")
    public String streetName;
}
