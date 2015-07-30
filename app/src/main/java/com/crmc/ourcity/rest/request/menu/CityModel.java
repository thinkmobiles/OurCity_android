package com.crmc.ourcity.rest.request.menu;

/**
 * Created by SetKrul on 28.07.2015.
 */
public class CityModel {
    public CityNumber city;
    public String lng;

    public CityModel(CityNumber city, String lng){
        this.city = city;
        this.lng = lng;
    }
}