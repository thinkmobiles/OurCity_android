package com.crmc.ourcity.utils;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

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

        //check here to KITKAT or new version
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(_context, _uri)) {

            // ExternalStorageProvider
            if (isExternalStorageDocument(_uri)) {
                final String docId = DocumentsContract.getDocumentId(_uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(_uri)) {

                final String id = DocumentsContract.getDocumentId(_uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(_context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(_uri)) {
                final String docId = DocumentsContract.getDocumentId(_uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    return null;
                    //contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    return null;
                    //contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(_context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(_uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(_uri)) return _uri.getLastPathSegment();

            return getDataColumn(_context, _uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(_uri.getScheme())) {
            return _uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param _context       The _context.
     * @param _uri           The Uri to query.
     * @param _selection     (Optional) Filter used in the query.
     * @param _selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context _context, Uri _uri, String _selection, String[] _selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = _context.getContentResolver().query(_uri, projection, _selection, _selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return null;
    }

    /**
     * @param _uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri _uri) {
        return "com.android.externalstorage.documents".equals(_uri.getAuthority());
    }

    /**
     * @param _uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri _uri) {
        return "com.android.providers.downloads.documents".equals(_uri.getAuthority());
    }

    /**
     * @param _uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri _uri) {
        return "com.android.providers.media.documents".equals(_uri.getAuthority());
    }

    /**
     * @param _uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri _uri) {
        return "com.google.android.apps.photos.content".equals(_uri.getAuthority());
    }

    /**
     * @param _file         image _file
     * @param _requiredSize out _file size in kb
     * @return bitmap with resize image
     */
    public Bitmap compressImage(File _file, int _requiredSize) {
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
}
