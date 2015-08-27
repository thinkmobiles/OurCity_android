package com.crmc.ourcity.rest.responce.events;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SetKrul on 30.07.2015.
 */
public class Phones implements Parcelable {

    @SerializedName("email")
    public String emailAddress;
    @SerializedName("entityId")
    public Integer entityId;
    @SerializedName("name")
    public String entityName;
    @SerializedName("address")
    public String address;
    @SerializedName("phoneNumber")
    public String phoneNumber;

    protected Phones(Parcel in) {
        emailAddress = in.readString();
        entityId = in.readInt();
        entityName = in.readString();
        address = in.readString();
        phoneNumber = in.readString();
    }

    public static final Creator<Phones> CREATOR = new Creator<Phones>() {
        @Override
        public Phones createFromParcel(Parcel in) {
            return new Phones(in);
        }

        @Override
        public Phones[] newArray(int size) {
            return new Phones[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(emailAddress);
        dest.writeInt(entityId);
        dest.writeString(entityName);
        dest.writeString(address);
        dest.writeString(phoneNumber);
    }
}
