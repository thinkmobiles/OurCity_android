package com.crmc.ourcity.rest.responce.map;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SetKrul on 30.07.2015.
 */
public class MapTripsDetails implements Parcelable{

    @SerializedName("lat")
    public String lat;
    @SerializedName("latLangInfo")
    public String latLangInfo;
    @SerializedName("latLngId")
    public Integer latLngId;
    @SerializedName("latLngName")
    public String latLngName;
    @SerializedName("latLngType")
    public Integer latLngType;
    @SerializedName("lng")
    public String lng;
    @SerializedName("tripId")
    public Integer tripId;

    protected MapTripsDetails(Parcel in) {
        lat = in.readString();
        latLangInfo = in.readString();
        latLngId = in.readInt();
        latLngName = in.readString();
        latLngType = in.readInt();
        lng = in.readString();
        tripId = in.readInt();
    }

    public static final Creator<MapTripsDetails> CREATOR = new Creator<MapTripsDetails>() {
        @Override
        public MapTripsDetails createFromParcel(Parcel in) {
            return new MapTripsDetails(in);
        }

        @Override
        public MapTripsDetails[] newArray(int size) {
            return new MapTripsDetails[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(lat);
        dest.writeString(latLangInfo);
        dest.writeInt(latLngId);
        dest.writeString(latLngName);
        dest.writeInt(latLngType);
        dest.writeString(lng);
        dest.writeInt(tripId);
    }
}
