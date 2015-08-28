package com.crmc.ourcity.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.activity.MainActivity;
import com.crmc.ourcity.adapter.MenuGridAdapter;
import com.crmc.ourcity.callback.OnItemActionListener;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.MenuBottomLoader;
import com.crmc.ourcity.loader.MenuLoader;
import com.crmc.ourcity.rest.responce.menu.MenuFull;
import com.crmc.ourcity.rest.responce.menu.MenuModel;
import com.crmc.ourcity.utils.Image;
import com.crmc.ourcity.utils.SPManager;
import com.crmc.ourcity.view.RecyclerItemClickListener;

import java.util.List;

/**
 * Created by SetKrul on 28.07.2015.
 */
public class MainMenuFragment extends BaseFourStatesFragment implements View.OnClickListener {

    LinearLayout llBtn_MMF;
    LinearLayout llBtnFirst_MMF;
    LinearLayout llBtnSecond_MMF;
    LinearLayout llBtnThird_MMF;

    ImageView ivBtnFirst_MMF;
    ImageView ivBtnSecond_MMF;
    ImageView ivBtnThird_MMF;

    TextView tvBtnFirst_MMF;
    TextView tvBtnSecond_MMF;
    TextView tvBtnThird_MMF;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MenuGridAdapter mAdapter;
    private List<MenuModel> mMenuBottom;
    private OnItemActionListener mCallBackMenuModel;

    private int cityNumber;
    private String lng;
    private int residentId;
    private boolean loaderMenuFinish = false;
    private boolean loaderMenuBottomFinish = false;


    public static MainMenuFragment newInstance() {
        return new MainMenuFragment();
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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        //findView(R.id.menu_home).setVisibility(View.GONE);
        setHasOptionsMenu(true);
        llBtn_MMF = findView(R.id.llBtn_MMF);
        llBtnFirst_MMF = findView(R.id.llBtnFirst_MMF);
        llBtnSecond_MMF = findView(R.id.llBtnSecond_MMF);
        llBtnThird_MMF = findView(R.id.llBtnThird_MMF);

        ivBtnFirst_MMF = findView(R.id.ivBtnFirst_MMF);
        ivBtnSecond_MMF = findView(R.id.ivBtnSecond_MMF);
        ivBtnThird_MMF = findView(R.id.ivBtnThird_MMF);

        tvBtnFirst_MMF = findView(R.id.tvBtnFirst_MMF);
        tvBtnSecond_MMF = findView(R.id.tvBtnSecond_MMF);
        tvBtnThird_MMF = findView(R.id.tvBtnThird_MMF);

        mRecyclerView = findView(R.id.rvMenu_FMM);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity().getApplicationContext(), new
                RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(Context _context, View _view, int _position) {
                MenuModel menuModel = mAdapter.getItem(_position);
                if (menuModel.menu != null) {
                    try {
                        mCallBackMenuModel.onMenuModelPrepared(menuModel.menu);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                } else {
                    mCallBackMenuModel.onItemAction(menuModel);
                }
            }
        }));
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        llBtnFirst_MMF.setOnClickListener(this);
        llBtnSecond_MMF.setOnClickListener(this);
        llBtnThird_MMF.setOnClickListener(this);
    }

    @Override
    public void onViewCreated(final View _view, final Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
        mLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        cityNumber = 1;
        lng = "en";
        residentId = SPManager.getInstance(getActivity()).getResidentId();
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.BUNDLE_CONSTANT_CITY_NUMBER, cityNumber);
        bundle.putString(Constants.BUNDLE_CONSTANT_LANG, lng);
        bundle.putInt(Constants.BUNDLE_CONSTANT_RESIDENT_ID, residentId);
        getLoaderManager().initLoader(Constants.LOADER_MENU_ID, bundle, mMenuCallBack);
        getLoaderManager().initLoader(Constants.LOADER_MENU_BUTTOM_ID, bundle, mMenuBottomCallBack);
    }

    private LoaderManager.LoaderCallbacks<MenuFull> mMenuCallBack = new LoaderManager.LoaderCallbacks<MenuFull>() {

        @Override
        public Loader<MenuFull> onCreateLoader(int _id, Bundle _args) {
            return new MenuLoader(getActivity(), _args);
        }

        @Override
        public void onLoadFinished(Loader<MenuFull> _loader, MenuFull _data) {
            mAdapter = new MenuGridAdapter(_data.getNodes(), getActivity());
            mRecyclerView.setAdapter(mAdapter);
            loaderMenuFinish = true;
            showView();
        }

        @Override
        public void onLoaderReset(Loader<MenuFull> _loader) {
        }
    };

    private LoaderManager.LoaderCallbacks<MenuFull> mMenuBottomCallBack = new LoaderManager.LoaderCallbacks<MenuFull>
            () {

        @Override
        public Loader<MenuFull> onCreateLoader(int _id, Bundle _args) {
            return new MenuBottomLoader(getActivity(), _args);
        }

        @Override
        public void onLoadFinished(Loader<MenuFull> _loader, MenuFull _data) {
            if (_data.getSize() > 0) {
                mMenuBottom = _data.getNodes();
                switch (_data.getSize()) {
                    case 1:
                        llBtnSecond_MMF.setVisibility(View.GONE);
                        llBtnThird_MMF.setVisibility(View.GONE);
                        tvBtnFirst_MMF.setText(mMenuBottom.get(0).title);
                        ivBtnFirst_MMF.setImageBitmap(Image.convertBase64ToBitmap(mMenuBottom.get(0).iconItem));
                        Image.setBackgroundColorView(getActivity(), llBtnFirst_MMF, R.drawable.boarder_round_red_vf,
                                Color.parseColor(mMenuBottom.get(0).colorItem));
                        break;

                    case 2:
                        llBtnThird_MMF.setVisibility(View.GONE);
                        tvBtnFirst_MMF.setText(mMenuBottom.get(0).title);
                        ivBtnFirst_MMF.setImageBitmap(Image.convertBase64ToBitmap(mMenuBottom.get(0).iconItem));
                        Image.setBackgroundColorView(getActivity(), llBtnFirst_MMF, R.drawable.boarder_round_red_vf,
                                Color.parseColor(mMenuBottom.get(0).colorItem));

                        tvBtnSecond_MMF.setText(mMenuBottom.get(1).title);
                        ivBtnSecond_MMF.setImageBitmap(Image.convertBase64ToBitmap(mMenuBottom.get(1).iconItem));
                        Image.setBackgroundColorView(getActivity(), llBtnSecond_MMF, R.drawable.boarder_round_red_vf,
                                Color.parseColor(mMenuBottom.get(1).colorItem));
                        break;

                    case 3:
                        tvBtnFirst_MMF.setText(mMenuBottom.get(0).title);
                        ivBtnFirst_MMF.setImageBitmap(Image.convertBase64ToBitmap(mMenuBottom.get(0).iconItem));
                        Image.setBackgroundColorView(getActivity(), llBtnFirst_MMF, R.drawable.boarder_round_red_vf,
                                Color.parseColor(mMenuBottom.get(0).colorItem));

                        tvBtnSecond_MMF.setText(mMenuBottom.get(1).title);
                        ivBtnSecond_MMF.setImageBitmap(Image.convertBase64ToBitmap(mMenuBottom.get(1).iconItem));
                        Image.setBackgroundColorView(getActivity(), llBtnSecond_MMF, R.drawable.boarder_round_red_vf,
                                Color.parseColor(mMenuBottom.get(1).colorItem));

                        tvBtnThird_MMF.setText(mMenuBottom.get(2).title);
                        ivBtnThird_MMF.setImageBitmap(Image.convertBase64ToBitmap(mMenuBottom.get(2).iconItem));
                        Image.setBackgroundColorView(getActivity(), llBtnThird_MMF, R.drawable.boarder_round_red_vf,
                                Color.parseColor(mMenuBottom.get(2).colorItem));
                        break;
                }
            } else {
                llBtn_MMF.setVisibility(View.GONE);
            }
            loaderMenuBottomFinish = true;
            showView();
        }

        @Override
        public void onLoaderReset(Loader<MenuFull> _loader) {
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llBtnFirst_MMF:
                mCallBackMenuModel.onItemAction(mMenuBottom.get(0));
                break;

            case R.id.llBtnSecond_MMF:
                mCallBackMenuModel.onItemAction(mMenuBottom.get(1));
                break;

            case R.id.llBtnThird_MMF:
                mCallBackMenuModel.onItemAction(mMenuBottom.get(2));
                break;
        }
    }

    private void showView() {
        if (loaderMenuFinish && loaderMenuBottomFinish) {
            showContent();
            loaderMenuFinish = false;
            loaderMenuBottomFinish = false;
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_main_menu;
    }

    @Override
    public void onRetryClick() {
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.getItem(0).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }
}