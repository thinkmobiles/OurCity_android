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
import com.crmc.ourcity.adapter.RSSAdapter;
import com.crmc.ourcity.callback.OnListItemActionListener;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.RSSLoader;
import com.crmc.ourcity.utils.Image;
import com.crmc.ourcity.utils.rss.RssItem;

import java.lang.ref.WeakReference;
import java.util.List;

public class RSSListFragment extends BaseFourStatesFragment implements LoaderManager.LoaderCallbacks<List<RssItem>> {

    private ListView lvRssEntries;
    private View vUnderLine_RssFrg;
    private String color;
    private String rssLink;
    private String title;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RSSAdapter mAdapter;
    private OnListItemActionListener mOnListItemActionListener;
    private WeakReference<MainActivity> mAcivity;

    public static RSSListFragment newInstance(String _colorItem, String _rssLink, String _title) {
        RSSListFragment rssListFragment = new RSSListFragment();
        Bundle args = new Bundle();
        args.putString(Constants.CONFIGURATION_KEY_COLOR, _colorItem);
        args.putString(Constants.BUNDLE_CONSTANT_RSS_LINK, _rssLink);
        args.putString(Constants.NODE_TITLE, _title);
        rssListFragment.setArguments(args);
        return rssListFragment;
    }

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);
        try {
            mAcivity = new WeakReference<>((MainActivity) _activity);
            mOnListItemActionListener = (OnListItemActionListener) _activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(_activity.toString() + " must implement OnListItemActionListener");
        }
    }

    @Override
    public void onDetach() {
        mOnListItemActionListener = null;
        mAcivity.clear();
        super.onDetach();
    }

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        color = getArguments().getString(Constants.CONFIGURATION_KEY_COLOR);
        rssLink = getArguments().getString(Constants.BUNDLE_CONSTANT_RSS_LINK, "");
        title = getArguments().getString(Constants.NODE_TITLE);
    }

    @Override
    public void onResume() {
        configureActionBar(true, true, title);
        super.onResume();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_CONSTANT_RSS_LINK, rssLink);
        getLoaderManager().initLoader(Constants.LOADER_RSS_ID, bundle, this);
    }

    @Override
    public void onLoadFinished(Loader<List<RssItem>> _loader, List<RssItem> _data) {
        swipeRefreshLayout.setRefreshing(false);
        if (_data != null) {
            mAdapter = new RSSAdapter(mAcivity.get(), _data, mOnListItemActionListener);
            lvRssEntries.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            showContent();
        } else {
            showError(getResources().getString(R.string.connection_error));
        }
    }

    @Override
    public Loader<List<RssItem>> onCreateLoader(int _id, Bundle _args) {
        return new RSSLoader(mAcivity.get(), _args);
    }

    @Override
    public void onLoaderReset(Loader<List<RssItem>> _loader) {
    }

    @Override
    protected void initViews() {
        super.initViews();
        lvRssEntries = findView(R.id.lvRSS_RssFrg);
        swipeRefreshLayout = findView(R.id.swipe_refresh_rss);
        vUnderLine_RssFrg = findView(R.id.vUnderLine_RssFrg);
        try {
            Image.init(Color.parseColor(color));
        } catch (Exception e) {
            Image.init(Color.BLACK);
        }
        vUnderLine_RssFrg.setBackgroundColor(Image.darkenColor(0.2));
        lvRssEntries.setDivider(new ColorDrawable(Image.darkenColor(0.2)));
        lvRssEntries.setDividerHeight(4);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        lvRssEntries.setOnItemClickListener(handleRssEntryClick());
        swipeRefreshLayout.setOnRefreshListener(swipeToRefresh());
        swipeInStart();
    }

    @NonNull
    private SwipeRefreshLayout.OnRefreshListener swipeToRefresh() {
        return () -> {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.BUNDLE_CONSTANT_RSS_LINK, rssLink);
            getLoaderManager().restartLoader(Constants.LOADER_RSS_ID, bundle, this);
        };
    }

    public void swipeInStart() {
        TypedValue typed_value = new TypedValue();
        mAcivity.get().getTheme().resolveAttribute(android.R.attr.actionBarSize, typed_value, true);
        swipeRefreshLayout.setProgressViewOffset(false, 0, getResources().getDimensionPixelSize(typed_value
                .resourceId));
        if (!swipeRefreshLayout.isEnabled()) swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setRefreshing(true);
    }

    @NonNull
    private OnItemClickListener handleRssEntryClick() {
        return (parent, view, position, id) -> mOnListItemActionListener.onRSSItemAction(mAdapter.getItem(position));
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
    public void onRetryClick() {}
}
