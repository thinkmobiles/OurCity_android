package com.crmc.ourcity.rest.request.streets;

public class StreetsDetails {
    public int clientId;
    public String userName;
    public String password;

    public StreetsDetails(int clientId, String userName, String password) {
        this.clientId = clientId;
        this.userName = userName;
        this.password = password;
    }
}
