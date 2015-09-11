package com.crmc.ourcity.callback;

import com.crmc.ourcity.rest.responce.menu.MenuModel;

import java.util.List;

/**
 * Created by podo on 05.08.15.
 */
public interface OnItemActionListener {
    void onItemAction(final MenuModel _menuModel);

    void onMenuModelPrepared(List<MenuModel> _menuModel);
}
