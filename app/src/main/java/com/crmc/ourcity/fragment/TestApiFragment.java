package com.crmc.ourcity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.crmc.ourcity.R;
import com.crmc.ourcity.adapter.GridAdapter;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.MenuLoader;
import com.crmc.ourcity.rest.responce.menu.MenuFull;
import com.crmc.ourcity.rest.responce.menu.MenuModel;
import com.crmc.ourcity.view.RecyclerItemClickListener;

/**
 * Created by SetKrul on 28.07.2015.
 */
public class TestApiFragment extends BaseFourStatesFragment implements LoaderManager.LoaderCallbacks<MenuFull> {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private GridAdapter mAdapter;
    private MenuFull data;

    private int cityNumber;
    private String lng;

    public static TestApiFragment newInstance() {
        return new TestApiFragment();
    }



    @Override
    protected void initViews() {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity().getApplicationContext(),
                mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(Context _context, View _view, int _position) {
                MenuModel menuModel = mAdapter.getItem(_position);
                if(menuModel.menu != null) {
                    Toast.makeText(_context, "woohoo", Toast.LENGTH_SHORT).show();
                }
            }
        }));
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(mLayoutManager);

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
        return R.layout.fragment_main_menu;
    }

    @Override
    public void onRetryClick() {
    }

    @Override
    public Loader<MenuFull> onCreateLoader(int id, Bundle args) {
        return new MenuLoader(getActivity(), args);
    }

    @Override
    public void onLoadFinished(Loader<MenuFull> _loader, MenuFull _data) {
        data = _data;
        mAdapter = new GridAdapter(data.getNodes());
        mRecyclerView.setAdapter(mAdapter);
        showContent();


    }

    @Override
    public void onLoaderReset(Loader<MenuFull> loader) {
    }


}
