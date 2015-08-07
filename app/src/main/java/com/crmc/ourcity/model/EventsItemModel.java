package com.crmc.ourcity.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SetKrul on 15.07.2015.
 */
public class EventsItemModel implements Parcelable {
    public String title;
    public String date;
    public String address;
    public ActionType actionType;
    public CatalogSizeView catalogSizeView;

    public EventsItemModel(String _title, ActionType _actionType){
        this.title = _title;
        this.actionType = _actionType;
        this.catalogSizeView = CatalogSizeView.SMALL;
    }

    public EventsItemModel(String _title, String _date, ActionType _actionType){
        this.title = _title;
        this.date = _date;
        this.actionType = _actionType;
        this.catalogSizeView = CatalogSizeView.MEDIUM;
    }

    public EventsItemModel(String _title, String _date, String _address, ActionType _actionType){
        this.title = _title;
        this.date = _date;
        this.address = _address;
        this.actionType = _actionType;
        this.catalogSizeView = CatalogSizeView.FULL;
    }

    protected EventsItemModel(Parcel _in) {
        title = _in.readString();
        date = _in.readString();
        address = _in.readString();
        actionType = ActionType.values()[_in.readInt()];
        catalogSizeView = CatalogSizeView.values()[_in.readInt()];
    }

    @Override
    public void writeToParcel(Parcel _parcel, int _flags) {
        _parcel.writeString(title);
        _parcel.writeString(date);
        _parcel.writeString(address);
        _parcel.writeInt(actionType.ordinal());
        _parcel.writeInt(catalogSizeView.ordinal());
    }

    public static final Creator<EventsItemModel> CREATOR = new Creator<EventsItemModel>() {
        @Override
        public EventsItemModel createFromParcel(Parcel _in) {
            return new EventsItemModel(_in);
        }

        @Override
        public EventsItemModel[] newArray(int _size) {
            return new EventsItemModel[_size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}