package com.crmc.ourcity.rest.request.appeals;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by podo on 04.09.15.
 */
public class WSPhoneNumber implements Parcelable {
    public String AreaCode;
    public String PhoneNumber;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.AreaCode);
        dest.writeString(this.PhoneNumber);
    }

    public WSPhoneNumber() {
    }

    protected WSPhoneNumber(Parcel in) {
        this.AreaCode = in.readString();
        this.PhoneNumber = in.readString();
    }

    public static final Creator<WSPhoneNumber> CREATOR = new Creator<WSPhoneNumber>() {
        public WSPhoneNumber createFromParcel(Parcel source) {
            return new WSPhoneNumber(source);
        }

        public WSPhoneNumber[] newArray(int size) {
            return new WSPhoneNumber[size];
        }
    };
}
