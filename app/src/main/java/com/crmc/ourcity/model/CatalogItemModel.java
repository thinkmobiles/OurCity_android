package com.crmc.ourcity.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SetKrul on 15.07.2015.
 */
public class CatalogItemModel implements Parcelable {
    public String title;
    public String date;
    public String address;
    public ItemClickAction itemStatus;
    public CatalogSizeView catalogSizeView;

    public CatalogItemModel(String title, ItemClickAction itemStatus){
        this.title = title;
        this.itemStatus = itemStatus;
        this.catalogSizeView = CatalogSizeView.SMALL;
    }

    public CatalogItemModel(String title, String date, ItemClickAction itemStatus){
        this.title = title;
        this.date = date;
        this.itemStatus = itemStatus;
        this.catalogSizeView = CatalogSizeView.MEDIUM;
    }

    public CatalogItemModel(String title, String date, String address, ItemClickAction itemStatus){
        this.title = title;
        this.date = date;
        this.address = address;
        this.itemStatus = itemStatus;
        this.catalogSizeView = CatalogSizeView.FULL;
    }

    protected CatalogItemModel(Parcel in) {
        title = in.readString();
        date = in.readString();
        address = in.readString();
        itemStatus = ItemClickAction.values()[in.readInt()];
        catalogSizeView = CatalogSizeView.values()[in.readInt()];
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(title);
        parcel.writeString(date);
        parcel.writeString(address);
        parcel.writeInt(itemStatus.ordinal());
        parcel.writeInt(catalogSizeView.ordinal());
    }

    public static final Creator<CatalogItemModel> CREATOR = new Creator<CatalogItemModel>() {
        @Override
        public CatalogItemModel createFromParcel(Parcel in) {
            return new CatalogItemModel(in);
        }

        @Override
        public CatalogItemModel[] newArray(int size) {
            return new CatalogItemModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}