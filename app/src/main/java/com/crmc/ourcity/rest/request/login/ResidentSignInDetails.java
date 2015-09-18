package com.crmc.ourcity.rest.request.login;

/**
 * Created by podo on 20.08.15.
 */
public class ResidentSignInDetails {
    public String userName;
    public String password;
    public int cityId;
    public String authToken;
    public String applianceType = "android";
    public String pushToken;


    public ResidentSignInDetails() {
    }

    public ResidentSignInDetails(String _userName, String _password, String _authToken, String _applianceType, String _pushToken) {
        userName = _userName;
        password = _password;
        authToken = _authToken;
        applianceType = _applianceType;
        pushToken = _pushToken;
    }
}
