package com.crmc.ourcity.rest.responce.mobileUISettings;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class MobileUISettings implements Parcelable {
    public List<SettingItem> properties;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(properties);
    }

    public MobileUISettings() {
    }

    protected MobileUISettings(Parcel in) {
        this.properties = in.createTypedArrayList(SettingItem.CREATOR);
    }

    public static final Creator<MobileUISettings> CREATOR = new Creator<MobileUISettings>() {
        public MobileUISettings createFromParcel(Parcel source) {
            return new MobileUISettings(source);
        }

        public MobileUISettings[] newArray(int size) {
            return new MobileUISettings[size];
        }
    };
}
