package com.crmc.ourcity.notification;

import android.app.IntentService;
import android.content.Intent;
import android.text.TextUtils;

import com.crmc.ourcity.R;
import com.crmc.ourcity.loader.PushTokenLoader;
import com.crmc.ourcity.rest.RestClientApi;
import com.crmc.ourcity.rest.api.CityApi;
import com.crmc.ourcity.rest.request.login.PushTokenUpdatingModel;
import com.crmc.ourcity.utils.SPManager;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

/**
 * Created by podo on 21.08.15.
 */
public class RegistrationIntentService extends IntentService {
    private static final String TAG = "RegIntentService";

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
                if (!TextUtils.isEmpty(authToken)) {
                    sendPushTokenToServer(authToken, pushToken);
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendPushTokenToServer(String _authToken, String _pushToken) {
        CityApi cityApi = RestClientApi.getCityApi();

        Boolean isPushUpdated = cityApi.updatePushTokenOnWS(new PushTokenUpdatingModel(_authToken, _pushToken));
        SPManager.getInstance(this).setPushToken(_pushToken);
    }




}
