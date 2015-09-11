package com.crmc.ourcity.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.ListView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.adapter.AppealsAdapter;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.AppealsLoader;
import com.crmc.ourcity.rest.responce.appeals.WSResult;
import com.crmc.ourcity.utils.Image;

/**
 * Created by podo on 04.09.15.
 */
public class AppealsListFragment extends BaseFourStatesFragment implements LoaderManager.LoaderCallbacks<WSResult>,
        SwipeRefreshLayout.OnRefreshListener {
    private ListView lvAppeals;
    private View vUnderLine_ALF;
    private String color;
    private String json;
    private String route;
    private SwipeRefreshLayout swipeRefreshLayout;

    private AppealsAdapter mAdapter;

    public static AppealsListFragment newInstance(String _colorItem, String _requestJson, String _requestRoute) {
        AppealsListFragment mAppealsListFragment = new AppealsListFragment();
        Bundle args = new Bundle();
        args.putString(Constants.CONFIGURATION_KEY_COLOR, _colorItem);
        args.putString(Constants.CONFIGURATION_KEY_JSON, _requestJson);
        args.putString(Constants.CONFIGURATION_KEY_ROUTE, _requestRoute);
        mAppealsListFragment.setArguments(args);
        return mAppealsListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        getLoaderManager().initLoader(Constants.LOADER_APPEALS_ID, bundle, this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_appealslist;
    }

    @Override
    public void onRetryClick() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_JSON, json);
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_ROUTE, route);
        getLoaderManager().restartLoader(Constants.LOADER_APPEALS_ID, bundle, this);
        showLoading();
    }

    @Override
    public Loader<WSResult> onCreateLoader(int id, Bundle args) {
        return new AppealsLoader(getActivity(), args);
    }

    @Override
    public void onLoadFinished(Loader<WSResult> loader, WSResult data) {
        swipeRefreshLayout.setRefreshing(false);
        if (data != null) {
            mAdapter = new AppealsAdapter(getActivity(), data.getResultObjects());
            lvAppeals.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            showContent();
        } else {
            showError(getResources().getString(R.string.connection_error));
        }
    }

    @Override
    protected void initViews() {
        super.initViews();
        //noinspection ConstantConditions
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        swipeRefreshLayout = findView(R.id.swipe_refresh_list_appeals);
        lvAppeals = findView(R.id.lvCityEntities_CEF);
        vUnderLine_ALF = findView(R.id.vUnderLine_ALF);
        try {
            Image.init(Color.parseColor(color));
        } catch (Exception e) {
            Image.init(Color.BLACK);
        }
        vUnderLine_ALF.setBackgroundColor(Image.darkenColor(0.2));
        lvAppeals.setDivider(new ColorDrawable(Image.darkenColor(0.2)));
        lvAppeals.setDividerHeight(4);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
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
    public void onLoaderReset(Loader<WSResult> loader) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_JSON, json);
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_ROUTE, route);
        getLoaderManager().restartLoader(Constants.LOADER_APPEALS_ID, bundle, this);
    }

    @Override
    public void onRefresh() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_JSON, json);
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_ROUTE, route);
        getLoaderManager().restartLoader(Constants.LOADER_APPEALS_ID, bundle, this);
    }
}
