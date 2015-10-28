package com.crmc.ourcity.rest.responce.mobileUISettings;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by andrei on 22.10.15.
 */
public class SettingItem implements Parcelable {
    public String propertyName;
    public Integer propertyValue;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.propertyName);
        dest.writeValue(this.propertyValue);
    }

    public SettingItem() {
    }

    protected SettingItem(Parcel in) {
        this.propertyName = in.readString();
        this.propertyValue = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<SettingItem> CREATOR = new Creator<SettingItem>() {
        public SettingItem createFromParcel(Parcel source) {
            return new SettingItem(source);
        }

        public SettingItem[] newArray(int size) {
            return new SettingItem[size];
        }
    };
}
