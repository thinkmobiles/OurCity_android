package com.crmc.ourcity.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.widget.ListView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.adapter.PhonesListAdapter;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.PhonesLoader;
import com.crmc.ourcity.rest.responce.events.Phones;
import com.crmc.ourcity.utils.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SetKrul on 05.08.2015.
 */
public class PhonesFragment extends BaseFourStatesFragment implements LoaderManager.LoaderCallbacks<List<Phones>>,
        OnRefreshListener {

    private ListView lvPhones;
    private List<Phones> mPhonesList;
    private PhonesListAdapter mAdapter;
    private View vUnderLine_FP;
    private String color;
    private String json;
    private String route;
    private int type;

    public static PhonesFragment newInstance(String _colorItem, String _requestJson, String _requestRoute, int _type) {
        PhonesFragment mPhonesFragment = new PhonesFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.CONFIGURATION_KEY_PHONE_LIST_OR_PHONE_BOOK, _type);
        args.putString(Constants.CONFIGURATION_KEY_COLOR, _colorItem);
        args.putString(Constants.CONFIGURATION_KEY_JSON, _requestJson);
        args.putString(Constants.CONFIGURATION_KEY_ROUTE, _requestRoute);
        mPhonesFragment.setArguments(args);
        return mPhonesFragment;
    }

    public static PhonesFragment newInstance(List<Phones> _phonesList, int _type){
        PhonesFragment mPhonesFragment = new PhonesFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.CONFIGURATION_KEY_PHONE_LIST_OR_PHONE_BOOK, _type);
        args.putParcelableArrayList(Constants.CONFIGURATION_KEY_PHONE_LIST, (ArrayList<? extends Parcelable>)
                _phonesList);
        mPhonesFragment.setArguments(args);
        return mPhonesFragment;
    }

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        type = getArguments().getInt(Constants.CONFIGURATION_KEY_PHONE_LIST_OR_PHONE_BOOK);
        if (type == Constants.PHONE_LIST){
            color = getArguments().getString(Constants.CONFIGURATION_KEY_COLOR);
            json = getArguments().getString(Constants.CONFIGURATION_KEY_JSON);
            route = getArguments().getString(Constants.CONFIGURATION_KEY_ROUTE);
        } else {
            mPhonesList = getArguments().getParcelableArrayList(Constants.CONFIGURATION_KEY_PHONE_LIST);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void initViews() {
        super.initViews();
        lvPhones = findView(R.id.lvPhones_FP);
        vUnderLine_FP = findView(R.id.vUnderLine_FP);
        Image.init(Color.parseColor(color));
        vUnderLine_FP.setBackgroundColor(Image.lighterColor(0.2));
        lvPhones.setDivider(new ColorDrawable(Image.darkenColor(0.2)));
        lvPhones.setDividerHeight(4);

        if (type == Constants.PHONE_BOOK_LIST) {
            mAdapter = new PhonesListAdapter(getActivity(), mPhonesList);
            lvPhones.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            showContent();
        }
    }

    @Override
    protected void setListeners() {
        super.setListeners();
    }

    @Override
    public void onViewCreated(final View _view, final Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
        if (type == Constants.PHONE_LIST) {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_JSON, json);
            bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_ROUTE, route);
            getLoaderManager().initLoader(Constants.LOADER_PHONES_ID, bundle, this);
        }
    }

    @Override
    public void onLoadFinished(Loader<List<Phones>> _loader, List<Phones> _data) {
        mAdapter = new PhonesListAdapter(getActivity(), _data);
        lvPhones.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        showContent();
    }

    @Override
    public Loader<List<Phones>> onCreateLoader(int _id, Bundle _args) {
        return new PhonesLoader(getActivity(), _args);
    }

    @Override
    public void onLoaderReset(Loader<List<Phones>> _loader) {
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_phones;
    }

    @Override
    public void onRetryClick() {
    }

    @Override
    public void onRefresh() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_JSON, json);
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_ROUTE, route);
        getLoaderManager().restartLoader(Constants.LOADER_PHONES_ID, bundle, this);
    }
}
