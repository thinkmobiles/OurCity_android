package com.crmc.ourcity.utils;

import android.content.Intent;

/**
 * Created by SetKrul on 17.07.2015.
 */
public class IntentUtils {
    public static Intent getIntentMail(String mail){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{mail});
        return i;
    }


}
