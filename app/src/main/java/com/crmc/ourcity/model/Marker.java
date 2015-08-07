package com.crmc.ourcity.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SetKrul on 04.08.2015.
 */
public class Marker implements Parcelable{
    public int categoryId;
    public String title;
    public boolean visible;

    public Marker(int _categoryId, String _title, boolean _visible){
        this.categoryId = _categoryId;
        this.title = _title;
        this.visible = _visible;
    }

    protected Marker(Parcel _in) {
        categoryId = _in.readInt();
        title = _in.readString();
        visible = Boolean.valueOf(_in.readString());
    }

    public static final Creator<Marker> CREATOR = new Creator<Marker>() {
        @Override
        public Marker createFromParcel(Parcel _in) {
            return new Marker(_in);
        }

        @Override
        public Marker[] newArray(int _size) {
            return new Marker[_size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel _dest, int _flags) {
        _dest.writeInt(categoryId);
        _dest.writeString(title);
        _dest.writeString(String.valueOf(visible));
    }
}
