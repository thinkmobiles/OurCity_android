package com.crmc.ourcity.rest.responce;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SetKrul on 24.07.2015.
 */
public class AddressModel {
    public String city;
    @SerializedName("road")
    public String street;
    @SerializedName("house_number")
    public String house;
}
