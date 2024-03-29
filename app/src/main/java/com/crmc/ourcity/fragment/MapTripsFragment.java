package com.crmc.ourcity.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

import com.annimon.stream.Stream;
import com.crmc.ourcity.R;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.responce.map.MapTrips;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public final class MapTripsFragment extends BaseFourStatesFragment implements OnMapReadyCallback, OnClickListener {

    private Double lat;
    private Double lon;
    private SupportMapFragment mMap;
//    private Button btnAnotherTrips;
    private MapTrips mMapTrips;
    private LatLngBounds.Builder bounds;
    private String title;

    public static MapTripsFragment newInstance(MapTrips _trips, Double _lat, Double _lon, String _title) {
        MapTripsFragment mMapTripsFragment = new MapTripsFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.CONFIGURATION_KEY_TRIPS, _trips);
        args.putDouble(Constants.CONFIGURATION_KEY_LAT, _lat);
        args.putDouble(Constants.CONFIGURATION_KEY_LON, _lon);
        args.putString(Constants.NODE_TITLE, _title);
        mMapTripsFragment.setArguments(args);
        return mMapTripsFragment;
    }

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        mMapTrips = getArguments().getParcelable(Constants.CONFIGURATION_KEY_TRIPS);
        lat = getArguments().getDouble(Constants.CONFIGURATION_KEY_LAT);
        lon = getArguments().getDouble(Constants.CONFIGURATION_KEY_LON);
        this.title = getArguments().getString(Constants.NODE_TITLE, "");
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_map_trips;
    }

    @Override
    public final void onViewCreated(final View _view, final Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
    }

    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapTrips_MTF));
            mMap.getMapAsync(this);
        }
        if (mMap != null) {
            getChildFragmentManager().findFragmentById(R.id.mapTrips_MTF);
        }
    }

    @Override
    public void onResume() {
        configureActionBar(true, true, title);
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    public void onClick(View _view) {
        switch (_view.getId()) {
//            case R.id.btnAnotherTrips_MTF:
//                popBackStack();
//                break;
        }
    }

    @Override
    protected void initViews() {
        super.initViews();

//        btnAnotherTrips = findView(R.id.btnAnotherTrips_MTF);
//        Image.setBackgroundColorView(getActivity(), btnAnotherTrips, R.drawable.btn_selector_mf,
//                Image.darkenColor(0.2));
    }

    @Override
    protected void setListeners() {
        super.setListeners();
//        btnAnotherTrips.setOnClickListener(this);
    }

    @Override
    public final void onRetryClick() {
    }

    @Override
    public void onMapReady(final GoogleMap _googleMap) {
        _googleMap.setMyLocationEnabled(true);
        _googleMap.getUiSettings().setZoomControlsEnabled(true);
        bounds = new LatLngBounds.Builder();
        if (mMapTrips.mapTripsDetails.size() > 0) {
            for (int i = 0; i < mMapTrips.mapTripsDetails.size(); i++) {
                if (!TextUtils.isEmpty(mMapTrips.getInfo(i))) {
                    _googleMap.addMarker(new MarkerOptions().title("\u200e" + mMapTrips.getInfo(i)).position(new LatLng
                            (mMapTrips.getTripsLat(i), mMapTrips.getTripsLon(i))));
                } else {
                    _googleMap.addMarker(new MarkerOptions().position(new LatLng
                            (mMapTrips.getTripsLat(i), mMapTrips.getTripsLon(i)))).hideInfoWindow();
                }
                bounds.include(new LatLng(mMapTrips.getTripsLat(i), mMapTrips.getTripsLon(i)));

                if (i > 0) {
                    PolylineOptions polylineOptions = new PolylineOptions().add(new LatLng(mMapTrips.getTripsLat(i - 1),
                            mMapTrips.getTripsLon(i - 1))).add(new LatLng(mMapTrips.getTripsLat(i), mMapTrips.getTripsLon
                            (i))).color(Color.RED).width(3);
                    _googleMap.addPolyline(polylineOptions);
                }
            }
        } else {
            bounds.include(new LatLng(0, 0));
        }
        setCamera(_googleMap);
    }

    private void setCamera(GoogleMap _googleMap) {
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.30);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds.build(), width, height, padding);
        //_googleMap.animateCamera(cameraUpdate);
        _googleMap.moveCamera(cameraUpdate);
        showContent();
    }
}