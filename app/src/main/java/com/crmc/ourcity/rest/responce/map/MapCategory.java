package com.crmc.ourcity.rest.responce.map;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by SetKrul on 29.07.2015.
 */
public class MapCategory {

    @SerializedName("interestPoint")
    public List<MapInterestedPoint> interestedPointList;

    @SerializedName("categoryId")
    public Integer categoryId;
    @SerializedName("categoryName")
    public String categoryName;
    @SerializedName("icon")
    public String icon;

    public List<MapInterestedPoint> getInterestedPointList(){
        return interestedPointList;
    }

    public Double getInterestedPointLat(int element){
        return Double.parseDouble((interestedPointList.get(element).lat).replace(',', '.'));
    }

    public Double getInterestedPointLon(int element){
        return Double.parseDouble((interestedPointList.get(element).lon).replace(',', '.'));
    }

    public String getInterestedPointDescription(int element){
        return interestedPointList.get(element).description;
    }

}
