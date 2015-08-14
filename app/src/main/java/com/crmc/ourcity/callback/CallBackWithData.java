package com.crmc.ourcity.callback;

import com.crmc.ourcity.model.Marker;

import java.util.List;

/**
 * Created by SetKrul on 04.08.2015.
 */
public interface CallBackWithData {
    void onActionDialogDataMarker(List<Marker> _list);

    void onActionDialogDataInteger(Integer _integer);

    void onActionDialogCancel(boolean _check);
}
