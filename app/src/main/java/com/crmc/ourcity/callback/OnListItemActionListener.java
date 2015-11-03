package com.crmc.ourcity.callback;

import android.view.View;

import com.crmc.ourcity.rest.responce.appeals.ResultObject;
import com.crmc.ourcity.rest.responce.events.CityEntities;
import com.crmc.ourcity.rest.responce.events.Events;
import com.crmc.ourcity.rest.responce.events.MassageToResident;
import com.crmc.ourcity.rest.responce.events.Phones;
import com.crmc.ourcity.rest.responce.map.MapTrips;
import com.crmc.ourcity.rest.responce.vote.VoteFull;
import com.crmc.ourcity.utils.rss.RssItem;

import java.util.List;

public interface OnListItemActionListener {
    void onAppealsItemAction(ResultObject resultObject);

    void onEventsItemAction(Events _events);

    void onCityEntitiesItemAction(CityEntities _cityEntities);

    void onTripsItemAction(MapTrips _trips, Double _lat, Double _lon);

    void onEventsClickLinkAction(String _link, String _title);

    void onPhoneBookItemAction(List<Phones> _phones);

    void onRSSItemAction(RssItem _entry);

    void onTickerAction(View _view, String _link, String _title);

    void onActionMail(String _mail);

    void onActionCall(String _number);

    void onVoteAction(VoteFull _voteFull);

    void onMessageItenAction(MassageToResident _mtr);
}
