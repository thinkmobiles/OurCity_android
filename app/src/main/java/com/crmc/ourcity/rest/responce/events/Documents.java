package com.crmc.ourcity.rest.responce.events;

import com.google.gson.annotations.SerializedName;

public class Documents {
    @SerializedName("DocumentTitle")
    public String documentTitle;
    @SerializedName("DocumentData")
    public String documentData;
}
