package com.crmc.ourcity.fragment;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.TypedValue;
import android.view.View;
import android.widget.ListView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.adapter.PhonesListAdapter;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.PhonesLoader;
import com.crmc.ourcity.rest.responce.events.Phones;

import java.util.List;

/**
 * Created by SetKrul on 05.08.2015.
 */
public class PhonesFragment extends BaseFourStatesFragment implements LoaderManager.LoaderCallbacks<List<Phones>>,
        OnRefreshListener {

    private int cityNumber;

    private ListView lvPhones;
    private PhonesListAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static PhonesFragment newInstance() {
        //noinspection deprecation
        return new PhonesFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.BUNDLE_CONSTANT_CITY_NUMBER, cityNumber);
        getLoaderManager().initLoader(1, bundle, this);
    }

    @Override
    protected void initViews() {
        super.initViews();
        swipeRefreshLayout = findView(R.id.swipe_refresh_layout);
        lvPhones = findView(R.id.lvPhones_FP);
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
    public void onViewCreated(final View _view, final Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
        cityNumber = 1;
    }

    @Override
    public void onLoadFinished(Loader<List<Phones>> _loader, List<Phones> _data) {
        swipeRefreshLayout.setRefreshing(false);
        mAdapter = new PhonesListAdapter(getActivity(), _data);
        lvPhones.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        showContent();
    }

    @Override
    public Loader<List<Phones>> onCreateLoader(int _id, Bundle _args) {
        return new PhonesLoader(getActivity(), _args);
    }

    @Override
    public void onLoaderReset(Loader<List<Phones>> _loader) {
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_phones;
    }

    @Override
    public void onRetryClick() {

    }

    @Override
    public void onRefresh() {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.BUNDLE_CONSTANT_CITY_NUMBER, cityNumber);
        getLoaderManager().restartLoader(1, bundle, this);
    }
}
