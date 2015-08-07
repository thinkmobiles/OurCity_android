package com.crmc.ourcity.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by SetKrul on 28.07.2015.
 */
public class Image {

    /**
     * Method for return file path of Gallery image
     *
     * @param context
     * @param uri
     * @return path of the selected image file from gallery
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {
        return new FilePath().getPath(context, uri);
    }

    /**
     * @param file         image file
     * @param requiredSize out file size in kb
     * @return bitmap with resize image
     */
    public Bitmap compressImage(File file, int requiredSize) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(file), null, options);

            int scale = 1;
            while (options.outWidth / scale / 2 >= requiredSize && options.outHeight / scale / 2 >= requiredSize)
                scale *= 2;

            BitmapFactory.Options optionsResize = new BitmapFactory.Options();
            optionsResize.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(file), null, optionsResize);
        } catch (FileNotFoundException e) {
        }
        return null;
    }
}
