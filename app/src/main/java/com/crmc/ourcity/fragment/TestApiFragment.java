package com.crmc.ourcity.fragment;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;

import com.crmc.ourcity.R;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.MessagesToResidentLoader;
import com.crmc.ourcity.rest.responce.events.MassageToResident;

import java.util.List;

/**
 * Created by SetKrul on 28.07.2015.
 */
public class TestApiFragment extends BaseFourStatesFragment implements LoaderManager.LoaderCallbacks<List<MassageToResident>> {
    private int residentId;

    public static TestApiFragment newInstance() {
        return new TestApiFragment();
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        residentId = 440;
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.BUNDLE_CONSTANT_RESIDENT_ID, residentId);
        getLoaderManager().initLoader(1, bundle, this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_webview;
    }

    @Override
    public void onRetryClick() {
    }

    @Override
    public Loader<List<MassageToResident>> onCreateLoader(int id, Bundle args) {
        return new MessagesToResidentLoader(getActivity(), args);
    }

    @Override
    public void onLoadFinished(Loader<List<MassageToResident>> loader, List<MassageToResident> data) {
        for (int i = 0; i < data.size(); i++) {
            Log.d("TAG", "TAG: " + data.get(i).message);
        }
//        Log.d("TAG", "TAG: " + data);
    }

    @Override
    public void onLoaderReset(Loader<List<MassageToResident>> loader) {
    }
}
