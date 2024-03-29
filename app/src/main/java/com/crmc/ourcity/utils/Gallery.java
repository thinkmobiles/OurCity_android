package com.crmc.ourcity.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;

import com.crmc.ourcity.activity.MainActivity;
import com.crmc.ourcity.global.Constants;

import java.io.File;
import java.lang.ref.WeakReference;

public class Gallery {

    private final WeakReference<MainActivity> mActivity;

    public Gallery(Activity _activity){
        this.mActivity = new WeakReference<>((MainActivity)_activity);
    }

    public ComponentName isCanGetGalleryPicture() {
        Intent galleryIntent = IntentUtils.getGalleryStartIntent();
        return galleryIntent.resolveActivity(mActivity.get().getPackageManager());
    }

    public void openGallery(Fragment _fragment) {
        if (isCanGetGalleryPicture() != null) {
            Intent galleryIntent = IntentUtils.getGalleryStartIntent();
            if (galleryIntent.resolveActivity(mActivity.get().getPackageManager()) != null) {
                _fragment.startActivityForResult(galleryIntent, Constants.REQUEST_GALLERY_IMAGE);
            }
        }
    }

    public void addPhotoToGallery(String _mPhotoFilePath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(_mPhotoFilePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        mActivity.get().sendBroadcast(mediaScanIntent);
    }
}
