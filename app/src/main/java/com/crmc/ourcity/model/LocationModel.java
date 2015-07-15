package com.crmc.ourcity.model;

/**
 * Created by SetKrul on 15.07.2015.
 */
public class LocationModel {

    public double lat;
    public double lng;
    public String nameCity;

    public LocationModel(double lat, double lng, String nameCity){
        this.lat = lat;
        this.lng = lng;
        this.nameCity = nameCity;
    }
}
