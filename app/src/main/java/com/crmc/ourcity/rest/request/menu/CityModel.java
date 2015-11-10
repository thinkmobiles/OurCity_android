package com.crmc.ourcity.rest.request.menu;

public class CityModel {
    public Resident resident;
    public String lng;

    public CityModel(Resident resident, String lang){
        this.resident = resident;
        this.lng = lang;
    }
}