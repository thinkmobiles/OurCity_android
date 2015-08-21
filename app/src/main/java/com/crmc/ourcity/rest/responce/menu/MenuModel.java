package com.crmc.ourcity.rest.responce.menu;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SetKrul on 29.07.2015.
 */
public class MenuModel implements Parcelable{

    @SerializedName("Nodes")
    public List<MenuModel> menu;

    @SerializedName("ActionType")
    public Integer actionType;
    @SerializedName("Color")
    public String colorItem;
    @SerializedName("Email")
    public String email;
    @SerializedName("Link")
    public String link;
    @SerializedName("ListType")
    public Integer listType;
    @SerializedName("MapLatitude")
    public String lat;
    @SerializedName("MapLongitude")
    public String lon;
    @SerializedName("PhoneNumber")
    public String phoneNumber;
    @SerializedName("Picture")
    public String iconItem;
    @SerializedName("RequestJson")
    public String requestJson;
    @SerializedName("RequestRoute")
    public String requestRoute;
    @SerializedName("RequestLogin")
    public String requestLogin;
//    @SerializedName("ReturnType")
//    public Integer returnType;
    @SerializedName("Title")
    public String title;

    protected MenuModel(Parcel in) {
        menu = in.createTypedArrayList(MenuModel.CREATOR);
        actionType = in.readInt();
        colorItem = in.readString();
        email = in.readString();
        link = in.readString();
        listType = in.readInt();
        lat = in.readString();
        lon = in.readString();
        phoneNumber = in.readString();
        iconItem = in.readString();
        requestJson = in.readString();
        requestRoute = in.readString();
        requestLogin = in.readString();
//        returnType = in.readInt();
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
        dest.writeInt(actionType);
        dest.writeString(colorItem);
        dest.writeString(email);
        dest.writeString(link);
        dest.writeInt(listType);
        dest.writeString(lat);
        dest.writeString(lon);
        dest.writeString(phoneNumber);
        dest.writeString(iconItem);
        dest.writeString(requestJson);
        dest.writeString(requestRoute);
        dest.writeString(requestLogin);
//        dest.writeInt(returnType);
        dest.writeString(title);
    }
}
