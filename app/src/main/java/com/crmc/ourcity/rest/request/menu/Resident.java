package com.crmc.ourcity.rest.request.menu;

/**
 * Created by SetKrul on 28.07.2015.
 */
public class Resident {
    public int residentId;
    public int cityId;

    public Resident(int residentId, int cityId) {
        this.residentId = residentId;
        this.cityId = cityId;
    }
}
