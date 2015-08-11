package com.crmc.ourcity.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.util.Base64;

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
     * @param _context
     * @param _uri
     * @return path of the selected image file from gallery
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context _context, final Uri _uri) {
        return new FilePath().getPath(_context, _uri);
    }

    /**
     * @param _file         image file
     * @param _requiredSize out file size in kb
     * @return bitmap with resize image
     */
    public static Bitmap compressImage(File _file, int _requiredSize) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(_file), null, options);

            int scale = 1;
            while (options.outWidth / scale / 2 >= _requiredSize && options.outHeight / scale / 2 >= _requiredSize)
                scale *= 2;

            BitmapFactory.Options optionsResize = new BitmapFactory.Options();
            optionsResize.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(_file), null, optionsResize);
        } catch (FileNotFoundException e) {
        }
        return null;
    }

    public static Bitmap convertBase64ToBitmap(String _image) {
        String imageDataBytes = _image.substring(_image.indexOf(",") + 1);
        Bitmap decodedByte = null;
        if (imageDataBytes.length() > 0) {
            byte[] decodedString = Base64.decode(imageDataBytes, Base64.DEFAULT);
            decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        }
        return decodedByte;
    }
}
