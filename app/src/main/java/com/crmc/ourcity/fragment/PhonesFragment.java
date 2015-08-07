package com.crmc.ourcity.fragment;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.adapter.PhonesListAdapter;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.PhonesLoader;
import com.crmc.ourcity.rest.responce.events.Phones;

import java.util.List;

/**
 * Created by SetKrul on 05.08.2015.
 */
public class PhonesFragment extends BaseFourStatesFragment implements LoaderManager.LoaderCallbacks<List<Phones>> {

    private int cityNumber;

    private ListView lvPhones;
    private PhonesListAdapter mAdapter;

    public static PhonesFragment newInstance() {
        //noinspection deprecation
        return new PhonesFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.BUNDLE_CONSTANT_CITY_NUMBER, cityNumber);
        getLoaderManager().initLoader(1, bundle, this);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cityNumber = 1;
    }

    @Override
    public void onLoadFinished(Loader<List<Phones>> loader, List<Phones> data) {
        for (int i = 0; i < data.size(); i++){
            Log.d(Constants.TAG, "TAG: " + data.get(i).phoneNumber);
        }
        lvPhones = findView(R.id.lvPhones_FP);
        mAdapter = new PhonesListAdapter(getActivity(), data);
        lvPhones.setAdapter(mAdapter);
        showContent();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_phones;
    }

    @Override
    public void onRetryClick() {

    }

    @Override
    public Loader<List<Phones>> onCreateLoader(int id, Bundle args) {
        return new PhonesLoader(getActivity(), args);
    }

    @Override
    public void onLoaderReset(Loader<List<Phones>> loader) {
    }
}
