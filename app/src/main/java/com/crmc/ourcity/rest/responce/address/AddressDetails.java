package com.crmc.ourcity.rest.responce.address;

import com.google.gson.annotations.SerializedName;

public class AddressDetails {
    @SerializedName("city")
    public String city;
    @SerializedName("town")
    public String town;
    @SerializedName("road")
    public String street;
    @SerializedName("house_number")
    public String house;
}
