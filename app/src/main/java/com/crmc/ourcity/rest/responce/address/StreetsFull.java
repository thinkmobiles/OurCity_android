package com.crmc.ourcity.rest.responce.address;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StreetsFull {

    @SerializedName("ResultObjects")
    public List<StreetsItem> streetsList;
}
