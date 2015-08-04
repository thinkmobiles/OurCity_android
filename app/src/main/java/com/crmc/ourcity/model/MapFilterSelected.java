package com.crmc.ourcity.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SetKrul on 04.08.2015.
 */
public class MapFilterSelected implements Parcelable{
    public int categoryId;
    public String title;
    public boolean visible;

    public MapFilterSelected(int _categoryId, String _title, boolean _visible){
        this.categoryId = _categoryId;
        this.title = _title;
        this.visible = _visible;
    }

    protected MapFilterSelected(Parcel in) {
        categoryId = in.readInt();
        title = in.readString();
        visible = Boolean.valueOf(in.readString());
    }

    public static final Creator<MapFilterSelected> CREATOR = new Creator<MapFilterSelected>() {
        @Override
        public MapFilterSelected createFromParcel(Parcel in) {
            return new MapFilterSelected(in);
        }

        @Override
        public MapFilterSelected[] newArray(int size) {
            return new MapFilterSelected[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(categoryId);
        dest.writeString(title);
        dest.writeString(String.valueOf(visible));
    }
}
