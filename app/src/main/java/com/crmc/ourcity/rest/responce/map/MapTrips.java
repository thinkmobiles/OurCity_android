package com.crmc.ourcity.rest.responce.map;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by SetKrul on 30.07.2015.
 */
public class MapTrips implements Parcelable{

    @SerializedName("latLngList")
    public List<MapTripsDetails> mapTripsDetails;

    @SerializedName("description")
    public String description;
    @SerializedName("tripId")
    public Integer tripId;
    @SerializedName("tripName")
    public String tripName;

    protected MapTrips(Parcel in) {
        mapTripsDetails = in.createTypedArrayList(MapTripsDetails.CREATOR);
        description = in.readString();
        tripId = in.readInt();
        tripName = in.readString();
    }

    public static final Creator<MapTrips> CREATOR = new Creator<MapTrips>() {
        @Override
        public MapTrips createFromParcel(Parcel in) {
            return new MapTrips(in);
        }

        @Override
        public MapTrips[] newArray(int size) {
            return new MapTrips[size];
        }
    };

    public String getInfo(int element){
        return mapTripsDetails.get(element).latLangInfo;
    }

    public Double getTripsLat(int element){
        return Double.parseDouble((mapTripsDetails.get(element).lat).replace(',', '.'));
    }

    public Double getTripsLon(int element){
        return Double.parseDouble((mapTripsDetails.get(element).lng).replace(',', '.'));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mapTripsDetails);
        dest.writeString(description);
        dest.writeInt(tripId);
        dest.writeString(tripName);
    }
}
