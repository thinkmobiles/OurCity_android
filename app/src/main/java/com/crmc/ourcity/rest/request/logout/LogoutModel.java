package com.crmc.ourcity.rest.request.logout;

public class LogoutModel {
    public String authToken;

    public LogoutModel() {
    }

    public LogoutModel(String _authToken) {
        authToken = _authToken;
    }
}
