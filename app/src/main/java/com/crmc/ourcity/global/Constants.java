package com.crmc.ourcity.global;

/**
 * Created by SetKrul on 23.07.2015.
 */
public final class Constants {

    public static final String TAG = "TAG";

    //for request
    public static final String REQUEST_INTENT_TYPE_PHOTO = "type";
    public static final int REQUEST_TYPE_PHOTO = 0;
    public static final int REQUEST_PHOTO = 1;
    public static final int REQUEST_GALLERY_IMAGE = 2;
    public static final int REQUEST_MARKER_FILTER = 3;
    public static final int REQUEST_VOTE = 4;
    public static final int REQUEST_AGE = 5;
    public static final int REQUEST_GENDER = 6;
    public static final String REQUEST_TYPE = "REQUEST_TYPE";
    public static final int REQUEST_OK = 7;
    public static final int REQUEST_CANCEL = 8;

    //for loaders
    public static final int LOADER_ADDRESS_ID = 1;
    public static final int LOADER_STREETS_ID = 2;
    public static final int LOADER_VOTE_ID = 3;
    public static final int LOADER_VOTE_REPLY_ID = 4;

    //for bundle
    public static final String BUNDLE_CONSTANTS_LAT = "LAT";
    public static final String BUNDLE_CONSTANTS_LON = "LON";
    public static final String BUNDLE_CONSTANT_CITY_NUMBER = "CITY";
    public static final String BUNDLE_CONSTANT_LANG = "LANG";
    public static final String BUNDLE_CONSTANT_TYPE = "TYPE";
    public static final String BUNDLE_CONSTANT_SELECTED_OPTION_ID = "SELECTED_OPTION_ID";
    public static final String BUNDLE_CONSTANT_AGE = "AGE";
    public static final String BUNDLE_CONSTANT_GENDER = "GENDER";
    public static final String BUNDLE_CONSTANT_LOAD_IMAGE_TYPE = "LOAD_IMAGE_TYPE";
    public static final int BUNDLE_CONSTANT_LOAD_IMAGE_TYPE_LOGO = 0;
    public static final int BUNDLE_CONSTANT_LOAD_IMAGE_TYPE_CITY = 1;
    public static final int BUNDLE_CONSTANT_LOAD_IMAGE_TYPE_MAYOR = 2;
    public static final String BUNDLE_CONSTANT_RESIDENT_ID = "RESIDENT_ID";
    public static final String BUNDLE_CONSTANT_CLIENT_ID = "CLIENT_ID";
    public static final String BUNDLE_CONSTANT_USER_NAME = "USER_NAME";
    public static final String BUNDLE_CONSTANT_PASSWORD = "PASSWORD";
    public static final String BUNDLE_MARKERS = "MARKER";
    public static final String BUNDLE_INTEGER = "INTEGER";
    public static final String IMAGE_MIME_TYPE_PREFIX = "image";
    public static final String IMAGE_MIME_TYPE = IMAGE_MIME_TYPE_PREFIX + "/*";
}
