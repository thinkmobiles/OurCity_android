package com.crmc.ourcity.rest.responce.appeals;

import android.os.Parcel;
import android.os.Parcelable;

public class Location implements Parcelable {
    public String HouseNumber;
    public String StreetName;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.HouseNumber);
        dest.writeString(this.StreetName);
    }

    public Location() {
    }

    protected Location(Parcel in) {
        this.HouseNumber = in.readString();
        this.StreetName = in.readString();
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        public Location createFromParcel(Parcel source) {
            return new Location(source);
        }

        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
}
