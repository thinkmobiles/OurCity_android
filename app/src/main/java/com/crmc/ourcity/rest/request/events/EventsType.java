package com.crmc.ourcity.rest.request.events;

/**
 * Created by SetKrul on 30.07.2015.
 */
public class EventsType {
    public String typeName;
    public int cityId;

    public EventsType(int cityId, String typeName) {
        this.cityId = cityId;
        this.typeName = typeName;
    }
}
