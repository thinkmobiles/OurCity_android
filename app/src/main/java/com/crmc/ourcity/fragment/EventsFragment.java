package com.crmc.ourcity.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.adapter.EventsListAdapter;
import com.crmc.ourcity.callback.OnListItemActionListener;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.EventsLoader;
import com.crmc.ourcity.rest.responce.events.Events;

import java.util.List;

/**
 * Created by SetKrul on 15.07.2015.
 */
public class EventsFragment extends BaseFourStatesFragment implements LoaderManager.LoaderCallbacks<List<Events>>,
        OnItemClickListener {

    private static final String CONFIGURATION_KEY_COLOR = "KEY_COLOR";
    private static final String CONFIGURATION_KEY_jSON = "KEY_JSON";
    private static final String CONFIGURATION_KEY_ROUTE = "KEY_ROUTE";
    private ListView lvEvents;
    private String color;
    private String json;
    private String route;

    private EventsListAdapter mAdapter;
    private OnListItemActionListener mOnListItemActionListener;

    public static EventsFragment newInstance(String _colorItem, String _requestJson, String _requestRoute) {
        EventsFragment mEventsFragment = new EventsFragment();
        Bundle args = new Bundle();
        args.putString(CONFIGURATION_KEY_COLOR, _colorItem);
        args.putString(CONFIGURATION_KEY_jSON, _requestJson);
        args.putString(CONFIGURATION_KEY_ROUTE, _requestRoute);
        mEventsFragment.setArguments(args);
        return mEventsFragment;
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
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        color = getArguments().getString(CONFIGURATION_KEY_COLOR);
        json = getArguments().getString(CONFIGURATION_KEY_jSON);
        route = getArguments().getString(CONFIGURATION_KEY_ROUTE);
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_JSON, json);
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_ROUTE, route);
        getLoaderManager().initLoader(Constants.LOADER_EVENTS_ID, bundle, this);
    }

    @Override
    public void onLoadFinished(Loader<List<Events>> _loader, List<Events> _data) {
        mAdapter = new EventsListAdapter(getActivity(), _data);
        lvEvents.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        showContent();
    }

    @Override
    public Loader<List<Events>> onCreateLoader(int _id, Bundle _args) {
        return new EventsLoader(getActivity(), _args);
    }

    @Override
    public void onLoaderReset(Loader<List<Events>> _loader) {
    }

    @Override
    protected void initViews() {
        super.initViews();
        lvEvents = findView(R.id.lvEvents_EF);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        lvEvents.setOnItemClickListener(this);
    }

    @Override
    public void onViewCreated(final View _view, final Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
    }

    @Override
    public void onItemClick(AdapterView<?> _parent, View _view, int _position, long _id) {
        mOnListItemActionListener.onEventsItemAction(mAdapter.getItem(_position));
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_events;
    }

    @Override
    public void onRetryClick() {
    }

    @Override
    public void onDetach() {
        mOnListItemActionListener = null;
        super.onDetach();
    }
}
