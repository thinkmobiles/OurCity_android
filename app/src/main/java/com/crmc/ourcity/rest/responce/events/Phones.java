package com.crmc.ourcity.rest.responce.events;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Phones implements Parcelable {

    @SerializedName("address")
    public String address;
    @SerializedName("cityId")
    public Integer cityId;
    @SerializedName("email")
    public String emailAddress;
    @SerializedName("lastModifyUserId")
    public Integer lastModifyUserId;
    @SerializedName("name")
    public String entityName;
    @SerializedName("phoneNumber")
    public String phoneNumber;
    @SerializedName("phonebookCategoryId")
    public Integer phonebookCategoryId;
    @SerializedName("phonebookId")
    public Integer phonebookId;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.address);
        dest.writeValue(this.cityId);
        dest.writeString(this.emailAddress);
        dest.writeValue(this.lastModifyUserId);
        dest.writeString(this.entityName);
        dest.writeString(this.phoneNumber);
        dest.writeValue(this.phonebookCategoryId);
        dest.writeValue(this.phonebookId);
    }

    public Phones() {
    }

    protected Phones(Parcel in) {
        this.address = in.readString();
        this.cityId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.emailAddress = in.readString();
        this.lastModifyUserId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.entityName = in.readString();
        this.phoneNumber = in.readString();
        this.phonebookCategoryId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.phonebookId = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<Phones> CREATOR = new Creator<Phones>() {
        public Phones createFromParcel(Parcel source) {
            return new Phones(source);
        }

        public Phones[] newArray(int size) {
            return new Phones[size];
        }
    };
}
