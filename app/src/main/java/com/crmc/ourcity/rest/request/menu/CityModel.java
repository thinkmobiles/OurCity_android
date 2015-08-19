package com.crmc.ourcity.rest.request.menu;

/**
 * Created by SetKrul on 28.07.2015.
 */
public class CityModel {
    public Resident resident;
    public String lang;

    public CityModel(Resident resident, String lang){
        this.resident = resident;
        this.lang = lang;
    }
}