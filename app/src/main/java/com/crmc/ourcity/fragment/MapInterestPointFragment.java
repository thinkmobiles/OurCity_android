package com.crmc.ourcity.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.crmc.ourcity.R;
import com.crmc.ourcity.dialog.DialogActivity;
import com.crmc.ourcity.dialog.DialogType;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.MapInterestPointLoader;
import com.crmc.ourcity.model.MapMarker;
import com.crmc.ourcity.rest.responce.map.MapCategory;
import com.crmc.ourcity.utils.EnumUtil;
import com.crmc.ourcity.utils.Image;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by SetKrul on 31.07.2015.
 */
public final class MapInterestPointFragment extends BaseFourStatesFragment implements OnMapReadyCallback,
        LoaderManager.LoaderCallbacks<List<MapCategory>> {

    private Double lat;
    private Double lon;
    private String color;
    private String json;
    private String route;
    private GoogleMap mGoogleMap;
    private SupportMapFragment mMap;
    private Button btnFilter;
    private List<MapMarker> mDialogMapMarkers;
    private LatLngBounds.Builder bounds;
    private String title;
    private Map<Integer, ArrayList<com.google.android.gms.maps.model.Marker>> mMarkersCategory = new HashMap<>();

    public static MapInterestPointFragment newInstance(Double _lat, Double _lon, String _colorItem, String
            _requestJson, String _requestRoute, String _title) {
        MapInterestPointFragment mMapInterestPointFragment = new MapInterestPointFragment();
        Bundle args = new Bundle();
        args.putDouble(Constants.CONFIGURATION_KEY_LAT, _lat);
        args.putDouble(Constants.CONFIGURATION_KEY_LON, _lon);
        args.putString(Constants.CONFIGURATION_KEY_COLOR, _colorItem);
        args.putString(Constants.CONFIGURATION_KEY_JSON, _requestJson);
        args.putString(Constants.CONFIGURATION_KEY_ROUTE, _requestRoute);
        args.putString(Constants.NODE_TITLE, _title);
        mMapInterestPointFragment.setArguments(args);
        return mMapInterestPointFragment;
    }

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        lat = getArguments().getDouble(Constants.CONFIGURATION_KEY_LAT);
        lon = getArguments().getDouble(Constants.CONFIGURATION_KEY_LON);
        color = getArguments().getString(Constants.CONFIGURATION_KEY_COLOR);
        json = getArguments().getString(Constants.CONFIGURATION_KEY_JSON);
        route = getArguments().getString(Constants.CONFIGURATION_KEY_ROUTE);
        title = getArguments().getString(Constants.NODE_TITLE);
    }


    @Override
    protected int getContentView() {
        return R.layout.fragment_map_interest_point;
    }

    @Override
    public final void onViewCreated(final View _view, final Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
    }


    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapInterestPoint_MIPF));
            mMap.getMapAsync(this);
        }
        if (mMap != null) {
            getChildFragmentManager().findFragmentById(R.id.mapInterestPoint_MIPF);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        configureActionBar(true, true, title);
        setUpMapIfNeeded();
    }

    @Override
    public void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
        super.onActivityResult(_requestCode, _resultCode, _data);
        switch (_requestCode) {
            case Constants.REQUEST_MARKER_FILTER:
                if (_data != null) {
                    if (!(_data.getIntExtra(Constants.REQUEST_TYPE, 0) == Constants.REQUEST_CANCEL)) {
                        mDialogMapMarkers = _data.getParcelableArrayListExtra(Constants.BUNDLE_MARKERS);
                        setFilterableMarkers();
                    }
                }
                break;
        }
    }

    private void setFilterableMarkers() {
        for (int i = 0; i < mDialogMapMarkers.size(); i++) {
            Set<Map.Entry<Integer, ArrayList<com.google.android.gms.maps.model.Marker>>> set = mMarkersCategory
                    .entrySet();
            for (Map.Entry<Integer, ArrayList<com.google.android.gms.maps.model.Marker>> me : set) {
                if (me.getKey() == mDialogMapMarkers.get(i).categoryId) {
                    for (int j = 0; j < me.getValue().size(); j++) {
                        me.getValue().get(j).setVisible(mDialogMapMarkers.get(i).visible);
                    }
                }
            }
        }
    }



    @Override
    protected void initViews() {
        super.initViews();
        btnFilter = findView(R.id.btnMarkerFilter_MIPF);
        try {
            Image.init(Color.parseColor(color));
        } catch (Exception e){
            Image.init(Color.BLACK);
        }
        Image.setBackgroundColorView(getActivity(), btnFilter, R.drawable.btn_selector_mf, Image.darkenColor(0.2));
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        btnFilter.setOnClickListener(handleClick());
    }

    @NonNull
    private OnClickListener handleClick() {
        return v -> {
            switch (v.getId()) {
                case R.id.btnMarkerFilter_MIPF:
                    //if (mDialogMarkers != null) {
                    Intent intent = new Intent(getActivity(), DialogActivity.class);
                    EnumUtil.serialize(DialogType.class, DialogType.MARKER_FILTER).to(intent);
                    intent.putParcelableArrayListExtra(Constants.BUNDLE_MARKERS, (ArrayList<? extends
                            Parcelable>) mDialogMapMarkers);
                    startActivityForResult(intent, Constants.REQUEST_MARKER_FILTER);
                    //} else {
                    //    Toast.makeText(getActivity(), "Do not have interested points!", Toast.LENGTH_SHORT).show();
                    //}
                    break;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<MapCategory>> _loader, List<MapCategory> _data) {
        if (mDialogMapMarkers == null) {
            bounds = new LatLngBounds.Builder();
            ArrayList<com.google.android.gms.maps.model.Marker> temp = new ArrayList<>();
            mDialogMapMarkers = new ArrayList<>();
            if (_data != null) {
                try {
                    for (int i = 0; i < _data.size(); i++) {
                        for (int j = 0; j < _data.get(i).getInterestedPointList().size(); j++) {
                            temp.add(mGoogleMap.addMarker(new MarkerOptions().title("\u200e" + _data.get(i)
                                    .getInterestedPointDescription(j)).position(new LatLng(_data.get(i)
                                    .getInterestedPointLat(j), _data.get(i).getInterestedPointLon(j))).icon
                                    (BitmapDescriptorFactory.fromBitmap(Image.convertBase64ToBitmap(_data.get(i)
                                            .icon)))));
                            bounds.include(new LatLng(_data.get(i).getInterestedPointLat(j), _data.get(i)
                                    .getInterestedPointLon(j)));
                        }
                        mMarkersCategory.put(_data.get(i).categoryId, new ArrayList<>(temp));
                        mDialogMapMarkers.add(new MapMarker(_data.get(i).categoryId, _data.get(i).categoryName, true));
                        temp.clear();
                    }
                    setCamera(mGoogleMap);
                } catch (Exception e) {
                    showError(getResources().getString(R.string.connection_error));
                }
            } else {
                showError(getResources().getString(R.string.connection_error));
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<List<MapCategory>> _loader) {
    }

    @Override
    public Loader<List<MapCategory>> onCreateLoader(int _id, Bundle _args) {
        return new MapInterestPointLoader(getActivity(), _args);
    }

    @Override
    public final void onRetryClick() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_JSON, json);
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_ROUTE, route);
        getLoaderManager().restartLoader(Constants.LOADER_INTERESTED_POINTS_ID, bundle, this);
    }

    @Override
    public void onMapReady(GoogleMap _googleMap) {
        _googleMap.setMyLocationEnabled(true);
        _googleMap.getUiSettings().setZoomControlsEnabled(true);
        this.mGoogleMap = _googleMap;
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_JSON, json);
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_ROUTE, route);
        getLoaderManager().initLoader(Constants.LOADER_INTERESTED_POINTS_ID, bundle, this);
    }

    private void setCamera(GoogleMap _googleMap) {
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.30);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds.build(), width, height, padding);
        _googleMap.moveCamera(cameraUpdate);
        showContent();
    }
}
