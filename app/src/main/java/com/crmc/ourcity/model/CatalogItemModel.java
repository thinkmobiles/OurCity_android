package com.crmc.ourcity.model;

/**
 * Created by SetKrul on 15.07.2015.
 */
public class CatalogItemModel {
    public String title;
    public String date;
    public String address;
    public ItemClickAction itemStatus;

    public CatalogItemModel(String title, ItemClickAction itemStatus){
        this.title = title;
        this.itemStatus = itemStatus;
    }

    public CatalogItemModel(String title, String date, ItemClickAction itemStatus){
        this.title = title;
        this.date = date;
        this.itemStatus = itemStatus;
    }

    public CatalogItemModel(String title, String date, String address, ItemClickAction itemStatus){
        this.title = title;
        this.date = date;
        this.address = address;
        this.itemStatus = itemStatus;
    }
}
