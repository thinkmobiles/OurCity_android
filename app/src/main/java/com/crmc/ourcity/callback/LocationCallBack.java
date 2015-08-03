package com.crmc.ourcity.callback;

/**
 * Created by SetKrul on 15.07.2015.
 */
public interface LocationCallBack {
    /**
     * @param lat Latitude
     * @param lon Longitude
     */
    void onSuccess(double lat, double lon);

    /**
     * @param result error - false
     */
    void onFailure(boolean result);
}
