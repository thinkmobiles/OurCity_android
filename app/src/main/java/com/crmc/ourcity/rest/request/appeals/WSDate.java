package com.crmc.ourcity.rest.request.appeals;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by podo on 04.09.15.
 */
public class WSDate implements Parcelable {
    public int Day ;
    public int Month ;
    public int Year ;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.Day);
        dest.writeInt(this.Month);
        dest.writeInt(this.Year);
    }

    public WSDate() {
    }

    protected WSDate(Parcel in) {
        this.Day = in.readInt();
        this.Month = in.readInt();
        this.Year = in.readInt();
    }

    public static final Creator<WSDate> CREATOR = new Creator<WSDate>() {
        public WSDate createFromParcel(Parcel source) {
            return new WSDate(source);
        }

        public WSDate[] newArray(int size) {
            return new WSDate[size];
        }
    };
}
