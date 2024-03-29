package com.crmc.ourcity.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.activity.MainActivity;
import com.crmc.ourcity.adapter.CityEntitiesListAdapter;
import com.crmc.ourcity.callback.OnListItemActionListener;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.CityEntitiesLoader;
import com.crmc.ourcity.rest.responce.events.CityEntities;
import com.crmc.ourcity.utils.Image;

import java.lang.ref.WeakReference;
import java.util.List;

public class CityEntitiesFragment extends BaseFourStatesFragment implements TextWatcher, LoaderManager.LoaderCallbacks<List<CityEntities>> {

    private ListView lvCityEntities;
    private EditText etSearch_CEF;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String color;
    private String json;
    private String route;
    private String title;
    private WeakReference<MainActivity> mActivity;

    private CityEntitiesListAdapter mAdapter;
    private OnListItemActionListener mOnListItemActionListener;

    public static CityEntitiesFragment newInstance(String _colorItem, String _requestJson, String _requestRoute, String _title) {
        CityEntitiesFragment mCityEntitiesFragment = new CityEntitiesFragment();
        Bundle args = new Bundle();
        args.putString(Constants.CONFIGURATION_KEY_COLOR, _colorItem);
        args.putString(Constants.CONFIGURATION_KEY_JSON, _requestJson);
        args.putString(Constants.CONFIGURATION_KEY_ROUTE, _requestRoute);
        args.putString(Constants.NODE_TITLE, _title);
        mCityEntitiesFragment.setArguments(args);
        return mCityEntitiesFragment;
    }

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);
        try {
            mActivity = new WeakReference<>((MainActivity) _activity);
            mOnListItemActionListener = (OnListItemActionListener) _activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(_activity.toString() + " must implement OnListItemActionListener");
        }
    }

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        color = getArguments().getString(Constants.CONFIGURATION_KEY_COLOR);
        json = getArguments().getString(Constants.CONFIGURATION_KEY_JSON);
        route = getArguments().getString(Constants.CONFIGURATION_KEY_ROUTE);
        title = getArguments().getString(Constants.NODE_TITLE);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_city_entities;
    }

    @Override
    protected void initViews() {
        super.initViews();
        swipeRefreshLayout = findView(R.id.swipe_refresh_city_entities);
        etSearch_CEF = findView(R.id.etSearch_CEF);
        lvCityEntities = findView(R.id.lvCityEntities_CEF);
        View vUnderLine_CEF = findView(R.id.vUnderLine_CEF);
        try {
            Image.init(Color.parseColor(color));
        } catch (Exception e) {
            Image.init(Color.BLACK);
        }
        vUnderLine_CEF.setBackgroundColor(Image.darkenColor(0.2));
        lvCityEntities.setDivider(new ColorDrawable(Image.darkenColor(0.2)));
        lvCityEntities.setDividerHeight(4);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        lvCityEntities.setOnItemClickListener(handleItemClick());
        swipeRefreshLayout.setOnRefreshListener(this::loadCityEntities);
        swipeInStart();
        etSearch_CEF.addTextChangedListener(this);
    }

    @Override
    public void onViewCreated(final View _view, final Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
    }

    @Override
    public void onResume() {
        configureActionBar(true, true, title);
        super.onResume();
        loadCityEntities();
    }

    @Override
    public void onDetach() {
        mOnListItemActionListener = null;
        etSearch_CEF.removeTextChangedListener(this);
        swipeRefreshLayout.setOnRefreshListener(null);
        lvCityEntities.setOnItemClickListener(null);
        hideKeyboard(mActivity.get(), etSearch_CEF);
        mActivity.clear();
        super.onDetach();
    }

    @Override
    public void onLoadFinished(Loader<List<CityEntities>> _loader, List<CityEntities> _data) {
        swipeRefreshLayout.setRefreshing(false);
        if (_data != null) {
            if (_data.size() > 0) {
                mAdapter = new CityEntitiesListAdapter(mActivity.get(), _data, mOnListItemActionListener);
                lvCityEntities.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                showContent();
            } else showEmpty(getResources().getString(R.string.text_when_no_results_in_list));
        } else {
            showError(getResources().getString(R.string.connection_error));
        }
    }

    @Override
    public Loader<List<CityEntities>> onCreateLoader(int _id, Bundle _args) {
        return new CityEntitiesLoader(mActivity.get(), _args);
    }

    @Override
    public void onLoaderReset(Loader<List<CityEntities>> _loader) {

    }

    @NonNull
    private AdapterView.OnItemClickListener handleItemClick() {
        return (_parent, _view, _position, _id) -> {
            hideKeyboard(mActivity.get());
            mOnListItemActionListener.onCityEntitiesItemAction(mAdapter.getItem(_position));
            etSearch_CEF.setText("");
        };
    }

    public void swipeInStart() {
        TypedValue typed_value = new TypedValue();
        mActivity.get().getTheme().resolveAttribute(android.R.attr.actionBarSize, typed_value, true);
        swipeRefreshLayout.setProgressViewOffset(false, 0, getResources().getDimensionPixelSize(typed_value
                .resourceId));
        if (!swipeRefreshLayout.isEnabled()) swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onRetryClick() {
        loadCityEntities();
    }

    private void loadCityEntities() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_JSON, json);
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_ROUTE, route);
        getLoaderManager().restartLoader(Constants.LOADER_CITY_ENTITIES_ID, bundle, this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (mAdapter != null) {
            mAdapter.getFilter().filter(s.toString());
        }
    }
}
