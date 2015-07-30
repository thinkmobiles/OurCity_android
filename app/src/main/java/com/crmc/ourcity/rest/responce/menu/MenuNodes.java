package com.crmc.ourcity.rest.responce.menu;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by SetKrul on 29.07.2015.
 */
public class MenuNodes {

    @SerializedName("Nodes")
    public List<MenuFull> nodes;
}
