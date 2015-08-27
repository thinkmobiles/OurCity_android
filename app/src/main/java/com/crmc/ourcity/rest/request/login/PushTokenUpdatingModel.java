package com.crmc.ourcity.rest.request.login;

/**
 * Created by podo on 21.08.15.
 */
public class PushTokenUpdatingModel {
   public PushTokenAndAuthToken token;

   public PushTokenUpdatingModel(PushTokenAndAuthToken token) {
        this.token = token;
    }
}
