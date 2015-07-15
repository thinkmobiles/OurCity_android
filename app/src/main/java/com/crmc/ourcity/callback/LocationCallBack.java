package com.crmc.ourcity.callback;

import com.crmc.ourcity.model.ModelLocation;

/**
 * Created by SetKrul on 15.07.2015.
 */
public interface LocationCallBack {
    void onSuccess(ModelLocation modelLocation);

    //false - error
    void onFailure(boolean result);
}
