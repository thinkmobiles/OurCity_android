package com.crmc.ourcity.loader;

import android.content.Context;
import android.os.Bundle;

import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.RestClientOpenStreetMap;
import com.crmc.ourcity.rest.api.AddressApi;
import com.crmc.ourcity.rest.responce.address.AddressFull;
import com.crmc.ourcity.rest.responce.address.AddressDetails;

import retrofit.RetrofitError;

public class AddressLoader extends BaseLoader<AddressFull> {

    private double lat, lon;

    public AddressLoader(Context _context, Bundle _args) {
        super(_context);
        lat = _args.getDouble(Constants.BUNDLE_CONSTANTS_LAT);
        lon = _args.getDouble(Constants.BUNDLE_CONSTANTS_LON);
    }

    @Override
    public AddressFull loadInBackground() {
        AddressApi api = RestClientOpenStreetMap.getAddressApi();
        AddressFull addressFull;
        try {
            addressFull = api.getAddress(lat, lon);
        } catch (RetrofitError e) {
            addressFull = new AddressFull();
            addressFull.address = new AddressDetails();
        }
        return addressFull;
    }
}