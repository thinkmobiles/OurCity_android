package com.crmc.ourcity.callback;

import com.crmc.ourcity.rest.responce.events.Events;

/**
 * Created by SetKrul on 19.08.2015.
 */
public interface OnListItemActionListener {
    void onEventsItemAction(Events _events);
    void onEventsClickLinkAction(String _link);
}
