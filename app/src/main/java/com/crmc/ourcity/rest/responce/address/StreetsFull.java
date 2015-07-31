package com.crmc.ourcity.rest.responce.address;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by SetKrul on 31.07.2015.
 */
public class StreetsFull {

    @SerializedName("ResultObjects")
    public List<StreetsItem> streetsList;
}
