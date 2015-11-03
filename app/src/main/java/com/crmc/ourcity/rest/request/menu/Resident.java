package com.crmc.ourcity.rest.request.menu;

public class Resident {
    public int residentId;
    public int cityId;

    public Resident(int residentId, int cityId) {
        this.residentId = residentId;
        this.cityId = cityId;
    }
}
