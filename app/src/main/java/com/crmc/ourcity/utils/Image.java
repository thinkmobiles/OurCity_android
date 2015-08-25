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

    private static int ColorNow = Color.BLACK;

    public static void init(int _color) {
        ColorNow = _color;
    }

    public static String getStringColor() {
        return "#" + Integer.toHexString(ColorNow).substring(2);
    }

    /**
     * Method for return file path of Gallery image
     *
     * @param _context context
     * @param _uri     file uri
     * @return path of the selected image file from gallery
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context _context, final Uri _uri) {
        return new FilePath().getPath(_context, _uri);
    }

    /**
     * Method for return compress image to set size
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
            return null;
        }
    }

    /**
     * Convert base64 string image to bitmap
     *
     * @param _image string base64 image
     * @return bitmap image
     */
    public static Bitmap convertBase64ToBitmap(String _image) {
        try {
            String imageDataBytes = _image.substring(_image.indexOf(",") + 1);
//            if (imageDataBytes.length() > 0) {
            byte[] decodedString = Base64.decode(imageDataBytes, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Set image color for drawable image, change white color to set color
     *
     * @param _context  context
     * @param _color    your color
     * @param _drawable your drawable
     * @return drawable image with your color
     */
    public static Drawable setDrawableImageColor(Context _context, int _drawable, int _color) {
        Drawable mDrawable = ContextCompat.getDrawable(_context, _drawable);
        PorterDuffColorFilter mFilter = new PorterDuffColorFilter(_color, PorterDuff.Mode.MULTIPLY);
        mDrawable.setColorFilter(mFilter);
        return mDrawable;
    }

    /**
     * Set image color default for drawable image
     *
     * @param _context  context
     * @param _drawable your drawable
     * @return drawable image with default color
     */
    public static Drawable setDrawableImageColor(Context _context, int _drawable) {
        return setDrawableImageColor(_context, _drawable, ColorNow);
    }

    /**
     * Set image color for drawable array image, change white color to set color
     *
     * @param _context  context
     * @param _color    your color
     * @param _drawable your array drawable
     * @return drawable array image with your color
     */
    public static Drawable[] setArrayDrawableImageColor(Context _context, int[] _drawable, int _color) {
        Drawable mDrawable[] = new Drawable[_drawable.length];
        PorterDuffColorFilter mFilter;
        for (int i = 0; i < _drawable.length; i++) {
            mDrawable[i] = ContextCompat.getDrawable(_context, _drawable[i]);
            mFilter = new PorterDuffColorFilter(_color, PorterDuff.Mode.MULTIPLY);
            mDrawable[i].setColorFilter(mFilter);
        }
        return mDrawable;
    }


    /**
     * Set image color default for drawable array image, change white color to set color
     *
     * @param _context  context
     * @param _drawable your array drawable
     * @return drawable array image with default color
     */
    public static Drawable[] setArrayDrawableImageColor(Context _context, int[] _drawable) {
        return setArrayDrawableImageColor(_context, _drawable, ColorNow);
    }

    /**
     * Change color view with change color
     *
     * @param _context  context
     * @param _view     view for change color
     * @param _drawable drawable attach to view
     * @param _color    color for paint
     */
    public static void setBackgroundColorView(Context _context, View _view, int _drawable, int _color) {
        Drawable d = ContextCompat.getDrawable(_context, _drawable);
        d.setColorFilter(_color, PorterDuff.Mode.SCREEN);
        _view.setBackground(d);
    }

    /**
     * Change color view with default color
     *
     * @param _context  context
     * @param _view     view for change color
     * @param _drawable drawable attach to view
     */
    public static void setBackgroundColorView(Context _context, View _view, int _drawable) {
        setBackgroundColorView(_context, _view, _drawable, ColorNow);
    }

    /**
     * Change color array view with change color
     *
     * @param _context  context
     * @param _view     array view for change color
     * @param _drawable drawable attach to view
     * @param _color    color for paint
     */
    public static void setBackgroundColorArrayView(Context _context, View[] _view, int _drawable, int _color) {
        Drawable d;
        for (View a_view : _view) {
            d = ContextCompat.getDrawable(_context, _drawable);
            d.setColorFilter(_color, PorterDuff.Mode.SCREEN);
            a_view.setBackground(d);
        }
    }

    /**
     * Change color array view with default color
     *
     * @param _context  context
     * @param _view     array view for change color
     * @param _drawable drawable attach to view
     */
    public static void setBackgroundColorArrayView(Context _context, View[] _view, int _drawable) {
        setBackgroundColorArrayView(_context, _view, _drawable, ColorNow);
    }

    /**
     * Lightens a color by a given factor.
     *
     * @param _factor The factor to lighten the color. 0 will make the color unchanged. 1 will make the
     *                color white.
     * @return lighter version of the specified color.
     */
    public static int lighterColor(double _factor) {
        int red = (int) ((Color.red(ColorNow) * (1 - _factor) / 255 + _factor) * 255);
        int green = (int) ((Color.green(ColorNow) * (1 - _factor) / 255 + _factor) * 255);
        int blue = (int) ((Color.blue(ColorNow) * (1 - _factor) / 255 + _factor) * 255);
        return Color.argb(Color.alpha(ColorNow), red, green, blue);
    }

    /**
     * Darkens a color by a given factor.
     *
     * @param _factor The factor to darken the color. 0 will make the color unchanged. 1 will make the
     *                color black.
     * @return darken version of the specified color.
     */
    public static int darkenColor(double _factor) {
        int red = (int) Math.round(Math.max(0, Color.red(ColorNow) - 255 * _factor));
        int green = (int) Math.round(Math.max(0, Color.green(ColorNow) - 255 * _factor));
        int blue = (int) Math.round(Math.max(0, Color.blue(ColorNow) - 255 * _factor));
        return Color.argb(Color.alpha(ColorNow), red, green, blue);
    }
}
