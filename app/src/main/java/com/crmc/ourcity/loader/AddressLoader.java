package com.crmc.ourcity.loader;

import android.content.Context;
import android.os.Bundle;

import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.RestClientOpenStreetMap;
import com.crmc.ourcity.rest.api.AddressApi;
import com.crmc.ourcity.rest.responce.AddressFull;
import com.crmc.ourcity.rest.responce.AddressModel;

import retrofit.RetrofitError;

/**
 * Created by SetKrul on 28.07.2015.
 */
public class AddressLoader extends BaseLoader<AddressFull> {

    private double lat, lon;

    public AddressLoader(Context context, Bundle args) {
        super(context);
        lat = args.getDouble(Constants.CONSTANTS_LAT);
        lon = args.getDouble(Constants.CONSTANTS_LON);
    }

    @Override
    public AddressFull loadInBackground() {
        AddressApi api = RestClientOpenStreetMap.getAddressApi();
        AddressFull addressFull;
        try {
            addressFull = api.getAddress(lat, lon);
        } catch (RetrofitError e) {
            addressFull = new AddressFull();
            addressFull.addressList = new AddressModel();
        }
        return addressFull;
    }
}
