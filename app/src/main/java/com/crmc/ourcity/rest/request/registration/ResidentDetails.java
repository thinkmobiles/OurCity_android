package com.crmc.ourcity.rest.request.registration;

/**
 * Created by podo on 19.08.15.
 */
public class ResidentDetails {
    public String firstName;
    public String lastName;
    public String userName;
    public String password;
    public String email;
    public String mobileNumber;
    public String phoneNumber;
    public String houseNumber;
    public Boolean isGetGlobalNotification;
    public Boolean isGetPersonalNotification;
    public Integer streetId;
    public Integer cityId;

    public ResidentDetails() {
    }

    public ResidentDetails(String _firstName, String _lastName, String _userName, String _password,
                           String _email, String _mobileNumber, String _phoneNumber, String _houseNumber,
                           Boolean _isGetGlobalNotification, Boolean _isGetPersonalNotification,
                           Integer _streetId, Integer _cityId) {

        firstName = _firstName;
        lastName = _lastName;
        userName = _userName;
        password = _password;
        email = _email;
        mobileNumber = _mobileNumber;
        phoneNumber = _phoneNumber;
        houseNumber = _houseNumber;
        isGetGlobalNotification = _isGetGlobalNotification;
        isGetPersonalNotification = _isGetPersonalNotification;
        streetId = _streetId;
        cityId = _cityId;
    }
}
