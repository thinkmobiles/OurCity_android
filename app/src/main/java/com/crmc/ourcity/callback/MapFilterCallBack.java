package com.crmc.ourcity.callback;

import com.crmc.ourcity.model.Marker;

import java.util.List;

/**
 * Created by SetKrul on 04.08.2015.
 */
public interface MapFilterCallBack {
    void onActionDialogDataSelected(List<Marker> _list);

    void onActionDialogDataCancel(boolean _check);
}
