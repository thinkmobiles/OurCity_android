package com.crmc.ourcity.callback;

import com.crmc.ourcity.model.MapFilterSelected;

import java.util.List;

/**
 * Created by SetKrul on 04.08.2015.
 */
public interface MapFilterCallBack {
    void onActionDialogDataSelected(List<MapFilterSelected> _list);

    void onActionDialogDataCancel(boolean check);
}
