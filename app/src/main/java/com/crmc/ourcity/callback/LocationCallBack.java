package com.crmc.ourcity.callback;

public interface LocationCallBack {
    /**
     * @param _lat Latitude
     * @param _lon Longitude
     */
    void onSuccess(double _lat, double _lon);

    /**
     * @param _result error - false
     */
    void onFailure(boolean _result);
}
