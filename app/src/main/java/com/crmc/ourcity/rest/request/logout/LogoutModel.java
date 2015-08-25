package com.crmc.ourcity.rest.request.logout;

/**
 * Created by podo on 21.08.15.
 */
public class LogoutModel {
    public String authToken;

    public LogoutModel() {
    }

    public LogoutModel(String _authToken) {
        authToken = _authToken;
    }
}
