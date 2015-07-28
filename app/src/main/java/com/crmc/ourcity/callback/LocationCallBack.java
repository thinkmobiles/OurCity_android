package com.crmc.ourcity.callback;

/**
 * Created by SetKrul on 15.07.2015.
 */
public interface LocationCallBack {
    void onSuccess(double lat, double lon);

    //false - error
    void onFailure(boolean result);
}
