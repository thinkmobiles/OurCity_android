package com.crmc.ourcity.rest.responce.events;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SetKrul on 30.07.2015.
 */
public class Events implements Parcelable {

    @SerializedName("address")
    public String address;
    @SerializedName("description")
    public String description;
    @SerializedName("email")
    public String email;
    @SerializedName("eventDate")
    public String eventDate;
    @SerializedName("eventDateToMobileClient")
    public String eventDateToMobileClient;
    @SerializedName("eventId")
    public Integer eventId;
    @SerializedName("link")
    public String link;
    @SerializedName("mobile")
    public String mobile;
    @SerializedName("notes")
    public String notes;
    @SerializedName("phone")
    public String phone;
    @SerializedName("picture")
    public String picture;
    @SerializedName("price")
    public String price;
    @SerializedName("title")
    public String title;

    protected Events(Parcel in) {
        address = in.readString();
        description = in.readString();
        eventDate = in.readString();
        email = in.readString();
        eventDateToMobileClient = in.readString();
        eventId = in.readInt();
        link = in.readString();
        mobile = in.readString();
        notes = in.readString();
        phone = in.readString();
        picture = in.readString();
        price = in.readString();
        title = in.readString();
    }

    public static final Creator<Events> CREATOR = new Creator<Events>() {
        @Override
        public Events createFromParcel(Parcel in) {
            return new Events(in);
        }

        @Override
        public Events[] newArray(int size) {
            return new Events[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeString(description);
        dest.writeString(eventDate);
        dest.writeString(email);
        dest.writeString(eventDateToMobileClient);
        dest.writeInt(eventId);
        dest.writeString(link);
        dest.writeString(mobile);
        dest.writeString(notes);
        dest.writeString(phone);
        dest.writeString(picture);
        dest.writeString(price);
        dest.writeString(title);
    }
}
