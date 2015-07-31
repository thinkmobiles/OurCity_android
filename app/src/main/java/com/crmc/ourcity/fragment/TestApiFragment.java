package com.crmc.ourcity.fragment;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;

import com.crmc.ourcity.R;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.MenuLoader;
import com.crmc.ourcity.rest.responce.menu.MenuFull;

/**
 * Created by SetKrul on 28.07.2015.
 */
public class TestApiFragment extends BaseFourStatesFragment implements LoaderManager.LoaderCallbacks<MenuFull> {
    private int cityNumber;
    private String lng;

    public static TestApiFragment newInstance() {
        return new TestApiFragment();
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
    public void onRetryClick() {
    }

    @Override
    public Loader<MenuFull> onCreateLoader(int id, Bundle args) {
        return new MenuLoader(getActivity(), args);
    }

    @Override
    public void onLoadFinished(Loader<MenuFull> loader, MenuFull data) {
        for (int i = 0; i < data.nodes.size(); i++) {
            Log.d("TAG", "TAG: " + data.nodes.get(i).colorItem);
            for (int j = 0; j < data.nodes.get(i).getMenu().size(); j++){
                Log.d("TAG", "TAG2: " + data.nodes.get(i).menu.get(j).colorItem);
            }
        }
//        Log.d("TAG", "TAG: " + data);
    }

    @Override
    public void onLoaderReset(Loader<MenuFull> loader) {
    }
}
