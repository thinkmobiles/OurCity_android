package com.crmc.ourcity.callback;

import com.crmc.ourcity.rest.responce.menu.MenuModel;

import java.util.List;

public interface OnItemActionListener {
    void onItemAction(final MenuModel _menuModel);

    void onMenuModelPrepared(List<MenuModel> _menuModel);

    void onMessageToResidentDetailTransition(String _message, String _link);

}
