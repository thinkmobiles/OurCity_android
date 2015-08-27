package com.crmc.ourcity.callback;

import com.crmc.ourcity.rest.responce.events.Events;
import com.crmc.ourcity.rest.responce.events.Phones;
import com.crmc.ourcity.rest.responce.map.MapTrips;

import java.util.List;

/**
 * Created by SetKrul on 19.08.2015.
 */
public interface OnListItemActionListener {
    void onEventsItemAction(Events _events);
    void onTripsItemAction(MapTrips _trips, Double _lat, Double _lon);
    void onEventsClickLinkAction(String _link);
    void onPhoneBookItemAction(List<Phones> _phones);
}
