package com.crmc.ourcity.rest.responce.login;

/**
 * Created by podo on 21.08.15.
 */
public class LoginResponse {
    public Integer residentId;
    public String authToken;

    public LoginResponse() {
    }

    public LoginResponse(Integer _residentId, String _authToken) {
        residentId = _residentId;
        authToken = _authToken;
    }
}
