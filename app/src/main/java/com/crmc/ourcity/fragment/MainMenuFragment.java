package com.crmc.ourcity.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.adapter.MenuGridAdapter;
import com.crmc.ourcity.callback.OnItemActionListener;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.ImageLoader;
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

    RelativeLayout rlMenu_MMF;

    ImageView ivBtnFirst_MMF;
    ImageView ivBtnSecond_MMF;
    ImageView ivBtnThird_MMF;
    ImageView ivTown_MA;

    TextView tvBtnFirst_MMF;
    TextView tvBtnSecond_MMF;
    TextView tvBtnThird_MMF;

    private MenuFull mMenuFull;

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
    private boolean loaderLogoImageFinish = false;
    private boolean loaderCityImageFinish = false;


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
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        //((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        setHasOptionsMenu(true);
        llBtn_MMF = findView(R.id.llBtn_MMF);
        llBtnFirst_MMF = findView(R.id.llBtnFirst_MMF);
        llBtnSecond_MMF = findView(R.id.llBtnSecond_MMF);
        llBtnThird_MMF = findView(R.id.llBtnThird_MMF);

        rlMenu_MMF = findView(R.id.rlMenu_MMF);

        ivBtnFirst_MMF = findView(R.id.ivBtnFirst_MMF);
        ivBtnSecond_MMF = findView(R.id.ivBtnSecond_MMF);
        ivBtnThird_MMF = findView(R.id.ivBtnThird_MMF);

        ivTown_MA = (ImageView) getActivity().findViewById(R.id.ivTown_MA);

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
                    mCallBackMenuModel.onMenuModelPrepared(menuModel.menu);
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
        cityNumber = getResources().getInteger(R.integer.city_id);
        Log.d("TAG", cityNumber + " id from Menu");
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
        getLoaderManager().initLoader(Constants.LOADER_MENU_BOTTOM_ID, bundle, mMenuBottomCallBack);
        Bundle bundle1 = new Bundle();
        bundle1.putInt(Constants.BUNDLE_CONSTANT_CITY_NUMBER, cityNumber);
        bundle1.putInt(Constants.BUNDLE_CONSTANT_LOAD_IMAGE_TYPE, Constants.BUNDLE_CONSTANT_LOAD_IMAGE_TYPE_LOGO);
        getLoaderManager().initLoader(Constants.LOADER_IMAGE_LOGO_ID, bundle1, mLogoImageLoader);
        Bundle bundle2 = new Bundle();
        bundle2.putInt(Constants.BUNDLE_CONSTANT_CITY_NUMBER, cityNumber);
        bundle2.putInt(Constants.BUNDLE_CONSTANT_LOAD_IMAGE_TYPE, Constants.BUNDLE_CONSTANT_LOAD_IMAGE_TYPE_CITY);
        getLoaderManager().initLoader(Constants.LOADER_IMAGE_CITY_ID, bundle2, mImageCityLoader);
    }

    private LoaderManager.LoaderCallbacks<String> mLogoImageLoader = new LoaderManager.LoaderCallbacks<String>() {

        @Override
        public Loader<String> onCreateLoader(int _id, Bundle _args) {
            return new ImageLoader(getActivity(), _args);
        }

        @Override
        public void onLoadFinished(Loader<String> _loader, String _data) {
            if (Constants.logoImage == null) {
                Constants.logoImage = Image.convertBase64ToBitmap(_data);
                ivTown_MA.setImageBitmap(Constants.logoImage);
            } else {
                ivTown_MA.setImageBitmap(Constants.logoImage);
            }
            loaderLogoImageFinish = true;
            showView();
        }

        @Override
        public void onLoaderReset(Loader<String> _loader) {
        }
    };

    private LoaderManager.LoaderCallbacks<String> mImageCityLoader = new LoaderManager.LoaderCallbacks<String>() {

        @Override
        public Loader<String> onCreateLoader(int _id, Bundle _args) {
            return new ImageLoader(getActivity(), _args);
        }

        @Override
        public void onLoadFinished(Loader<String> _loader, String _data) {
            if (Constants.cityImage == null) {
                Constants.cityImage = new BitmapDrawable(getResources(), Image.convertBase64ToBitmap(_data));
                rlMenu_MMF.setBackground(Constants.cityImage);
            } else {
                rlMenu_MMF.setBackground(Constants.cityImage);
            }
            loaderCityImageFinish = true;
            showView();
        }

        @Override
        public void onLoaderReset(Loader<String> _loader) {
        }
    };

    private LoaderManager.LoaderCallbacks<MenuFull> mMenuCallBack = new LoaderManager.LoaderCallbacks<MenuFull>() {

        @Override
        public Loader<MenuFull> onCreateLoader(int _id, Bundle _args) {
            return new MenuLoader(getActivity(), _args);
        }

        @Override
        public void onLoadFinished(Loader<MenuFull> _loader, MenuFull _data) {
            if (_data.getSize() > 0) {
                mMenuFull = _data;
                mAdapter = new MenuGridAdapter(_data.getNodes(), getActivity());
                mRecyclerView.setAdapter(mAdapter);
            }
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
        if (loaderMenuFinish && loaderMenuBottomFinish && loaderCityImageFinish && loaderCityImageFinish) {
            if (mMenuFull != null) {
                showContent();
                loaderMenuFinish = false;
                loaderMenuBottomFinish = false;
                loaderLogoImageFinish = false;
                loaderCityImageFinish = false;
            } else {
                showError("Server do not responds");
            }
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_main_menu;
    }

    @Override
    public void onRetryClick() {
        showLoading();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.BUNDLE_CONSTANT_CITY_NUMBER, cityNumber);
        bundle.putString(Constants.BUNDLE_CONSTANT_LANG, lng);
        bundle.putInt(Constants.BUNDLE_CONSTANT_RESIDENT_ID, residentId);
        getLoaderManager().restartLoader(Constants.LOADER_MENU_ID, bundle, mMenuCallBack);
        getLoaderManager().restartLoader(Constants.LOADER_MENU_BOTTOM_ID, bundle, mMenuBottomCallBack);
        Bundle bundle1 = new Bundle();
        bundle1.putInt(Constants.BUNDLE_CONSTANT_CITY_NUMBER, cityNumber);
        bundle1.putInt(Constants.BUNDLE_CONSTANT_LOAD_IMAGE_TYPE, Constants.BUNDLE_CONSTANT_LOAD_IMAGE_TYPE_LOGO);
        getLoaderManager().restartLoader(Constants.LOADER_IMAGE_LOGO_ID, bundle1, mLogoImageLoader);
        Bundle bundle2 = new Bundle();
        bundle2.putInt(Constants.BUNDLE_CONSTANT_CITY_NUMBER, cityNumber);
        bundle2.putInt(Constants.BUNDLE_CONSTANT_LOAD_IMAGE_TYPE, Constants.BUNDLE_CONSTANT_LOAD_IMAGE_TYPE_CITY);
        getLoaderManager().restartLoader(Constants.LOADER_IMAGE_CITY_ID, bundle2, mImageCityLoader);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.getItem(0).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }
}