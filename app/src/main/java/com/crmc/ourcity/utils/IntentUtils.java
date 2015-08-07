package com.crmc.ourcity.utils;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import com.crmc.ourcity.global.Constants;

/**
 * Created by SetKrul on 17.07.2015.
 */
public final class IntentUtils {

    public static Intent getIntentSkype(String _phoneNumber){
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("skype:" + _phoneNumber));
        return i;
    }

    public static Intent getIntentMail(String _mail){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{_mail});
        return i;
    }

    public static Intent getGalleryStartIntent() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
        galleryIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        galleryIntent.setType(Constants.IMAGE_MIME_TYPE);
        galleryIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        return galleryIntent;
    }

    public static Intent getCameraStartIntent(final Uri _fileUri) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, _fileUri);
        return takePictureIntent;
    }
}