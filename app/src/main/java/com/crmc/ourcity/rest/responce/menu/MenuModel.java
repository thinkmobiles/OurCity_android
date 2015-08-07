package com.crmc.ourcity.rest.responce.menu;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SetKrul on 29.07.2015.
 */
public class MenuModel implements Parcelable {

    @SerializedName("Nodes")
    public List<MenuModel> menu;

    @SerializedName("ActionType")
    public Integer actionType;
    @SerializedName("Color")
    public String colorItem;
    @SerializedName("Link")
    public String link;
    @SerializedName("ListType")
    public Integer listType;
    @SerializedName("MapLatitude")
    public String lat;
    @SerializedName("MapLongitude")
    public String lon;
    @SerializedName("Picture")
    public String iconItem;
    @SerializedName("RequestJson")
    public String requestJson;
    @SerializedName("RequestRoute")
    public String requestRoute;
    @SerializedName("SplashScreen")
    public String splashScreen;
    @SerializedName("Title")
    public String title;

    protected MenuModel(Parcel in) {
        menu = in.createTypedArrayList(MenuModel.CREATOR);
        colorItem = in.readString();
        link = in.readString();
        lat = in.readString();
        lon = in.readString();
        iconItem = in.readString();
        requestJson = in.readString();
        requestRoute = in.readString();
        splashScreen = in.readString();
        title = in.readString();
    }

    public static final Creator<MenuModel> CREATOR = new Creator<MenuModel>() {
        @Override
        public MenuModel createFromParcel(Parcel in) {
            return new MenuModel(in);
        }

        @Override
        public MenuModel[] newArray(int size) {
            return new MenuModel[size];
        }
    };

    public List<MenuModel> getMenu(){
        if (menu != null){
            return menu;
        } else {
            menu = new ArrayList<>();
            //menu.add(new MenuModel());
            return menu;
        }
    }

    public Double getLat(){
        if (lat != null) {
            return Double.parseDouble(lat.replace(',', '.'));
        }
        return null;
    }

    public Double getLon(){
        if (lon != null) {
            return Double.parseDouble(lon.replace(',','.'));
        }
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(menu);
        dest.writeString(colorItem);
        dest.writeString(link);
        dest.writeString(lat);
        dest.writeString(lon);
        dest.writeString(iconItem);
        dest.writeString(requestJson);
        dest.writeString(requestRoute);
        dest.writeString(splashScreen);
        dest.writeString(title);
    }
}
