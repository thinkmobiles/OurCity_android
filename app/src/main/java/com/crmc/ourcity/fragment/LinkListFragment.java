package com.crmc.ourcity.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.adapter.LinksListAdapter;
import com.crmc.ourcity.callback.OnListItemActionListener;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.EventsLoader;
import com.crmc.ourcity.rest.responce.events.Events;
import com.crmc.ourcity.utils.Image;

import java.util.List;

/**
 * Created by SetKrul on 09.09.2015.
 */
public class LinkListFragment extends BaseFourStatesFragment implements LoaderManager.LoaderCallbacks<List<Events>>, AdapterView.OnItemClickListener {

    private ListView lvLinks;
    private View vUnderLine_LLF;
    private String color;
    private String json;
    private String route;

    private LinksListAdapter mAdapter;
    private OnListItemActionListener mOnListItemActionListener;

    public static LinkListFragment newInstance(String _colorItem, String _requestJson, String _requestRoute) {
        LinkListFragment mLinkListFragment = new LinkListFragment();
        Bundle args = new Bundle();
        args.putString(Constants.CONFIGURATION_KEY_COLOR, _colorItem);
        args.putString(Constants.CONFIGURATION_KEY_JSON, _requestJson);
        args.putString(Constants.CONFIGURATION_KEY_ROUTE, _requestRoute);
        mLinkListFragment.setArguments(args);
        return mLinkListFragment;
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
        getLoaderManager().initLoader(Constants.LOADER_LIST_LINK_ID, bundle, this);
    }

    @Override
    public void onLoadFinished(Loader<List<Events>> _loader, List<Events> _data) {
        if (_data != null) {
            mAdapter = new LinksListAdapter(getActivity(), _data);
            lvLinks.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            showContent();
        } else {
            showError(getResources().getString(R.string.connection_error));
        }
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
        //noinspection ConstantConditions
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lvLinks = findView(R.id.lvLinks_LLF);
        vUnderLine_LLF = findView(R.id.vUnderLine_LLF);
        Image.init(Color.parseColor(color));
        vUnderLine_LLF.setBackgroundColor(Image.darkenColor(0.2));
        lvLinks.setDivider(new ColorDrawable(Image.darkenColor(0.2)));
        lvLinks.setDividerHeight(4);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        lvLinks.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> _parent, View _view, int _position, long _id) {
        mOnListItemActionListener.onEventsClickLinkAction(mAdapter.getItem(_position).link);
    }

    @Override
    public void onViewCreated(final View _view, final Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_links;
    }

    @Override
    public void onRetryClick() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_JSON, json);
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_ROUTE, route);
        getLoaderManager().restartLoader(Constants.LOADER_LIST_LINK_ID, bundle, this);
    }
}
