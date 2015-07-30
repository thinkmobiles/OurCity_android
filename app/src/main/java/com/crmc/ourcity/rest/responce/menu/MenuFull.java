package com.crmc.ourcity.rest.responce.menu;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by SetKrul on 29.07.2015.
 */
public class MenuFull {

    @SerializedName("Nodes")
    public List<MenuModel> menuList;

    public Integer getActionType(int element) {
        if (menuList != null && menuList.size() > 0) {
            return menuList.get(element).actionType;
        }
        return null;
    }

    public String getColorItem(int element) {
        if (menuList != null && menuList.size() > 0) {
            return menuList.get(element).colorItem;
        }
        return null;
    }

    public String getLink(int element) {
        if (menuList != null && menuList.size() > 0) {
            return menuList.get(element).link;
        }
        return null;
    }

    public Integer getListType(int element) {
        if (menuList != null && menuList.size() > 0) {
            return menuList.get(element).listType;
        }
        return null;
    }

    public String getIconItem(int element) {
        if (menuList != null && menuList.size() > 0) {
            return menuList.get(element).iconItem;
        }
        return null;
    }

    public String getTitle(int element){
        if (menuList != null && menuList.size() > 0) {
            return menuList.get(element).title;
        }
        return null;
    }

    public Double getLat(int element){
        if (menuList != null && menuList.size() > 0) {
            return Double.parseDouble((menuList.get(element).lat).replace(',','.'));
        }
        return null;
    }

    public Double getLon(int element){
        if (menuList != null && menuList.size() > 0) {
            return Double.parseDouble((menuList.get(element).lon).replace(',','.'));
        }
        return null;
    }
}
