package com.crmc.ourcity.rest.responce.map;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by SetKrul on 29.07.2015.
 */
public class MapCategory {

    @SerializedName("CategoryId")
    public Integer categoryId;
    @SerializedName("categoryName")
    public String categoryName;
    @SerializedName("icon")
    public String icon;
    @SerializedName("interestPoint")
    public List<MapInterestedPoint> interestedPointList;

    public Integer getCategoryId() {
        return categoryId;
    }

    public String getCategoryName(){
        return categoryName;
    }

    public String getIcon(){
        return icon;
    }

    public List<MapInterestedPoint> getInterestedPointList(){
        return interestedPointList;
    }
}
