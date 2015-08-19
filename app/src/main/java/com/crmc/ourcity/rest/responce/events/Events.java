package com.crmc.ourcity.rest.responce.events;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SetKrul on 30.07.2015.
 */
public class Events {

    @SerializedName("address")
    public String address;
    @SerializedName("description")
    public String description;
    @SerializedName("eventDate")
    public String eventDate;
    @SerializedName("email")
    public String email;
    @SerializedName("eventDateToMobileClient")
    public String eventDateToMobileClient;
    @SerializedName("eventId")
    public Integer eventId;
    @SerializedName("link")
    public String link;
    @SerializedName("mobile")
    public String mobile;
    @SerializedName("notes")
    public String notes;
    @SerializedName("phone")
    public String phone;
    @SerializedName("picture")
    public String picture;
    @SerializedName("price")
    public String price;
    @SerializedName("title")
    public String title;
}
