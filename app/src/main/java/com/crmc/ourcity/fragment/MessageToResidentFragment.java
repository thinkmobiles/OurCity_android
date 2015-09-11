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
import com.crmc.ourcity.adapter.MassageToResidentAdapter;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.MassageToResidentLoader;
import com.crmc.ourcity.rest.responce.events.MassageToResident;
import com.crmc.ourcity.utils.Image;

import java.util.List;

/**
 * Created by SetKrul on 28.08.2015.
 */
public class MessageToResidentFragment extends BaseFourStatesFragment implements LoaderManager
        .LoaderCallbacks<List<MassageToResident>>, SwipeRefreshLayout.OnRefreshListener {

    private ListView lvMassageToResident;
    private View vUnderLine_MTRF;
    private String color;
    private String json;
    private String route;
    private SwipeRefreshLayout swipeRefreshLayout;

    private MassageToResidentAdapter mAdapter;

    public static MessageToResidentFragment newInstance(String _colorItem, String _requestJson, String _requestRoute) {
        MessageToResidentFragment mMessageToResidentFragment = new MessageToResidentFragment();
        Bundle args = new Bundle();
        args.putString(Constants.CONFIGURATION_KEY_COLOR, _colorItem);
        args.putString(Constants.CONFIGURATION_KEY_JSON, _requestJson);
        args.putString(Constants.CONFIGURATION_KEY_ROUTE, _requestRoute);
        mMessageToResidentFragment.setArguments(args);
        return mMessageToResidentFragment;
    }

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        getLoaderManager().initLoader(Constants.LOADER_MESSAGE_TO_RESIDENT, bundle, this);
    }

    @Override
    public void onLoadFinished(Loader<List<MassageToResident>> _loader, List<MassageToResident> _data) {
        swipeRefreshLayout.setRefreshing(false);
        if (_data != null) {
            if (_data.size() > 0) {
                mAdapter = new MassageToResidentAdapter(getActivity(), _data);
                lvMassageToResident.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                showContent();
            } else {
                //showError(getResources().getString(R.string.connection_error));
                showEmpty("There are no messages for you");
            }
        } else {
            showEmpty("There are no messages for you");
        }
    }

    @Override
    public Loader<List<MassageToResident>> onCreateLoader(int _id, Bundle _args) {
        return new MassageToResidentLoader(getActivity(), _args);
    }

    @Override
    public void onLoaderReset(Loader<List<MassageToResident>> _loader) {
    }

    @Override
    protected void initViews() {
        super.initViews();
        //noinspection ConstantConditions
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        swipeRefreshLayout = findView(R.id.swipe_refresh_message_to_resident);
        lvMassageToResident = findView(R.id.lvMassageToResident_MTRF);
        vUnderLine_MTRF = findView(R.id.vUnderLine_MTRF);
        try {
            Image.init(Color.parseColor(color));
        } catch (Exception e){
            Image.init(Color.BLACK);
        }
        vUnderLine_MTRF.setBackgroundColor(Image.darkenColor(0.2));
        lvMassageToResident.setDivider(new ColorDrawable(Image.darkenColor(0.2)));
        lvMassageToResident.setDividerHeight(4);
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
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_message_to_resident;
    }

    @Override
    public void onRetryClick() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_JSON, json);
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_ROUTE, route);
        getLoaderManager().restartLoader(Constants.LOADER_MESSAGE_TO_RESIDENT, bundle, this);
    }

    @Override
    public void onRefresh() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_JSON, json);
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_ROUTE, route);
        getLoaderManager().restartLoader(Constants.LOADER_MESSAGE_TO_RESIDENT, bundle, this);
    }
}
