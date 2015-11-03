package com.crmc.ourcity.rest.request.login;

public class PushTokenAndAuthToken {
    public String authToken;
    public String pushToken;

    public PushTokenAndAuthToken() {
    }

    public PushTokenAndAuthToken(String _authToken, String _pushToken) {
        authToken = _authToken;
        pushToken = _pushToken;
    }
}
