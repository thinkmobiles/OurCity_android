package com.crmc.ourcity.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.adapter.TripsListAdapter;
import com.crmc.ourcity.callback.OnListItemActionListener;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.MapTripsLoader;
import com.crmc.ourcity.rest.responce.map.MapTrips;
import com.crmc.ourcity.utils.Image;

import java.util.List;

/**
 * Created by SetKrul on 25.08.2015.
 */
public class TripsFragment extends BaseFourStatesFragment implements LoaderManager.LoaderCallbacks<List<MapTrips>>,
        SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    private ListView lvTrips;
    private TripsListAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String color;
    private String json;
    private String route;
    private Double lat;
    private Double lon;
    private OnListItemActionListener mOnListItemActionListener;

    public static TripsFragment newInstance(Double _lat, Double _lon, String _colorItem, String _requestJson, String
            _requestRoute) {
        TripsFragment mTripsFragment = new TripsFragment();
        Bundle args = new Bundle();
        args.putDouble(Constants.CONFIGURATION_KEY_LAT, _lat);
        args.putDouble(Constants.CONFIGURATION_KEY_LON, _lon);
        args.putString(Constants.CONFIGURATION_KEY_COLOR, _colorItem);
        args.putString(Constants.CONFIGURATION_KEY_JSON, _requestJson);
        args.putString(Constants.CONFIGURATION_KEY_ROUTE, _requestRoute);
        mTripsFragment.setArguments(args);
        return mTripsFragment;
    }

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);
        try {
            mOnListItemActionListener = (OnListItemActionListener) _activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(_activity.toString() + " must implement OnListItemActionListener");
        }
    }

    @Override
    public void onDetach() {
        mOnListItemActionListener = null;
        super.onDetach();
    }

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        lat = getArguments().getDouble(Constants.CONFIGURATION_KEY_LAT);
        lon = getArguments().getDouble(Constants.CONFIGURATION_KEY_LON);
        color = getArguments().getString(Constants.CONFIGURATION_KEY_COLOR);
        json = getArguments().getString(Constants.CONFIGURATION_KEY_JSON);
        route = getArguments().getString(Constants.CONFIGURATION_KEY_ROUTE);
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_JSON, json);
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_ROUTE, route);
        getLoaderManager().initLoader(Constants.LOADER_TRIPS_ID, bundle, this);
    }

    @Override
    protected void initViews() {
        super.initViews();
        swipeRefreshLayout = findView(R.id.swipe_refresh_trips);
        lvTrips = findView(R.id.lvTrips_FT);
        Image.init(Color.parseColor(color));
        lvTrips.setDivider(new ColorDrawable(Image.darkenColor(0.2)));
        lvTrips.setDividerHeight(4);
    }

    @Override
    public void onItemClick(AdapterView<?> _parent, View _view, int _position, long _id) {
        mOnListItemActionListener.onTripsItemAction(mAdapter.getItem(_position), lat, lon);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        lvTrips.setOnItemClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeInStart();
    }

    public void swipeInStart() {
        TypedValue typed_value = new TypedValue();
        getActivity().getTheme().resolveAttribute(android.R.attr.actionBarSize, typed_value, true);
        swipeRefreshLayout.setProgressViewOffset(false, 0, getResources().getDimensionPixelSize(typed_value
                .resourceId));
        if (!swipeRefreshLayout.isEnabled()) swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onViewCreated(final View _view, final Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
    }

    @Override
    public void onLoadFinished(Loader<List<MapTrips>> _loader, List<MapTrips> _data) {
        swipeRefreshLayout.setRefreshing(false);
        mAdapter = new TripsListAdapter(getActivity(), _data);
        lvTrips.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        showContent();
    }

    @Override
    public Loader<List<MapTrips>> onCreateLoader(int _id, Bundle _args) {
        return new MapTripsLoader(getActivity(), _args);
    }

    @Override
    public void onLoaderReset(Loader<List<MapTrips>> _loader) {
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_trips;
    }

    @Override
    public void onRetryClick() {
    }

    @Override
    public void onRefresh() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_JSON, json);
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_ROUTE, route);
        getLoaderManager().restartLoader(Constants.LOADER_TRIPS_ID, bundle, this);
    }
}