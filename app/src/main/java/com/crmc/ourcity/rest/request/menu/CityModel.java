package com.crmc.ourcity.rest.request.menu;

public class CityModel {
    public Resident resident;
    public String lang;

    public CityModel(Resident resident, String lang){
        this.resident = resident;
        this.lang = lang;
    }
}