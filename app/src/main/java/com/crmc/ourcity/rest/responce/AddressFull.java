package com.crmc.ourcity.rest.responce;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SetKrul on 24.07.2015.
 */
public class AddressFull {

    @SerializedName("address")
    public AddressModel addressList;

    public String getCity(){
//        if (addressList != null && addressList.size() > 0) {
        return addressList/*.get(0)*/.city;
//        }
//        return null;
    }

    public String getStreet(){
//        if (addressList != null && addressList.size() > 0) {
        return addressList/*.get(0)*/.street;
//        }
//        return null;
    }

    public String getHouse(){
//        if (addressList != null && addressList.size() > 0) {
        return addressList/*.get(0)*/.house;
//        }
//        return null;
    }

}