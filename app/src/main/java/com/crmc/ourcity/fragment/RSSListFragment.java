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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.adapter.RSSAdapter;
import com.crmc.ourcity.callback.OnListItemActionListener;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.RSSLoader;
import com.crmc.ourcity.model.rss.RSSEntry;
import com.crmc.ourcity.utils.Image;

import java.util.List;

public class RSSListFragment extends BaseFourStatesFragment implements LoaderManager.LoaderCallbacks<List<RSSEntry>>,
        OnItemClickListener {

    private ListView lvRssEntries;
    private View vUnderLine_RssFrg;
    private String color;
    private String rssLink;

    private RSSAdapter mAdapter;
    private OnListItemActionListener mOnListItemActionListener;

    public static RSSListFragment newInstance(String _colorItem, String _rssLink) {
        RSSListFragment rssListFragment = new RSSListFragment();
        Bundle args = new Bundle();
        args.putString(Constants.CONFIGURATION_KEY_COLOR, _colorItem);
        args.putString(Constants.BUNDLE_CONSTANT_RSS_LINK, _rssLink);
        rssListFragment.setArguments(args);
        return rssListFragment;
    }

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        color = getArguments().getString(Constants.CONFIGURATION_KEY_COLOR);
        rssLink = getArguments().getString(Constants.BUNDLE_CONSTANT_RSS_LINK, "");
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
        bundle.putString(Constants.BUNDLE_CONSTANT_RSS_LINK, rssLink);

        getLoaderManager().initLoader(Constants.LOADER_RSS_ID, bundle, this);
    }

    @Override
    public void onLoadFinished(Loader<List<RSSEntry>> _loader, List<RSSEntry> _data) {
        if (_data != null) {
            mAdapter = new RSSAdapter(getActivity(), _data, mOnListItemActionListener);
            lvRssEntries.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            showContent();
        } else {
            showError(getResources().getString(R.string.connection_error));
        }
    }

    @Override
    public Loader<List<RSSEntry>> onCreateLoader(int _id, Bundle _args) {
        return new RSSLoader(getActivity(), _args);
    }

    @Override
    public void onLoaderReset(Loader<List<RSSEntry>> _loader) {
    }

    @Override
    protected void initViews() {
        super.initViews();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lvRssEntries = findView(R.id.lvRSS_RssFrg);
        vUnderLine_RssFrg = findView(R.id.vUnderLine_RssFrg);
        try {
            Image.init(Color.parseColor(color));
        } catch (Exception e){
            Image.init(Color.BLACK);
        }
        vUnderLine_RssFrg.setBackgroundColor(Image.darkenColor(0.2));
        lvRssEntries.setDivider(new ColorDrawable(Image.darkenColor(0.2)));
        lvRssEntries.setDividerHeight(4);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        lvRssEntries.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> _parent, View _view, int _position, long _id) {
        mOnListItemActionListener.onRSSItemAction(mAdapter.getItem(_position));
    }

    @Override
    public void onViewCreated(final View _view, final Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_rss;
    }

    @Override
    public void onRetryClick() {
    }
}
