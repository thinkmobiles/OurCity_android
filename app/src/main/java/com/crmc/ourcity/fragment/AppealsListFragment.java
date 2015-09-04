package com.crmc.ourcity.fragment;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.widget.ListView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.AppealsLoader;
import com.crmc.ourcity.rest.responce.appeals.WSResult;

/**
 * Created by podo on 04.09.15.
 */
public class AppealsListFragment extends BaseFourStatesFragment implements LoaderManager.LoaderCallbacks<WSResult> {
    private ListView lvAppeals;
    private String color;
    private String json;
    private String route;

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
        showContent();
        getLoaderManager().initLoader(Constants.LOADER_APPEALS_ID, bundle, this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_city_entities;
    }

    @Override
    public void onRetryClick() {

    }

    @Override
    public Loader<WSResult> onCreateLoader(int id, Bundle args) {
        return new AppealsLoader(getActivity(), args);
    }

    @Override
    public void onLoadFinished(Loader<WSResult> loader, WSResult data) {

    }

    @Override
    public void onLoaderReset(Loader<WSResult> loader) {

    }
}
