package com.crmc.ourcity.rest.request.streets;

/**
 * Created by SetKrul on 31.07.2015.
 */
public class StreetsModel {
    public StreetsDetails GetStreetListWrapper;

    public StreetsModel(StreetsDetails GetStreetListWrapper) {
        this.GetStreetListWrapper = GetStreetListWrapper;
    }
}
