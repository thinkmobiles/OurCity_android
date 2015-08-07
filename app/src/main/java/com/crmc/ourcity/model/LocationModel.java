package com.crmc.ourcity.model;

/**
 * Created by SetKrul on 15.07.2015.
 */
public class LocationModel {

    public double lat;
    public double lng;
    public String nameCity;
    public String nameStreet;
    public String numberHouse;

    public LocationModel(double _lat, double _lng, String _nameCity, String _nameStreet, String _numberHouse){
        this.lat = _lat;
        this.lng = _lng;
        this.nameCity = _nameCity;
        this.nameStreet = _nameStreet;
        this.numberHouse = _numberHouse;
    }
}
