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
import com.crmc.ourcity.adapter.PhoneBookListAdapter;
import com.crmc.ourcity.callback.OnListItemActionListener;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.PhoneBookLoader;
import com.crmc.ourcity.rest.responce.events.PhoneBook;
import com.crmc.ourcity.utils.Image;

import java.util.List;

/**
 * Created by SetKrul on 27.08.2015.
 */
public class PhoneBookFragment extends BaseFourStatesFragment implements LoaderManager.LoaderCallbacks<List<PhoneBook>>,
        AdapterView.OnItemClickListener {

    private ListView lvPhoneBook;
    private View vUnderLine_PBF;
    private String color;
    private String json;
    private String route;

    private PhoneBookListAdapter mAdapter;
    private OnListItemActionListener mOnListItemActionListener;

    public static PhoneBookFragment newInstance(String _colorItem, String _requestJson, String _requestRoute) {
        PhoneBookFragment mPhoneBookFragment = new PhoneBookFragment();
        Bundle args = new Bundle();
        args.putString(Constants.CONFIGURATION_KEY_COLOR, _colorItem);
        args.putString(Constants.CONFIGURATION_KEY_JSON, _requestJson);
        args.putString(Constants.CONFIGURATION_KEY_ROUTE, _requestRoute);
        mPhoneBookFragment.setArguments(args);
        return mPhoneBookFragment;
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
        getLoaderManager().initLoader(Constants.LOADER_EVENTS_ID, bundle, this);
    }

    @Override
    public void onLoadFinished(Loader<List<PhoneBook>> _loader, List<PhoneBook> _data) {
        mAdapter = new PhoneBookListAdapter(getActivity(), _data, mOnListItemActionListener);
        lvPhoneBook.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        showContent();
    }

    @Override
    public Loader<List<PhoneBook>> onCreateLoader(int _id, Bundle _args) {
        return new PhoneBookLoader(getActivity(), _args);
    }

    @Override
    public void onLoaderReset(Loader<List<PhoneBook>> _loader) {
    }

    @Override
    protected void initViews() {
        super.initViews();
        //noinspection ConstantConditions
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lvPhoneBook = findView(R.id.lvPhoneBook_PBF);
        vUnderLine_PBF = findView(R.id.vUnderLine_PBF);
        Image.init(Color.parseColor(color));
        vUnderLine_PBF.setBackgroundColor(Image.darkenColor(0.2));
        lvPhoneBook.setDivider(new ColorDrawable(Image.darkenColor(0.2)));
        lvPhoneBook.setDividerHeight(4);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        lvPhoneBook.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> _parent, View _view, int _position, long _id) {
        mOnListItemActionListener.onPhoneBookItemAction(mAdapter.getItem(_position).phonebookEntries);
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
}
