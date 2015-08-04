package com.crmc.ourcity.global;

/**
 * Created by SetKrul on 23.07.2015.
 */
public final class Constants {

    public static final String REQUEST_INTENT_TYPE_PHOTO = "type";
//    focus fragment
    public static final int REQUEST_TYPE_PHOTO = 0;
    public static final int REQUEST_PHOTO = 1;
    public static final int REQUEST_GALLERY_IMAGE = 2;

//    map fragment
    public static final int REQUEST_MAP_FILTER = 3;
    public static final String REQUEST_MAP_FILTER_TYPE = "filter_type";
    public static final int REQUEST_MAP_SELECTED = 1;
    public static final int REQUEST_MAP_CANCEL_SELECTED = 0;

    public static final int LOADER_ADDRESS_ID = 1;
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

    public static final String IMAGE_MIME_TYPE_PREFIX = "image";
    public static final String IMAGE_MIME_TYPE = IMAGE_MIME_TYPE_PREFIX + "/*";
}
