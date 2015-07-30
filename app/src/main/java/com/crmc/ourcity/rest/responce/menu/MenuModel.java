package com.crmc.ourcity.rest.responce.menu;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SetKrul on 29.07.2015.
 */
public class MenuModel {

    @SerializedName("ActionType")
    public Integer actionType;
    @SerializedName("Color")
    public String colorItem;
    @SerializedName("Link")
    public String link;
    @SerializedName("ListType")
    public Integer listType;
    @SerializedName("Picture")
    public String iconItem;
    @SerializedName("Title")
    public String title;
    @SerializedName("MapLatitude")
    public String lat;
    @SerializedName("MapLongitude")
    public String lon;
}
