package com.crmc.ourcity.callback;

import android.view.View;

import com.crmc.ourcity.model.rss.RSSEntry;
import com.crmc.ourcity.rest.responce.appeals.ResultObject;
import com.crmc.ourcity.rest.responce.events.CityEntities;
import com.crmc.ourcity.rest.responce.events.Events;
import com.crmc.ourcity.rest.responce.events.Phones;
import com.crmc.ourcity.rest.responce.map.MapTrips;
import com.crmc.ourcity.rest.responce.vote.VoteFull;

import java.util.List;

/**
 * Created by SetKrul on 19.08.2015.
 */
public interface OnListItemActionListener {
    void onAppealsItemAction(ResultObject resultObject);

    void onEventsItemAction(Events _events);

    void onCityEntitiesItemAction(CityEntities _cityEntities);

    void onTripsItemAction(MapTrips _trips, Double _lat, Double _lon);

    void onEventsClickLinkAction(String _link, String _title);

    void onPhoneBookItemAction(List<Phones> _phones);

    void onRSSItemAction(RSSEntry _entry);

    void onTickerAction(View _view, String _link, String _title);

    void onActionMail(String _mail);

    void onActionCall(String _number);

    void onVoteAction(VoteFull _voteFull);
}
