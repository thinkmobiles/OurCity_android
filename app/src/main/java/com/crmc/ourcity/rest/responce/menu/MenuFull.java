package com.crmc.ourcity.rest.responce.menu;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by SetKrul on 29.07.2015.
 */
public class MenuFull {

    @SerializedName("Nodes")
    public List<MenuModel> nodes;

    @SerializedName("SplashScreen")
    public String splashScreen;
}
