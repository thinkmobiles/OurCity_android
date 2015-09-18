package com.crmc.ourcity.rest.responce.interestAreas;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by andrei on 17.09.15.
 */
public class InterestingArea implements Parcelable {
    public int cityId;
    public int lastModifyUserId;
    public int interestAreaId;
    public String interestAreaName;
    public int interestAreaToResidentId;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.cityId);
        dest.writeInt(this.lastModifyUserId);
        dest.writeInt(this.interestAreaId);
        dest.writeString(this.interestAreaName);
        dest.writeInt(this.interestAreaToResidentId);
    }

    public InterestingArea() {
    }

    protected InterestingArea(Parcel in) {
        this.cityId = in.readInt();
        this.lastModifyUserId = in.readInt();
        this.interestAreaId = in.readInt();
        this.interestAreaName = in.readString();
        this.interestAreaToResidentId = in.readInt();
    }

    public static final Creator<InterestingArea> CREATOR = new Creator<InterestingArea>() {
        public InterestingArea createFromParcel(Parcel source) {
            return new InterestingArea(source);
        }

        public InterestingArea[] newArray(int size) {
            return new InterestingArea[size];
        }
    };
}
