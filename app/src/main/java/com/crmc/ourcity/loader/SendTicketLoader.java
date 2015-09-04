package com.crmc.ourcity.loader;

import android.content.Context;
import android.os.Bundle;

import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.RestClientApi;
import com.crmc.ourcity.rest.api.CityApi;
import com.crmc.ourcity.rest.request.appeals.NewTicketObj;
import com.crmc.ourcity.rest.responce.appeals.WSResult;

import retrofit.RetrofitError;

/**
 * Created by podo on 04.09.15.
 */
public class SendTicketLoader extends BaseLoader<WSResult> {
    NewTicketObj ticket;

    public SendTicketLoader(Context _context, Bundle _bundle) {
        super(_context);
        ticket = _bundle.getParcelable(Constants.BUNDLE_CONSTANT_PARCELABLE_TICKET);
    }

    @Override
    public WSResult loadInBackground() {
        CityApi api = RestClientApi.getCityApi();
        WSResult wsResult;

        try {
            wsResult = api.sendTicket(ticket);
        } catch (RetrofitError e) {
            wsResult = new WSResult();
        }

        return wsResult;
    }
}
