package com.crmc.ourcity.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.adapter.CityEntitiesListAdapter;
import com.crmc.ourcity.callback.OnListItemActionListener;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.CityEntitiesLoader;
import com.crmc.ourcity.rest.responce.events.CityEntities;
import com.crmc.ourcity.utils.Image;

import java.util.List;

/**
 * Created by SetKrul on 31.08.2015.
 */
public class CityEntitiesFragment  extends BaseFourStatesFragment implements LoaderManager.LoaderCallbacks<List<CityEntities>>,
        AdapterView.OnItemClickListener {

    private ListView lvCityEntities;
    private View vUnderLine_CEF;
    private String color;
    private String json;
    private String route;

    private CityEntitiesListAdapter mAdapter;
    private OnListItemActionListener mOnListItemActionListener;

    public static CityEntitiesFragment newInstance(String _colorItem, String _requestJson, String _requestRoute) {
        CityEntitiesFragment mCityEntitiesFragment = new CityEntitiesFragment();
        Bundle args = new Bundle();
        args.putString(Constants.CONFIGURATION_KEY_COLOR, _colorItem);
        args.putString(Constants.CONFIGURATION_KEY_JSON, _requestJson);
        args.putString(Constants.CONFIGURATION_KEY_ROUTE, _requestRoute);
        mCityEntitiesFragment.setArguments(args);
        return mCityEntitiesFragment;
    }

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        color = getArguments().getString(Constants.CONFIGURATION_KEY_COLOR);
        json = getArguments().getString(Constants.CONFIGURATION_KEY_JSON);
        route = getArguments().getString(Constants.CONFIGURATION_KEY_ROUTE);
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
    public void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_JSON, json);
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_ROUTE, route);
        getLoaderManager().initLoader(Constants.LOADER_CITY_ENTITIES_ID, bundle, this);
    }

    @Override
    public void onLoadFinished(Loader<List<CityEntities>> _loader, List<CityEntities> _data) {
        if (_data != null) {
            mAdapter = new CityEntitiesListAdapter(getActivity(), _data);
            lvCityEntities.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            showContent();
        } else {
            showError("Server do not response!");
        }
    }

    @Override
    public Loader<List<CityEntities>> onCreateLoader(int _id, Bundle _args) {
        return new CityEntitiesLoader(getActivity(), _args);
    }

    @Override
    public void onLoaderReset(Loader<List<CityEntities>> _loader) {
    }

    @Override
    protected void initViews() {
        super.initViews();
        lvCityEntities = findView(R.id.lvCityEntities_CEF);
        vUnderLine_CEF = findView(R.id.vUnderLine_CEF);
        Image.init(Color.parseColor(color));
        vUnderLine_CEF.setBackgroundColor(Image.darkenColor(0.2));
        lvCityEntities.setDivider(new ColorDrawable(Image.darkenColor(0.2)));
        lvCityEntities.setDividerHeight(4);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        lvCityEntities.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> _parent, View _view, int _position, long _id) {
        mOnListItemActionListener.onCityEntitiesItemAction(mAdapter.getItem(_position));
    }

    @Override
    public void onViewCreated(final View _view, final Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_city_entities;
    }

    @Override
    public void onRetryClick() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_JSON, json);
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_ROUTE, route);
        getLoaderManager().restartLoader(Constants.LOADER_CITY_ENTITIES_ID, bundle, this);
    }
}
