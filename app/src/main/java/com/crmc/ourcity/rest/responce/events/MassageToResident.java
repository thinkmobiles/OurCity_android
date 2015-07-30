package com.crmc.ourcity.rest.responce.events;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SetKrul on 30.07.2015.
 */
public class MassageToResident {

    @SerializedName("message")
    public String message;
    @SerializedName("messageToResidentId")
    public Integer messageToResidentId;
}
