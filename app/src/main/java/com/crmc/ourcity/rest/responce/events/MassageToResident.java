package com.crmc.ourcity.rest.responce.events;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MassageToResident implements Parcelable{

    @SerializedName("message")
    public String message;
    @SerializedName("messageToResidentId")
    public Integer messageToResidentId;
    @SerializedName("url")
    public String link;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.message);
        dest.writeValue(this.messageToResidentId);
        dest.writeString(this.link);
    }

    public MassageToResident() {
    }

    protected MassageToResident(Parcel in) {
        this.message = in.readString();
        this.messageToResidentId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.link = in.readString();
    }

    public static final Creator<MassageToResident> CREATOR = new Creator<MassageToResident>() {
        public MassageToResident createFromParcel(Parcel source) {
            return new MassageToResident(source);
        }

        public MassageToResident[] newArray(int size) {
            return new MassageToResident[size];
        }
    };
}
