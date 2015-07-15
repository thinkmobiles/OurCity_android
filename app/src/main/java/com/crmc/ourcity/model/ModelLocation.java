package com.crmc.ourcity.model;

/**
 * Created by SetKrul on 15.07.2015.
 */
public class ModelLocation {

    public double lat;
    public double lng;
    public String nameCity;

    public ModelLocation(double lat, double lng, String nameCity){
        this.lat = lat;
        this.lng = lng;
        this.nameCity = nameCity;
    }
}
