package com.crmc.ourcity.fragment;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;

import com.crmc.ourcity.R;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.PhonesLoader;
import com.crmc.ourcity.rest.responce.events.Phones;

import java.util.List;

/**
 * Created by SetKrul on 28.07.2015.
 */
public class TestApiFragment extends BaseFourStatesFragment implements LoaderManager.LoaderCallbacks<List<Phones>> {
    private int cityNumber;
    private String lng;

    public static TestApiFragment newInstance() {
        return new TestApiFragment();
    }

    @Override
    public void onViewCreated(final View _view, final Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
        cityNumber = 1;
        lng = "en";
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.BUNDLE_CONSTANT_CITY_NUMBER, cityNumber);
        bundle.putString(Constants.BUNDLE_CONSTANT_LANG, lng);
        getLoaderManager().initLoader(1, bundle, this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_webview;
    }

    @Override
    public void onRetryClick() {}

    @Override
    public Loader<List<Phones>> onCreateLoader(int _id, Bundle _args) {
        return new PhonesLoader(getActivity(), _args);
    }

    @Override
    public void onLoadFinished(Loader<List<Phones>> _loader, List<Phones> _data) {
        for (int i = 0; i < _data.size(); i++) {
            Log.d("TAG", "TAG: " + _data.get(i).phoneNumber);
        }
//        Log.d("TAG", "TAG: " + data);
    }

    @Override
    public void onLoaderReset(Loader<List<Phones>> _loader) {
    }
}
