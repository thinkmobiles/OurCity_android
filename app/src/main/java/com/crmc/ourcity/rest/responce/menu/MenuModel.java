package com.crmc.ourcity.rest.responce.menu;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.annimon.stream.Stream;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MenuModel implements Parcelable {

    @SerializedName("Nodes")
    public List<MenuModel> menu;

    @SerializedName("ActionType")
    public Integer actionType;
    @SerializedName("Color")
    public String colorItem;
    @SerializedName("BorderColor")
    public String borderColor;
    @SerializedName("BorderWidth")
    public Integer borderWidth;
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
    @SerializedName("ShowOnMap")
    public Integer showOnMap;
    @SerializedName("Title")
    public String title;

    protected MenuModel(Parcel in) {
        menu = in.createTypedArrayList(MenuModel.CREATOR);
        actionType = in.readInt();
        colorItem = in.readString();
        borderColor = in.readString();
        borderWidth = in.readInt();
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
        showOnMap = in.readInt();
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

    public List<MenuModel> getMenu() {
        if (menu != null) {
            return menu;
        } else {
            menu = new ArrayList<>();
            return menu;
        }
    }

    public Double getLat() {
        if (!TextUtils.isEmpty(lat)) {
            return Double.parseDouble(lat.replace(',', '.'));
        }
        return null;
    }

    public Double getLon() {
        if (!TextUtils.isEmpty(lon)) {
            return Double.parseDouble(lon.replace(',', '.'));
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
        dest.writeString(borderColor);
        dest.writeInt(borderWidth);
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
        dest.writeInt(showOnMap);
        dest.writeString(title);
    }
}
