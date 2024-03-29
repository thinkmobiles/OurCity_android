package com.crmc.ourcity.rest.responce.menu;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MenuFull {

    @SerializedName("Nodes")
    public List<MenuModel> nodes;
    @SerializedName("SplashScreen")
    public String splashScreen;

    public int getSize() {
        return nodes != null ? nodes.size() : 0;
    }

    public List<MenuModel> getNodes() {
        return nodes;
    }
}