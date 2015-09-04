package com.crmc.ourcity.rest.responce.ticker;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by podo on 03.09.15.
 */
public class TickerModel implements Parcelable {
    public String link;
    public String title;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.link);
        dest.writeString(this.title);
    }

    public TickerModel() {
    }

    protected TickerModel(Parcel in) {
        this.link = in.readString();
        this.title = in.readString();
    }

    public static final Creator<TickerModel> CREATOR = new Creator<TickerModel>() {
        public TickerModel createFromParcel(Parcel source) {
            return new TickerModel(source);
        }

        public TickerModel[] newArray(int size) {
            return new TickerModel[size];
        }
    };
}
