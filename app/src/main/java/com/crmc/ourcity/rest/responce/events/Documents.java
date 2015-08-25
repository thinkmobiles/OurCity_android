package com.crmc.ourcity.rest.responce.events;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SetKrul on 21.08.2015.
 */
public class Documents {
    @SerializedName("DocumentTitle")
    public String documentTitle;
    @SerializedName("DocumentData")
    public String documentData;
}
