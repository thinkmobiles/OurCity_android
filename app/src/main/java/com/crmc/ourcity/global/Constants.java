package com.crmc.ourcity.global;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import com.crmc.ourcity.rest.responce.menu.MenuFull;

/**
 * Created by SetKrul on 23.07.2015.
 */
public class Constants {

    final public static String TAG = "TAG";
    public static final String BUNDLE_CONSTANT_PARCELABLE_TICKET = "ticket_parse";
    public static final String BUNDLE_CONSTANT_CRMCUSERNAME = "crmcusername";
    public static final String BUNDLE_CONSTANT_CRMCPASSWORD = "crmcpassword";


    public static BitmapDrawable cityImage = null;
    public static Bitmap logoImage = null;
    public static MenuFull mMenuFull = null;
    public static MenuFull mMenuFullBottom = null;

    //for request
    final public static String REQUEST_INTENT_TYPE_PHOTO = "type";
    final public static int REQUEST_TYPE_PHOTO = 0;
    final public static int REQUEST_PHOTO = 1;
    final public static int REQUEST_GALLERY_IMAGE = 2;
    final public static int REQUEST_MARKER_FILTER = 3;
    final public static int REQUEST_VOTE = 4;
    final public static int REQUEST_AGE = 5;
    final public static int REQUEST_GENDER = 6;
    final public static String REQUEST_TYPE = "REQUEST_TYPE";
    final public static int REQUEST_CANCEL = 8;

    //for loaders
    final public static int LOADER_ADDRESS_ID = 1;
    final public static int LOADER_STREETS_ID = 2;
    final public static int LOADER_VOTE_ID = 3;
    final public static int LOADER_VOTE_REPLY_ID = 4;
    final public static int LOADER_EVENTS_ID = 5;
    final public static int LOADER_INTERESTED_POINTS_ID = 6;
    final public static int LOADER_PHONES_ID = 7;
    final public static int LOADER_DOCUMENTS_ID = 8;
    final public static int LOADER_MENU_ID = 9;
    final public static int LOADER_MENU_BOTTOM_ID = 10;
    final public static int LOADER_TRIPS_ID = 11;
    final public static int LOADER_MESSAGE_TO_RESIDENT = 12;
    final public static int LOADER_IMAGE_LOGO_ID = 13;
    final public static int LOADER_IMAGE_CITY_ID = 14;
    final public static int LOADER_BACKGROUND_IMAGE_ID = 15;
    final public static int LOADER_CITY_ENTITIES_ID = 16;
    public static final int LOADER_RSS_ID = 17;
    public static final int LOADER_REGISTER_NEW_RESIDENT_ID = 18;
    public static final int LOADER_TICKERS_ID = 19;
    public static final int LOADER_APPEALS_ID = 20;
    public static final int LOADER_LOGIN_ID = 21;
    public static final int LOADER_LIST_LINK_ID = 22;
    public static final int LOADER_SEND_APPEALS_ID = 23;

    //for bundle
    final public static int BUNDLE_CONSTANT_LOAD_IMAGE_TYPE_LOGO = 0;
    final public static int BUNDLE_CONSTANT_LOAD_IMAGE_TYPE_CITY = 1;
    final public static int BUNDLE_CONSTANT_LOAD_IMAGE_TYPE_MAYOR = 2;
    final public static int BUNDLE_CONSTANT_LOAD_IMAGE_TYPE_PROMOTIONAL = 3;
    final public static int BUNDLE_CONSTANT_LOAD_IMAGE_TYPE_BANNER = 4;
    final public static String BUNDLE_CONSTANT_REQUEST_JSON = "JSON";
    final public static String BUNDLE_CONSTANT_REQUEST_ROUTE = "ROUTE";
    final public static String BUNDLE_CONSTANTS_LAT = "LAT";
    final public static String BUNDLE_CONSTANTS_LON = "LON";
    final public static String BUNDLE_CONSTANT_CITY_NUMBER = "CITY";
    final public static String BUNDLE_CONSTANT_LANG = "LANG";
    final public static String BUNDLE_CONSTANT_SELECTED_OPTION_ID = "SELECTED_OPTION_ID";
    final public static String BUNDLE_CONSTANT_AGE = "AGE";
    final public static String BUNDLE_CONSTANT_GENDER = "GENDER";
    final public static String BUNDLE_CONSTANT_LOAD_IMAGE_TYPE = "LOAD_IMAGE_TYPE";
    final public static String BUNDLE_CONSTANT_RESIDENT_ID = "RESIDENT_ID";
    final public static String BUNDLE_MARKERS = "MARKER";
    final public static String BUNDLE_INTEGER = "INTEGER";
    final public static String BUNDLE_CONSTANT_USER_NAME = "USER_NAME";
    final public static String BUNDLE_CONSTANT_PASSWORD = "PASSWORD";
    final public static String IMAGE_MIME_TYPE_PREFIX = "image";
    final public static String IMAGE_MIME_TYPE = IMAGE_MIME_TYPE_PREFIX + "/*";
    public static final String BUNDLE_CONSTANT_FIRST_NAME = "FIRST_NAME";
    public static final String BUNDLE_CONSTANT_LAST_NAME = "LAST_NAME";
    public static final String BUNDLE_CONSTANT_EMAIL = "EMAIL";
    public static final String BUNDLE_CONSTANT_MOBILE_NUMBER = "MOBILE_NUMBER";
    public static final String BUNDLE_CONSTANT_PHONE_NUMBER = "PHONE_NUMBER";
    public static final String BUNDLE_CONSTANT_HOUSE_NUMBER = "HOUSE_NUMBER";
    public static final String BUNDLE_CONSTANT_STREET_ID = "STREET_ID";
    public static final String BUNDLE_CONSTANT_CITY_ID = "CITY_ID";
    public static final String BUNDLE_CONSTANT_GLOBAL_NOTIFICATION_NEEDED = "GLOBAL_NOTIFICATION";
    public static final String BUNDLE_CONSTANT_PERSONAL_NOTIFICATION_NEEDED = "PERSONAL_NOTIFICATION";
    public static final String BUNDLE_CONSTANT_AUTH_TOKEN = "AUTH_TOKEN";
    public static final String BUNDLE_CONSTANT_PUSH_TOKEN = "PUSH_TOKEN";
    public static final String BUNDLE_CONSTANT_RSS_LINK = "rss_link";

    //for Instance
    final public static String CONFIGURATION_KEY_LINK = "KEY_LINK";
    final public static String CONFIGURATION_KEY_COLOR = "KEY_COLOR";
    final public static String CONFIGURATION_KEY_LAT = "KEY_LAT";
    final public static String CONFIGURATION_KEY_LON = "KEY_LON";
    final public static String CONFIGURATION_KEY_JSON = "KEY_JSON";
    final public static String CONFIGURATION_KEY_ROUTE = "KEY_ROUTE";
    final public static String CONFIGURATION_KEY_EVENTS = "KEY_EVENTS";
    final public static String CONFIGURATION_KEY_TRIPS = "KEY_TRIPS";
    final public static String CONFIGURATION_KEY_SUBMENU = "SUBMENU";
    final public static String CONFIGURATION_KEY_PHONE_LIST = "PHONE_LIST";
    final public static String CONFIGURATION_KEY_PHONE_LIST_OR_PHONE_BOOK = "LIST_BOOK";
    final public static String CONFIGURATION_KEY_CITY_ENINIES = "CITY_ENINIES";
    public static final String CONFIGURATION_KEY_RSS = "rss";
    public static final String CONFIGURATION_KEY_MAIL = "mail";

    //action type
    final public static int ACTION_TYPE_LIST = 1;
    final public static int ACTION_TYPE_LIST_EVENTS = 1;
    final public static int ACTION_TYPE_LIST_APPEALS = 2;
    final public static int ACTION_TYPE_LIST_LINK = 3;
    final public static int ACTION_TYPE_LINK = 3;
    final public static int ACTION_TYPE_DOCUMENT = 4;
    final public static int ACTION_TYPE_MAP = 5;
    final public static int ACTION_TYPE_ADD_APPEALS = 6;
    final public static int ACTION_TYPE_VOTE = 7;
    final public static int ACTION_TYPE_CALL = 8;
    final public static int ACTION_TYPE_MAIL = 9;
    final public static int ACTION_TYPE_PHONE_BOOK = 10;
    final public static int ACTION_TYPE_RSS = 11;
    final public static int ACTION_TYPE_MAP_TRIPS = 12;
    final public static int ACTION_TYPE_ENTITIES = 13;
    final public static int ACTION_SEND_MAIL_FRAGMENT = 14;
    final public static int ACTION_HOT_CALL = 15;
    final public static int ACTION_TYPE_MESSAGE_TO_RESIDENT = 16;

    //phone fragment type
    final public static int PHONE_BOOK_LIST = 0;
    final public static int PHONE_LIST = 1;

    public static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    public static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static String IS_FROM_MAIN_ACTIVITY = "true";
    public static String BUNDLE_TICKERS_LIST = "tickersList";
}
