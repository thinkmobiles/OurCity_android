package com.crmc.ourcity.dialog;

import com.crmc.ourcity.model.MapFilterSelected;

import java.util.List;

/**
 * Created by SetKrul on 04.08.2015.
 */
public interface OnActionDialogListenerWithData {
    void onActionDialogDataSelected(List<MapFilterSelected> _list);

    void onActionDialogDataCancel(boolean check);
}
