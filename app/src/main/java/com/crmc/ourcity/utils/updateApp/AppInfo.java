package com.crmc.ourcity.utils.updateApp;

import android.os.Parcel;
import android.os.Parcelable;

public class AppInfo  implements Parcelable{

    public String currentVersion;
    public int statusCode;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.currentVersion);
    }

    public AppInfo() {
    }

    protected AppInfo(Parcel in) {
        this.currentVersion = in.readString();
    }

    public static final Creator<AppInfo> CREATOR = new Creator<AppInfo>() {
        public AppInfo createFromParcel(Parcel source) {
            return new AppInfo(source);
        }

        public AppInfo[] newArray(int size) {
            return new AppInfo[size];
        }
    };
}
