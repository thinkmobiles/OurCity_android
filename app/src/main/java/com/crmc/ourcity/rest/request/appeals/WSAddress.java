package com.crmc.ourcity.rest.request.appeals;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by podo on 04.09.15.
 */
public class WSAddress implements Parcelable {
    public int HouseNumber;
    public String StreetName;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.HouseNumber);
        dest.writeString(this.StreetName);
    }

    public WSAddress() {
    }

    protected WSAddress(Parcel in) {
        this.HouseNumber = in.readInt();
        this.StreetName = in.readString();
    }

    public static final Creator<WSAddress> CREATOR = new Creator<WSAddress>() {
        public WSAddress createFromParcel(Parcel source) {
            return new WSAddress(source);
        }

        public WSAddress[] newArray(int size) {
            return new WSAddress[size];
        }
    };
}
