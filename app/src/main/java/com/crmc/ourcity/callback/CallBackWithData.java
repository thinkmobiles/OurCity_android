package com.crmc.ourcity.callback;

import com.crmc.ourcity.model.MapMarker;

import java.util.List;

/**
 * Created by SetKrul on 04.08.2015.
 */
public interface CallBackWithData {
    void onActionDialogDataMarker(List<MapMarker> _list);

    void onActionDialogDataInteger(Integer _integer);

    void onActionDialogVote(Integer _integer, boolean _active);

    void onActionDialogCancel(boolean _check);
}
