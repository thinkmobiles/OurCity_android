package com.crmc.ourcity.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.activity.MainActivity;
import com.crmc.ourcity.adapter.PhoneBookListAdapter;
import com.crmc.ourcity.callback.OnListItemActionListener;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.PhoneBookLoader;
import com.crmc.ourcity.rest.responce.events.PhoneBook;
import com.crmc.ourcity.utils.Image;

import java.lang.ref.WeakReference;
import java.util.List;

public class PhoneBookFragment extends BaseFourStatesFragment implements LoaderManager.LoaderCallbacks<List<PhoneBook>>,
        AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private ListView lvPhoneBook;
    private View vUnderLine_PBF;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String color;
    private String json;
    private String route;
    private String title;

    private PhoneBookListAdapter mAdapter;
    private OnListItemActionListener mOnListItemActionListener;
    private WeakReference<MainActivity> mActivity;

    public static PhoneBookFragment newInstance(String _colorItem, String _requestJson, String _requestRoute, String _title) {
        PhoneBookFragment mPhoneBookFragment = new PhoneBookFragment();
        Bundle args = new Bundle();
        args.putString(Constants.CONFIGURATION_KEY_COLOR, _colorItem);
        args.putString(Constants.CONFIGURATION_KEY_JSON, _requestJson);
        args.putString(Constants.CONFIGURATION_KEY_ROUTE, _requestRoute);
        args.putString(Constants.NODE_TITLE, _title);
        mPhoneBookFragment.setArguments(args);
        return mPhoneBookFragment;
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
    public void onDetach() {
        mOnListItemActionListener = null;
        mActivity.clear();
        super.onDetach();
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
    public void onResume() {
        configureActionBar(true, true, title);
        super.onResume();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_JSON, json);
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_ROUTE, route);
        getLoaderManager().initLoader(Constants.LOADER_EVENTS_ID, bundle, this);
    }

    @Override
    public void onLoadFinished(Loader<List<PhoneBook>> _loader, List<PhoneBook> _data) {
        swipeRefreshLayout.setRefreshing(false);
        mAdapter = new PhoneBookListAdapter(mActivity.get(), _data, mOnListItemActionListener);
        lvPhoneBook.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        showContent();
    }

    @Override
    public Loader<List<PhoneBook>> onCreateLoader(int _id, Bundle _args) {
        return new PhoneBookLoader(mActivity.get(), _args);
    }

    @Override
    public void onLoaderReset(Loader<List<PhoneBook>> _loader) {
    }

    @Override
    protected void initViews() {
        super.initViews();
        lvPhoneBook = findView(R.id.lvPhoneBook_PBF);
        vUnderLine_PBF = findView(R.id.vUnderLine_PBF);
        swipeRefreshLayout = findView(R.id.swipe_refresh_phone_book);
        try {
            Image.init(Color.parseColor(color));
        } catch (Exception e) {
            Image.init(Color.BLACK);
        }
        vUnderLine_PBF.setBackgroundColor(Image.darkenColor(0.2));
        lvPhoneBook.setDivider(new ColorDrawable(Image.darkenColor(0.2)));
        lvPhoneBook.setDividerHeight(4);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        lvPhoneBook.setOnItemClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeInStart();
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
    public void onItemClick(AdapterView<?> _parent, View _view, int _position, long _id) {
        mOnListItemActionListener.onPhoneBookItemAction(mAdapter.getItem(_position).phonebookEntries, mAdapter.getItem(_position).categoryName);
    }

    @Override
    public void onViewCreated(final View _view, final Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_phone_book;
    }

    @Override
    public void onRetryClick() {
    }

    @Override
    public void onRefresh() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_JSON, json);
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_ROUTE, route);
        getLoaderManager().restartLoader(Constants.LOADER_EVENTS_ID, bundle, this);
    }
}
