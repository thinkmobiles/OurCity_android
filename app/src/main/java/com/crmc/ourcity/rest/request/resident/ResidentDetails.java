package com.crmc.ourcity.rest.request.resident;

import com.crmc.ourcity.rest.responce.interestAreas.InterestingArea;

import java.util.List;

/**
 * Created by SetKrul on 30.07.2015.
 */
public class ResidentDetails {
    public int residentId;
    public int cityId;
    public List<InterestingArea> interestAreasList;

    public ResidentDetails(int residentId, int cityId) {
        this.residentId = residentId;
        this.cityId = cityId;
    }
}
