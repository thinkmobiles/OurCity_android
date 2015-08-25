package com.crmc.ourcity.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.crmc.ourcity.R;
import com.crmc.ourcity.adapter.MenuGridAdapter;
import com.crmc.ourcity.callback.OnItemActionListener;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
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
    private MenuGridAdapter mAdapter;
    private ArrayList<MenuModel> mData;
    private OnItemActionListener mCallBackMenuModel;

    public static SubMenuFragment newInstance(List<MenuModel> _submenu) {
        SubMenuFragment subMenuFragment = new SubMenuFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(Constants.CONFIGURATION_KEY_SUBMENU, (ArrayList<? extends Parcelable>) _submenu);
        subMenuFragment.setArguments(args);
        return subMenuFragment;
    }

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);
        try {
            mCallBackMenuModel = (OnItemActionListener) _activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(_activity.toString() + " must implement OnItemActionListener");
        }
    }

    @Override
    public void onDetach() {
        mCallBackMenuModel = null;
        super.onDetach();
    }

    @Override
    protected void initViews() {
        mRecyclerView = findView(R.id.rvMenu_FMM);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity().getApplicationContext(), new
                RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(Context _context, View _view, int _position) {
                MenuModel menuModel = mAdapter.getItem(_position);
                if (menuModel.menu != null) {
                    mCallBackMenuModel.onMenuModelPrepared(menuModel.menu);
                } else {
                    mCallBackMenuModel.onItemAction(menuModel);
                }
            }
        }));
    }

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        mData = getArguments().getParcelableArrayList(Constants.CONFIGURATION_KEY_SUBMENU);
    }

    @Override
    public void onViewCreated(final View _view, final Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
        mLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MenuGridAdapter(mData, getActivity());
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
