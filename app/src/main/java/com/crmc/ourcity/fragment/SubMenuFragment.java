package com.crmc.ourcity.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SetKrul on 28.07.2015.
 */
public class SubMenuFragment extends BaseFourStatesFragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private GridAdapter mAdapter;
    private ArrayList<MenuModel> data;
    private ListItemAction mCallbackMenuModel;


    private int cityNumber;
    private String lng;

    @SuppressLint("ValidFragment")
    private SubMenuFragment() {}

    public static SubMenuFragment newInstance(List<MenuModel> _submenu) {
        SubMenuFragment subMenuFragment = new SubMenuFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("SUBMENU", (ArrayList<? extends Parcelable>) _submenu);
        subMenuFragment.setArguments(args);

        return subMenuFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
            mCallbackMenuModel = (ListItemAction) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ListItemAction");
        }
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
                    mCallbackMenuModel.onMenuModelPrepared(menuModel.menu);
                }
            }
        }));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = getArguments().getParcelableArrayList("SUBMENU");
        Log.i("TAG", data.toString());
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GridAdapter(data);
        mRecyclerView.setAdapter(mAdapter);
        showContent();

    }


    @Override
    protected int getContentView() {
        return R.layout.fragment_main_menu;
    }


    @Override
    public void onRetryClick() {

    }
}
