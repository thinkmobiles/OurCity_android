package com.crmc.ourcity.callback;

import com.crmc.ourcity.model.LocationModel;

/**
 * Created by SetKrul on 15.07.2015.
 */
public interface LocationCallBack {
    void onSuccess(LocationModel modelLocation);

    //false - error
    void onFailure(boolean result);
}
