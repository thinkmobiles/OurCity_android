package com.crmc.ourcity.notification;

import android.app.IntentService;
import android.content.Intent;

import com.crmc.ourcity.R;
import com.crmc.ourcity.rest.RestClientApi;
import com.crmc.ourcity.rest.api.CityApi;
import com.crmc.ourcity.rest.request.login.PushTokenAndAuthToken;
import com.crmc.ourcity.rest.request.login.PushTokenUpdatingModel;
import com.crmc.ourcity.utils.SPManager;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

public class RegistrationIntentService extends IntentService {
    private static final String TAG = "RegIntentService";
    public static final String ACTION = "service action";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.     *
     */
    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            synchronized (TAG) {
                InstanceID instanceID = InstanceID.getInstance(this);
                String pushToken = instanceID.getToken(getString(R.string.gcm_SenderID),
                        GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

                String authToken = SPManager.getInstance(this).getAuthToken();
                sendPushTokenToServer(authToken, pushToken);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendPushTokenToServer(String _authToken, String _pushToken) {
        CityApi cityApi = RestClientApi.getCityApi();

        boolean isPushUpdated;
        try {
            isPushUpdated = cityApi.updatePushTokenOnWS(new PushTokenUpdatingModel(new PushTokenAndAuthToken(_authToken, _pushToken)));
        } catch (Exception e){
            isPushUpdated = false;
        }
        if (isPushUpdated)
            SPManager.getInstance(this).setPushToken(_pushToken);
    }
}