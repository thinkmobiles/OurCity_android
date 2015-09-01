package com.crmc.ourcity.rest.responce.events;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SetKrul on 31.08.2015.
 */
public class CityEntities implements Parcelable{
    @SerializedName("cityId")
    public Integer cityId;
    @SerializedName("details")
    public String details;
    @SerializedName("emailAddress")
    public String emailAddress;
    @SerializedName("entityId")
    public Integer entityId;
    @SerializedName("entityName")
    public String entityName;
    @SerializedName("information")
    public String information;
    @SerializedName("phoneNumber")
    public String phoneNumber;

    protected CityEntities(Parcel in) {
        cityId = in.readInt();
        details = in.readString();
        emailAddress = in.readString();
        entityId = in.readInt();
        entityName = in.readString();
        information = in.readString();
        phoneNumber = in.readString();
    }

    public static final Creator<CityEntities> CREATOR = new Creator<CityEntities>() {
        @Override
        public CityEntities createFromParcel(Parcel in) {
            return new CityEntities(in);
        }

        @Override
        public CityEntities[] newArray(int size) {
            return new CityEntities[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(cityId);
        dest.writeString(details);
        dest.writeString(emailAddress);
        dest.writeInt(entityId);
        dest.writeString(entityName);
        dest.writeString(information);
        dest.writeString(phoneNumber);
    }
}
