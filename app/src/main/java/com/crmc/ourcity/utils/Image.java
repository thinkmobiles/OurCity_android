package com.crmc.ourcity.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.view.View;

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
     * Metod for return compress image to set size
     *
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

    /**
     * Convert base64 string image to bitmap
     *
     * @param _image string base64 image
     * @return bitmap image
     */
    public static Bitmap convertBase64ToBitmap(String _image) {
        Bitmap decodedByte = null;
        try {
            String imageDataBytes = _image.substring(_image.indexOf(",") + 1);
            if (imageDataBytes.length() > 0) {
                byte[] decodedString = Base64.decode(imageDataBytes, Base64.DEFAULT);
                decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

            }
        } catch (Exception e) {

        }
        return decodedByte;
    }

    /**
     *
     *
     * @param _context
     * @param _color
     * @param _drawable
     * @return
     */
    public static Drawable setImageColor(Context _context, int _color, int _drawable) {
        Drawable mDrawable = ContextCompat.getDrawable(_context, _drawable);
        PorterDuffColorFilter mFilter = new PorterDuffColorFilter(_color, PorterDuff.Mode.MULTIPLY);
        mDrawable.setColorFilter(mFilter);
        return mDrawable;
    }

    /**
     *
     * @param _context
     * @param _view
     * @param _drawable
     * @param _color
     */
    public static void setBackgroundColorView(Context _context, View _view, int _drawable, int _color) {
        Drawable d = ContextCompat.getDrawable(_context, _drawable);
        d.setColorFilter(_color, PorterDuff.Mode.SCREEN);
        _view.setBackground(d);
    }

    /**
     *
     * @param _context
     * @param _view
     * @param _drawable
     * @param _color
     */
    public static void setBackgroundColorArrayView(Context _context, View[] _view, int _drawable, int _color) {
        Drawable d;
        for (int i = 0; i < _view.length; i++) {
            d = ContextCompat.getDrawable(_context, _drawable);
            d.setColorFilter(_color, PorterDuff.Mode.SCREEN);
            _view[i].setBackground(d);
        }
    }

    /**
     * Lightens a color by a given factor.
     *
     * @param _color  The color to lighten
     * @param _factor The factor to lighten the color. 0 will make the color unchanged. 1 will make the
     *               color white.
     * @return lighter version of the specified color.
     */
    public static int lighterColor(int _color, float _factor) {
        int red = (int) ((Color.red(_color) * (1 - _factor) / 255 + _factor) * 255);
        int green = (int) ((Color.green(_color) * (1 - _factor) / 255 + _factor) * 255);
        int blue = (int) ((Color.blue(_color) * (1 - _factor) / 255 + _factor) * 255);
        return Color.argb(Color.alpha(_color), red, green, blue);
    }

    /**
     * Darkens a color by a given factor.
     *
     * @param _color The color to darken
     * @param _factor The factor to darken the color. 0 will make the color unchanged. 1 will make the
     *               color black.
     * @return darken version of the specified color.
     */
    public static int darkenColor(int _color, float _factor) {
        int red = Math.round(Math.max(0, Color.red(_color) - 255 * _factor));
        int green = Math.round(Math.max(0, Color.green(_color) - 255 * _factor));
        int blue = Math.round(Math.max(0, Color.blue(_color) - 255 * _factor));
        return Color.argb(Color.alpha(_color), red, green, blue);
    }
}
