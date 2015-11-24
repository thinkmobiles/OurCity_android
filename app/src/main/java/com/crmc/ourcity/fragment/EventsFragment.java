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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.activity.MainActivity;
import com.crmc.ourcity.adapter.EventsListAdapter;
import com.crmc.ourcity.callback.OnListItemActionListener;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.EventsLoader;
import com.crmc.ourcity.rest.responce.events.Events;
import com.crmc.ourcity.utils.Image;

import java.lang.ref.WeakReference;
import java.util.List;

public class EventsFragment extends BaseFourStatesFragment implements LoaderManager.LoaderCallbacks<List<Events>> {

    private ListView lvEvents;
    private String color;
    private String json;
    private String route;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String title;

    private EventsListAdapter mAdapter;
    private OnListItemActionListener mOnListItemActionListener;
    private WeakReference<MainActivity> mActivity;

    public static EventsFragment newInstance(String _colorItem, String _requestJson, String _requestRoute, String _title) {
        EventsFragment mEventsFragment = new EventsFragment();

        Bundle args = new Bundle();
        args.putString(Constants.CONFIGURATION_KEY_COLOR, _colorItem);
        args.putString(Constants.CONFIGURATION_KEY_JSON, _requestJson);
        args.putString(Constants.CONFIGURATION_KEY_ROUTE, _requestRoute);
        args.putString(Constants.NODE_TITLE, _title);
        mEventsFragment.setArguments(args);
        return mEventsFragment;
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
        return R.layout.fragment_events;
    }

    @Override
    protected void initViews() {
        super.initViews();
        swipeRefreshLayout = findView(R.id.swipe_refresh_events);
        lvEvents = findView(R.id.lvEvents_EF);
        View vUnderLine_EF = findView(R.id.vUnderLine_EF);
        try {
            Image.init(Color.parseColor(color));
        } catch (Exception e){
            Image.init(Color.BLACK);
        }
        vUnderLine_EF.setBackgroundColor(Image.darkenColor(0.2));
        lvEvents.setDivider(new ColorDrawable(Image.darkenColor(0.2)));
        lvEvents.setDividerHeight(4);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        lvEvents.setOnItemClickListener(handleEventItemClick());
        swipeRefreshLayout.setOnRefreshListener(this::loadEvents);
        swipeInStart();
    }

    @Override
    public void onViewCreated(final View _view, final Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
    }

    @Override
    public void onResume() {
        configureActionBar(true, true, title);
        super.onResume();
        loadEvents();
    }

    @Override
    public void onDetach() {
        mOnListItemActionListener = null;
        mActivity.clear();
        super.onDetach();
    }

    private void loadEvents() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_JSON, json);
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_ROUTE, route);
        getLoaderManager().restartLoader(Constants.LOADER_EVENTS_ID, bundle, this);
    }

    @Override
    public void onLoadFinished(Loader<List<Events>> _loader, List<Events> _data) {
        swipeRefreshLayout.setRefreshing(false);
        if (_data != null) {
            if (_data.size() > 0) {
                mAdapter = new EventsListAdapter(mActivity.get(), _data, mOnListItemActionListener);
                lvEvents.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                showContent();
            } else showEmpty(getResources().getString(R.string.text_when_no_results_in_list));
        } else {
            showError(getResources().getString(R.string.connection_error));
        }
    }

    @Override
    public Loader<List<Events>> onCreateLoader(int _id, Bundle _args) {
        return new EventsLoader(mActivity.get(), _args);
    }

    @Override
    public void onLoaderReset(Loader<List<Events>> _loader) {
    }

    @NonNull
    private OnItemClickListener handleEventItemClick() {
        return (_parent, _view, _position, id) -> mOnListItemActionListener.onEventsItemAction(mAdapter.getItem(_position));
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
        loadEvents();
    }
}
