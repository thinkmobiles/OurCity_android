package com.crmc.ourcity.fragment;

import com.crmc.ourcity.model.CatalogItemModel;
import com.crmc.ourcity.rest.responce.menu.MenuModel;

import java.util.List;

/**
 * Created by podo on 05.08.15.
 */
public interface ListItemAction {
    void onItemAction(final CatalogItemModel catalogItemModel);
    void onMenuModelPrepared(List<MenuModel> _menuModel);
}
