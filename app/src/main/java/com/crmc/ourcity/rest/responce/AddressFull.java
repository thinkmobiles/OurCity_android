package com.crmc.ourcity.rest.responce;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SetKrul on 24.07.2015.
 */
public class AddressFull {

    @SerializedName("address")
    public AddressModel addressList;

    public String getCity() {
        return addressList.city;
    }

    public String getStreet() {
        return addressList.street;
    }

    public String getHouse() {
        return addressList.house;
    }
}