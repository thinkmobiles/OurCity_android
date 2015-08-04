package com.crmc.ourcity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.crmc.ourcity.R;
import com.crmc.ourcity.dialog.DialogActivity;
import com.crmc.ourcity.dialog.DialogType;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.MapDataLoader;
import com.crmc.ourcity.model.MapFilterSelected;
import com.crmc.ourcity.rest.responce.map.MapCategory;
import com.crmc.ourcity.utils.EnumUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by SetKrul on 31.07.2015.
 */
public final class MapsFragment extends BaseFourStatesFragment implements OnMapReadyCallback, LoaderManager
        .LoaderCallbacks<List<MapCategory>>, OnClickListener {

    private GoogleMap mGoogleMap;
    private int cityNumber;
    private String lng;

    private SupportMapFragment mMap;
    private Button btnFilter;
    private List<MapFilterSelected> mDialogMapFilterSelecteds = new ArrayList<>();
    private Map<Integer, ArrayList<Marker>> mMarkersCategory = new HashMap<>();

    public static MapsFragment newInstance() {
        return new MapsFragment();
    }


    @Override
    protected int getContentView() {
        return R.layout.fragment_map;
    }

    @Override
    public final void onViewCreated(final View _view, final Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
    }


    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map));
            mMap.getMapAsync(this);
        }
        if (mMap != null) {
            getChildFragmentManager().findFragmentById(R.id.map);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        cityNumber = 1;
        lng = "en";
        setUpMapIfNeeded();
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), DialogActivity.class);
        EnumUtil.serialize(DialogType.class, DialogType.FILTER_MAP).to(intent);
        intent.putParcelableArrayListExtra(MapFilterSelected.class.getCanonicalName(), (ArrayList<? extends
                Parcelable>) mDialogMapFilterSelecteds);
        startActivityForResult(intent, Constants.REQUEST_MAP_FILTER);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.REQUEST_MAP_FILTER:
                if (data != null) {
                    if (data.getIntExtra(Constants.REQUEST_MAP_FILTER_TYPE, 0) == Constants.REQUEST_MAP_SELECTED) {
                        mDialogMapFilterSelecteds = data.getParcelableArrayListExtra(MapFilterSelected.class
                                .getCanonicalName());
                        setFilterableMarkers();
                    }
                }
                break;
        }
    }

    private void setFilterableMarkers() {
        for (int i = 0; i < mDialogMapFilterSelecteds.size(); i++) {
            Set<Map.Entry<Integer, ArrayList<Marker>>> set = mMarkersCategory.entrySet();
            for (Map.Entry<Integer, ArrayList<Marker>> me : set) {
                if (me.getKey() == mDialogMapFilterSelecteds.get(i).categoryId) {
                    for (int j = 0; j < me.getValue().size(); j++) {
                        me.getValue().get(j).setVisible(mDialogMapFilterSelecteds.get(i).visible);
                    }
                }
            }
        }
    }

    @Override
    protected void initViews() {
        super.initViews();
        btnFilter = findView(R.id.map_btn_point);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        btnFilter.setOnClickListener(this);
    }

    @Override
    public void onLoadFinished(Loader<List<MapCategory>> loader, List<MapCategory> data) {
        ArrayList<Marker> temp = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(i).getInterestedPointList().size(); j++) {
                temp.add(mGoogleMap.addMarker(new MarkerOptions().title("\u200e" + data.get(i)
                        .getInterestedPointDescription(j)).position(new LatLng(data.get(i).getInterestedPointLat(j),
                        data.get(i).getInterestedPointLon(j)))));
            }
            mMarkersCategory.put(data.get(i).categoryId, new ArrayList<>(temp));
            mDialogMapFilterSelecteds.add(new MapFilterSelected(data.get(i).categoryId, data.get(i).categoryName,
                    false));
            temp.clear();
        }
        showContent();
    }

    @Override
    public void onLoaderReset(Loader<List<MapCategory>> loader) {
    }

    @Override
    public Loader<List<MapCategory>> onCreateLoader(int id, Bundle args) {
        return new MapDataLoader(getActivity(), args);
    }

    @Override
    public final void onRetryClick() {
    }

    @Override
    public void onMapReady(GoogleMap _googleMap) {
        _googleMap.setMyLocationEnabled(true);
        _googleMap.getUiSettings().setZoomControlsEnabled(true);
        setCamera(_googleMap);
        this.mGoogleMap = _googleMap;
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.BUNDLE_CONSTANT_CITY_NUMBER, cityNumber);
        bundle.putString(Constants.BUNDLE_CONSTANT_LANG, lng);
        getLoaderManager().initLoader(1, bundle, this);
    }

    private void setCamera(GoogleMap googleMap) {
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(32.441364, 34.922662)).zoom
                (12).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        googleMap.moveCamera(cameraUpdate);
    }
}
