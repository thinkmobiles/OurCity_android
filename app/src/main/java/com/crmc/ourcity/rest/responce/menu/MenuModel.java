package com.crmc.ourcity.rest.responce.menu;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SetKrul on 29.07.2015.
 */
public class MenuModel {

    @SerializedName("Nodes")
    public List<MenuModel> menu;

    @SerializedName("ActionType")
    public Integer actionType;
    @SerializedName("Color")
    public String colorItem;
    @SerializedName("Link")
    public String link;
    @SerializedName("ListType")
    public Integer listType;
    @SerializedName("MapLatitude")
    public String lat;
    @SerializedName("MapLongitude")
    public String lon;
    @SerializedName("Picture")
    public String iconItem;
    @SerializedName("RequestJson")
    public String requestJson;
    @SerializedName("RequestRoute")
    public String requestRoute;
    @SerializedName("SplashScreen")
    public String splashScreen;
    @SerializedName("Title")
    public String title;

    public List<MenuModel> getMenu(){
        if (menu != null){
            return menu;
        } else {
            menu = new ArrayList<>();
            menu.add(new MenuModel());
            return menu;
        }
    }

    public Double getLat(){
        if (lat != null) {
            return Double.parseDouble(lat.replace(',', '.'));
        }
        return null;
    }

    public Double getLon(){
        if (lon != null) {
            return Double.parseDouble(lon.replace(',','.'));
        }
        return null;
    }

}
