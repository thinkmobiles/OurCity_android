package com.crmc.ourcity.rest.responce.events;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PhoneBook {
    @SerializedName("phonebookEntries")
    public List<Phones> phonebookEntries;

    @SerializedName("categoryName")
    public String categoryName;
}
