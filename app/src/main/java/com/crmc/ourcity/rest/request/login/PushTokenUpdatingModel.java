package com.crmc.ourcity.rest.request.login;

/**
 * Created by podo on 21.08.15.
 */
public class PushTokenUpdatingModel {
    public String authToken;
    public String pushToken;

    public PushTokenUpdatingModel() {
    }

    public PushTokenUpdatingModel(String _authToken, String _pushToken) {
        authToken = _authToken;
        pushToken = _pushToken;
    }
}
