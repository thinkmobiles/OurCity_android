package com.crmc.ourcity.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
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
public class AppealsListFragment extends BaseFourStatesFragment implements LoaderManager.LoaderCallbacks<WSResult> {
    private ListView lvAppeals;
    private String color;
    private String json;
    private String route;

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
        if (data != null){
            mAdapter = new AppealsAdapter(getActivity(), data.getResultObjects());
            lvAppeals.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            showContent();
        } else {
            showError("Server do not response!");
        }
    }

    @Override
    protected void initViews() {
        super.initViews();
        lvAppeals = findView(R.id.lvCityEntities_CEF);
        Image.init(Color.parseColor(color));
        lvAppeals.setDivider(new ColorDrawable(Image.darkenColor(0.2)));
        lvAppeals.setDividerHeight(4);
    }

    @Override
    public void onLoaderReset(Loader<WSResult> loader) {

    }
}
