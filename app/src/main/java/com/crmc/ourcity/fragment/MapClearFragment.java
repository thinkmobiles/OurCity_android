package com.crmc.ourcity.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.crmc.ourcity.R;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public final class MapClearFragment extends BaseFourStatesFragment implements OnMapReadyCallback {

    private Double lat;
    private Double lon;
    private SupportMapFragment mMap;

    public static MapClearFragment newInstance(Double _lat, Double _lon) {
        MapClearFragment mMapClearFragment = new MapClearFragment();
        Bundle args = new Bundle();
        args.putDouble(Constants.CONFIGURATION_KEY_LAT, _lat);
        args.putDouble(Constants.CONFIGURATION_KEY_LON, _lon);
        mMapClearFragment.setArguments(args);
        return mMapClearFragment;
    }

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        lat = getArguments().getDouble(Constants.CONFIGURATION_KEY_LAT);
        lon = getArguments().getDouble(Constants.CONFIGURATION_KEY_LON);
    }

    @Override
    protected void initViews() {
        //((AppCompatActivity) getActivity()).getDelegate().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_map_clear;
    }

    @Override
    public final void onViewCreated(final View _view, final Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
    }

    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapClear_MCF));
            mMap.getMapAsync(this);
        }
        if (mMap != null) {
            getChildFragmentManager().findFragmentById(R.id.mapClear_MCF);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        configureActionBar(true, true);
        setUpMapIfNeeded();
    }

    @Override
    public final void onRetryClick() {
    }

    @Override
    public void onMapReady(GoogleMap _googleMap) {
        _googleMap.setMyLocationEnabled(true);
        _googleMap.getUiSettings().setZoomControlsEnabled(true);
        setCamera(_googleMap);
        _googleMap.addMarker(new MarkerOptions().position(new LatLng(lat, lon)));
        showContent();
    }

    private void setCamera(GoogleMap _googleMap) {
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(lat, lon)).zoom(12).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        _googleMap.moveCamera(cameraUpdate);
    }
}
