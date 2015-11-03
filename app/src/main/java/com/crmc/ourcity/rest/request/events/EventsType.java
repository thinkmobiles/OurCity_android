package com.crmc.ourcity.rest.request.events;

public class EventsType {
    public String typeName;
    public int cityId;

    public EventsType(int cityId, String typeName) {
        this.cityId = cityId;
        this.typeName = typeName;
    }
}
