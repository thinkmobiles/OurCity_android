package com.crmc.ourcity.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.crmc.ourcity.R;
import com.crmc.ourcity.activity.MainActivity;
import com.crmc.ourcity.adapter.AppealsAdapter;
import com.crmc.ourcity.callback.OnItemActionListener;
import com.crmc.ourcity.callback.OnListItemActionListener;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.AppealsLoader;
import com.crmc.ourcity.rest.responce.appeals.WSResult;
import com.crmc.ourcity.utils.Image;
import com.crmc.ourcity.utils.SPManager;

import java.lang.ref.WeakReference;

public class AppealsListFragment extends BaseFourStatesFragment implements LoaderManager.LoaderCallbacks<WSResult>,
        SwipeRefreshLayout.OnRefreshListener {
    private ListView lvAppeals;
    private String color;
    private String json;
    private String route;
    private String title;
    private SwipeRefreshLayout swipeRefreshLayout;
    private OnListItemActionListener mOnListItemActionListener;
    private WeakReference<MainActivity> mActivity;
    private AppealsAdapter mAdapter;

    public static AppealsListFragment newInstance(String _colorItem, String _requestJson, String _requestRoute, String _title) {
        AppealsListFragment mAppealsListFragment = new AppealsListFragment();
        Bundle args = new Bundle();
        args.putString(Constants.CONFIGURATION_KEY_COLOR, _colorItem);
        args.putString(Constants.CONFIGURATION_KEY_JSON, _requestJson);
        args.putString(Constants.CONFIGURATION_KEY_ROUTE, _requestRoute);
        args.putString(Constants.NODE_TITLE, _title);
        mAppealsListFragment.setArguments(args);
        return mAppealsListFragment;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        color = getArguments().getString(Constants.CONFIGURATION_KEY_COLOR);
        json = getArguments().getString(Constants.CONFIGURATION_KEY_JSON);
        route = getArguments().getString(Constants.CONFIGURATION_KEY_ROUTE);
        title = getArguments().getString(Constants.NODE_TITLE, "");
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_appealslist;
    }

    @Override
    protected void initViews() {
        super.initViews();
        swipeRefreshLayout = findView(R.id.swipe_refresh_list_appeals);
        lvAppeals = findView(R.id.lvCityEntities_CEF);
        View vUnderLine_ALF = findView(R.id.vUnderLine_ALF);
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
        //TODO: uncomment this line to get detail Appeal
        lvAppeals.setOnItemClickListener(handleItemClick());
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeInStart();
    }

    @Override
    public void onResume() {
        configureActionBar(true, true, title);
        super.onResume();
        amountOfVisibleTickets = getAmountOfVisibleClosedTickets(SPManager.getInstance(mActivity.get()).getAmountOfVisibleTickets());
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_JSON, json);
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_ROUTE, route);
        getLoaderManager().initLoader(Constants.LOADER_APPEALS_ID, bundle, this);
    }

    @Override
    public void onDetach() {
        mOnListItemActionListener = null;
        mActivity.clear();
        super.onDetach();
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
        return new AppealsLoader(mActivity.get(), args);
    }

    @Override
    public void onLoadFinished(Loader<WSResult> loader, WSResult data) {
        swipeRefreshLayout.setRefreshing(false);
        if (data != null) {
            if (data.getResultObjects() != null && data.getResultObjects().size() > 0) {
                mAdapter = new AppealsAdapter(mActivity.get(), data.getResultObjects(), color, amountOfVisibleTickets);
                lvAppeals.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                showContent();
            } else {
                showEmpty(getResources().getString(R.string.no_appeals_found));
            }
        } else {
            showError(getResources().getString(R.string.connection_error));
        }
    }

    @NonNull
    private AdapterView.OnItemClickListener handleItemClick() {
        return (parent, view, position, id) -> mOnListItemActionListener.onAppealsItemAction(mAdapter.getItem(position));
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
