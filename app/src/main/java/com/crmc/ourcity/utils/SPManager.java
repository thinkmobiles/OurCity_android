package com.crmc.ourcity.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.crmc.ourcity.R;

public class SPManager {
    private static final String CRMCUSERNAME = "crmcUsername";
    private static final String CRMCPASSWORD = "crmcPassword";
    public static SPManager spManager;
    public static SharedPreferences sharedPreferences;
    public static final String USER_NAME = "userName";
    public static final String USER_PASSWORD = "userPassword";
    public static final String CITY_ID = "cityId";
    public static final String RESIDENT_ID = "residentID";
    public static final String AUTH_TOKEN = "authToken";
    public static final String PUSH_TOKEN = "pushToken";
    public static final String LOGGED_IN = "LoggedIn";
    public static final String REG_ID = "registrationId";
    public static final String PUSH_RINGTONE = "pushRingtone";
    public static final String FIRST_LAUNCH = "firstLaunch";
    public static final String FIRST_EMERGENCY_NUM = "firstEmergencyNum";
    public static final String SECOND_EMERGENCY_NUM = "secondEmergencyNum";
    public static final String THIRD_EMERGENCY_NUM = "thirdEmergencyNum";
    public static final String SP_NAME="user_data";
    public static final String AMOUNT_OF_VISIBLE_TICKETS = "amountOfVisibleTickets";
    public static final String LANGUAGE = "language";
    public static final String APP_LANG = "app_lang";
    public static final String APP_COUNTRY = "app_country";
    public static final String SHOW_INTEREST_AREAS = "ShowIntrestAreas";
    public static final String SHOW_HOT_CALLS = "ShowHotCalls";


    public static SPManager getInstance(Context context) {
        if (spManager == null) {
            if (context != null)
                spManager = new SPManager(context);
        }
        return spManager;
    }

    public void setFirstLaunch(boolean installed) {
        saveBoolean(FIRST_LAUNCH, installed);
    }

    public boolean isInstalled() {
        return  retrieveBoolean(FIRST_LAUNCH, false);
    }

    public SPManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_MULTI_PROCESS);
    }

    public void deleteSP(Context context) {
        SharedPreferences sharedPreferences1 = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        sharedPreferences1.edit().clear().commit();
    }

    public static void saveString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }


    public void saveBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void saveInt(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void deleteResidentInformation() {
        setUserName("");
        setPassword("");
        setResidentId(0);
        setAuthToken("");
        setPushToken("");
        setIsLoggedStatus(false);
    }

    public void setShowInterestAreas(int _value) {
        saveInt(SHOW_INTEREST_AREAS, _value);
    }

    public int getShowInterestAreas() {
        return retrieveInt(SHOW_INTEREST_AREAS, 0);
    }

    public void setShowHotCalls(int _value) {
        saveInt(SHOW_HOT_CALLS, _value);
    }

    public int getShowHotCalls() {
        return retrieveInt(SHOW_HOT_CALLS, 0);
    }

    public void setApplicationCountry(String _country) {
        saveString(APP_COUNTRY, _country);
    }

    public String getApplicationCountry() {
        return retrieveString(APP_COUNTRY, "IL");
    }

    public void setApplicationLanguage(String _language) {
        saveString(APP_LANG, _language);
    }

    public String getApplicationLanguage() {
        return retrieveString(APP_LANG, "iw");
    }

    public void setAppLanguage(int _language) {
        saveInt(LANGUAGE, _language);
    }

    public int getAppLanguage() {
        return retrieveInt(LANGUAGE, R.id.rbLanguage_HE_FDL);
    }

    public void setAmountOfVisibleTickets(int _amountOfVisibleTickets){
        saveInt(AMOUNT_OF_VISIBLE_TICKETS, _amountOfVisibleTickets);
    }

    public int getAmountOfVisibleTickets() {
        return retrieveInt(AMOUNT_OF_VISIBLE_TICKETS, R.id.rbVisibleTickets100_FDVT);
    }

    public void setFirstEmergencyNumber(String _phone) {
        saveString(FIRST_EMERGENCY_NUM, _phone);
    }

    public String getFirstEmergencyNum() {
        return retrieveString(FIRST_EMERGENCY_NUM, "");
    }

    public void setSecondEmergencyNum(String _phone) {
        saveString(SECOND_EMERGENCY_NUM, _phone);
    }

    public String getSecondEmergencyNum() {
        return retrieveString(SECOND_EMERGENCY_NUM, "");
    }

    public void setThirdEmergencyNumber(String _phone) {
        saveString(THIRD_EMERGENCY_NUM, _phone);
    }

    public String getThirdEmergencyNum() {
        return retrieveString(THIRD_EMERGENCY_NUM, "");
    }

    public void setPushToken(String _pushToken) {
        saveString(PUSH_TOKEN, _pushToken);
    }

    public String getPushToken() {
        return retrieveString(PUSH_TOKEN, "");
    }


    public String getUserName() {
        return retrieveString(USER_NAME, "default value");
    }

    public String getUserPassword() {
        return retrieveString(USER_PASSWORD, "default password");
    }

    public String getUserCityId() {
        return retrieveString(CITY_ID, "default value");
    }

    public String getRegId() {
        return retrieveString(REG_ID, "");
    }

    public  int getResidentId() {
        return retrieveInt(RESIDENT_ID, 0);
    }

    public void setIsLoggedStatus(Boolean _isLogIn) {
        saveBoolean(LOGGED_IN, _isLogIn);
    }

    public boolean getIsLoggedStatus() {
        return retrieveBoolean(LOGGED_IN, false);
    }

    public void setResidentId(Integer _residentId) {
        saveInt(RESIDENT_ID, _residentId);
    }

    public void setCRMCUsername(String _crmcUsername) {
        saveString(CRMCUSERNAME, _crmcUsername);
    }

    public String getCRMCUsername() {
        return retrieveString(CRMCUSERNAME, "");
    }

    public void setCRMCPassword(String _crmcPassword) {
        saveString(CRMCPASSWORD, _crmcPassword);
    }

    public String getCRMCPassword() {
        return retrieveString(CRMCPASSWORD, "");
    }

    public String getAuthToken() {
        return retrieveString(AUTH_TOKEN, "");
    }

    public void setAuthToken(String _authToken) {
        saveString(AUTH_TOKEN, _authToken);
    }

    public void setUserName(String userName) {
        saveString(USER_NAME, userName);
    }

    public void setPassword(String password) {
        saveString(USER_PASSWORD, password);
    }

    public void setCityId(String cityId) {
        saveString(CITY_ID, cityId);
    }

    public void setStayLoggedIn(Boolean isStayLoggedIn) {
        saveBoolean(LOGGED_IN, isStayLoggedIn);
    }

    public static void setPushRingtone(String path) {
        saveString(PUSH_RINGTONE, path);
    }

    public static String getPushRingtone() {
        return retrieveString(PUSH_RINGTONE, "");
    }

    public static String retrieveString(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }


    public int retrieveInt(String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);

    }

    public boolean retrieveBoolean(String key, Boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }

    public void saveTicketSumInSP(int totalClosed, int totalOpened, int totalNew) {
        saveInt("totalClosed", totalClosed);
        saveInt("totalOpened", totalOpened);
        saveInt("totalNew", totalNew);
    }

    public void saveUrlPrefix(String urlPrefix) {
        saveString("url", urlPrefix);
    }

    public String getUrlPrefix() {
        return retrieveString("url", "no url");
    }

    public int getTotalClosed() {
        return retrieveInt("totalClosed", 0);
    }

    public int getTotalOpened() {
        return retrieveInt("totalOpened", 0);
    }

    public int getTotalNew() {
        return retrieveInt("totalNew", 0);
    }
}

