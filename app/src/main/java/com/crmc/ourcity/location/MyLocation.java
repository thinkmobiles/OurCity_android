package com.crmc.ourcity.location;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.crmc.ourcity.R;
import com.crmc.ourcity.callback.LocationCallBack;
import com.crmc.ourcity.model.LocationModel;
import com.crmc.ourcity.rest.RestClientOpenStreetMap;
import com.crmc.ourcity.rest.api.AddressApi;
import com.crmc.ourcity.rest.responce.AddressFull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit.RetrofitError;


/**
 * Created by SetKrul on 15.07.2015.
 */
public class MyLocation implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {

    private Location mLocation;
    private LocationCallBack mCallBack;
    private Context mContext;

    private boolean mRequestingLocationUpdates = false;
    private LocationRequest mLocationRequest;
    private static int UPDATE_INTERVAL = 10000; // 10 sec
    private static int FATEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 10; // 10 meters

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private GoogleApiClient mGoogleApiClient;

    private static final String TAG = "TAG";

    public MyLocation(Context context, LocationCallBack mCallBack) {
        this.mCallBack = mCallBack;
        this.mContext = context;
        if (checkPlayServices()) {
            buildGoogleApiClient();
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(mContext).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(mContext);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, (Activity) mContext,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(mContext, mContext.getResources().getString(R.string.play_service_do_not_supported),
                        Toast.LENGTH_LONG).show();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onConnected(Bundle bundle) {
        getLocation();
    }

    private void getLocation() {
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLocation != null) {
            getAddressSync(mLocation.getLatitude(), mLocation.getLongitude());
        } else {
            createLocationRequest();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mLocation = location;
        if (mLocation != null) {
            getAddressSync(mLocation.getLatitude(), mLocation.getLongitude());
        }
    }

    public void getAddressSync(final Double lat, final Double lon) {
        new AsyncTask<Double, Void, AddressFull>() {

            @Override
            protected AddressFull doInBackground(Double... params) {
                AddressApi api = RestClientOpenStreetMap.getWeatherApi();

                try {
                    return api.getAddress(params[0], params[1]);
                } catch (RetrofitError e) {
                    mCallBack.onFailure(false);
                    Log.d("TAG", e.getKind().name());
                    return null;
                }
            }

            @Override
            protected void onPostExecute(AddressFull addressFull) {
                super.onPostExecute(addressFull);
                if (addressFull != null) {
                    mCallBack.onSuccess(new LocationModel(lat, lon, addressFull.getCity(), addressFull.getStreet(),
                            addressFull.getHouse()));
                } else {
                    mCallBack.onFailure(false);
                }
            }
        }.execute(lat, lon);
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT); // 10 meters
        togglePeriodicLocationUpdates();
    }

    private void togglePeriodicLocationUpdates() {
        if (!mRequestingLocationUpdates) {
            mRequestingLocationUpdates = true;
            if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
                startLocationUpdates();
            }
        } else {
            mRequestingLocationUpdates = false;
            stopLocationUpdates();
        }
    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

    }

    public void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    public void getLocationGoogle(){
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLocation != null) {
            final double lat;
            final double lng;
            String nameCity = "";
            String nameStreet = "";
            Geocoder gcd = new Geocoder(mContext, Locale.getDefault());
            List<Address> addresses;
            try {
                lat = mLocation.getLatitude();
                lng = mLocation.getLongitude();
                addresses = gcd.getFromLocation(lat, lng, 1);
                if (addresses != null) {
                    if (addresses.size() > 0) {
                        Address mAddress = addresses.get(0);
                        nameCity = mAddress.getLocality();
                        nameStreet = mAddress.getAddressLine(0);
                    }
                }
                //mCallBack.onSuccess(new LocationModel(lat, lng, nameCity, nameStreet));
            } catch (IOException e) {
                mCallBack.onFailure(false);
                e.printStackTrace();
            }
        } else {
            createLocationRequest();
        }
    }
}
