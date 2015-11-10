package com.crmc.ourcity.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crmc.ourcity.BuildConfig;
import com.crmc.ourcity.R;
import com.crmc.ourcity.activity.MainActivity;
import com.crmc.ourcity.adapter.MenuGridAdapter;
import com.crmc.ourcity.callback.OnItemActionListener;
import com.crmc.ourcity.dialog.DialogActivity;
import com.crmc.ourcity.dialog.DialogType;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.ImageLoader;
import com.crmc.ourcity.loader.MenuBottomLoader;
import com.crmc.ourcity.loader.MenuLoader;
import com.crmc.ourcity.rest.responce.menu.MenuFull;
import com.crmc.ourcity.rest.responce.menu.MenuModel;
import com.crmc.ourcity.utils.EnumUtil;
import com.crmc.ourcity.utils.Image;
import com.crmc.ourcity.utils.SPManager;
import com.crmc.ourcity.view.RecyclerItemClickListener;

import java.lang.ref.WeakReference;
import java.util.List;

import static com.crmc.ourcity.global.Constants.LOADER_IMAGE_CITY_ID;
import static com.crmc.ourcity.global.Constants.LOADER_IMAGE_LOGO_ID;
import static com.crmc.ourcity.global.Constants.LOADER_MENU_BOTTOM_ID;
import static com.crmc.ourcity.global.Constants.LOADER_MENU_ID;

public class MainMenuFragment extends BaseFourStatesFragment implements LoaderManager.LoaderCallbacks<Object> {

    private View actionBar;
    private LinearLayout llBtn_MMF;
    private LinearLayout llBtnFirst_MMF;
    private LinearLayout llBtnSecond_MMF;
    private LinearLayout llBtnThird_MMF;
    private RelativeLayout rlMenu_MMF;
    private ImageView ivTown_MA;

    private LinearLayout[] llBottomButtons;
    private TextView[] tvBottomButtons;
    private ImageView[] ivBottomButtons;

    private RecyclerView mRecyclerView;
    private MenuGridAdapter mAdapter;
    private List<MenuModel> mMenuBottom;
    private OnItemActionListener mCallBackMenuModel;
    private WeakReference<MainActivity> mActivity;

    private boolean loaderMenuFinish = false;
    private boolean loaderMenuBottomFinish = false;
    private boolean loaderLogoImageFinish = false;
    private boolean loaderCityImageFinish = false;

    private RecyclerItemClickListener recyclerItemClickListener;


    public static MainMenuFragment newInstance() {
        return new MainMenuFragment();
    }

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);
        try {
            mActivity = new WeakReference<>((MainActivity)_activity);
            mCallBackMenuModel = (OnItemActionListener) _activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(_activity.toString() + " must implement OnItemActionListener");
        }
    }

    @Override
    public void onDetach() {
        mCallBackMenuModel = null;
        llBtnFirst_MMF.setOnClickListener(null);
        llBtnSecond_MMF.setOnClickListener(null);
        llBtnThird_MMF.setOnClickListener(null);
        mRecyclerView.removeOnItemTouchListener(recyclerItemClickListener);
        recyclerItemClickListener = null;
        mActivity.clear();
        super.onDetach();
    }

    @Override
    protected void initViews() {
        actionBar = mActivity.get().findViewById(R.id.rlActionBar);
        configureActionBar(false, false);
        setHasOptionsMenu(true);
        llBtn_MMF = findView(R.id.llBtn_MMF);
        llBtnFirst_MMF = findView(R.id.llBtnFirst_MMF);
        llBtnSecond_MMF = findView(R.id.llBtnSecond_MMF);
        llBtnThird_MMF = findView(R.id.llBtnThird_MMF);
        llBottomButtons = new LinearLayout[]{llBtnFirst_MMF, llBtnSecond_MMF, llBtnThird_MMF};
        rlMenu_MMF = findView(R.id.rlMenu_MMF);
        ImageView ivBtnFirst_MMF = findView(R.id.ivBtnFirst_MMF);
        ImageView ivBtnSecond_MMF = findView(R.id.ivBtnSecond_MMF);
        ImageView ivBtnThird_MMF = findView(R.id.ivBtnThird_MMF);
        ivBottomButtons = new ImageView[]{ivBtnFirst_MMF, ivBtnSecond_MMF, ivBtnThird_MMF};
        ivTown_MA = (ImageView) mActivity.get().findViewById(R.id.ivTown_MA);
        TextView tvBtnFirst_MMF = findView(R.id.tvBtnFirst_MMF);
        TextView tvBtnSecond_MMF = findView(R.id.tvBtnSecond_MMF);
        TextView tvBtnThird_MMF = findView(R.id.tvBtnThird_MMF);
        tvBottomButtons = new TextView[]{tvBtnFirst_MMF, tvBtnSecond_MMF, tvBtnThird_MMF};
        mRecyclerView = findView(R.id.rvMenu_FMM);
        mRecyclerView.setHasFixedSize(true);
        recyclerItemClickListener = new RecyclerItemClickListener(mActivity.get(),
                handleItemClick());
        mRecyclerView.addOnItemTouchListener(recyclerItemClickListener);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        llBtnFirst_MMF.setOnClickListener(handleBtnClick());
        llBtnSecond_MMF.setOnClickListener(handleBtnClick());
        llBtnThird_MMF.setOnClickListener(handleBtnClick());
    }

    @Override
    public void onViewCreated(final View _view, final Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mActivity.get(), 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        TextView mTitle = (TextView) actionBar.findViewById(R.id.action_title);
        mTitle.setText("");
        Constants.PREVIOUSTITLE = "";
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle menuBundle = buildMenuBundle(mActivity.get());
        getLoaderManager().initLoader(Constants.LOADER_MENU_ID, menuBundle, this);
        getLoaderManager().initLoader(Constants.LOADER_MENU_BOTTOM_ID, menuBundle, this);

        Bundle logoImgBundle = buildLogoImgBundle(Constants.BUNDLE_CONSTANT_LOAD_IMAGE_TYPE_LOGO);
        getLoaderManager().initLoader(Constants.LOADER_IMAGE_LOGO_ID, logoImgBundle, this);

        Bundle cityImgBundle = buildLogoImgBundle(Constants.BUNDLE_CONSTANT_LOAD_IMAGE_TYPE_CITY);
        getLoaderManager().initLoader(Constants.LOADER_IMAGE_CITY_ID, cityImgBundle, this);
    }

    private void showView() {
        if (loaderMenuFinish && loaderMenuBottomFinish && loaderCityImageFinish && loaderLogoImageFinish) {
            if (Constants.mMenuFull != null) {
                showContent();
                loaderMenuFinish = false;
                loaderMenuBottomFinish = false;
                loaderLogoImageFinish = false;
                loaderCityImageFinish = false;
            } else {
                showError(getResources().getString(R.string.connection_error));
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

        Bundle menuBundle = buildMenuBundle(mActivity.get());
        getLoaderManager().restartLoader(Constants.LOADER_MENU_ID, menuBundle, this).forceLoad();
        getLoaderManager().restartLoader(Constants.LOADER_MENU_BOTTOM_ID, menuBundle, this).forceLoad();

        Bundle logoImgBundle = buildLogoImgBundle(Constants.BUNDLE_CONSTANT_LOAD_IMAGE_TYPE_LOGO);
        getLoaderManager().restartLoader(Constants.LOADER_IMAGE_LOGO_ID, logoImgBundle, this).forceLoad();

        Bundle cityImgBundle = buildLogoImgBundle(Constants.BUNDLE_CONSTANT_LOAD_IMAGE_TYPE_CITY);
        getLoaderManager().restartLoader(Constants.LOADER_IMAGE_CITY_ID, cityImgBundle, this).forceLoad();
    }

    @Override
    public Loader<Object> onCreateLoader(int id, Bundle args) {
        Loader loader = null;
        switch (id) {
            case LOADER_MENU_ID:
                loader = new MenuLoader(mActivity.get(), args);
                break;
            case LOADER_MENU_BOTTOM_ID:
                loader = new MenuBottomLoader(mActivity.get(), args);
                break;
            case LOADER_IMAGE_CITY_ID:
                loader = new ImageLoader(mActivity.get(), args);
                break;
            case LOADER_IMAGE_LOGO_ID:
                loader = new ImageLoader(mActivity.get(), args);
                break;
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Object> loader, Object _data) {

        switch (loader.getId()) {

            case LOADER_MENU_ID:
                if (Constants.mMenuFull == null) {
                    Constants.mMenuFull = (MenuFull) _data;
                }
                if (Constants.mMenuFull != null && Constants.mMenuFull.getNodes() != null && Constants.mMenuFull
                        .getSize() > 0) {
                    mAdapter = new MenuGridAdapter(Constants.mMenuFull.getNodes(), mActivity.get());
                    mRecyclerView.setAdapter(mAdapter);
                }
                loaderMenuFinish = true;
                showView();
                break;

            case LOADER_MENU_BOTTOM_ID:
                if (Constants.mMenuFullBottom == null) {
                    Constants.mMenuFullBottom = (MenuFull) _data;
                }
                if (Constants.mMenuFullBottom != null && Constants.mMenuFullBottom.getNodes() != null && Constants
                        .mMenuFullBottom.getSize() > 0) {
                    mMenuBottom = Constants.mMenuFullBottom.getNodes();
                    for (int i = 0; i < Constants.mMenuFullBottom.getSize(); i++) {
                        llBottomButtons[i].setVisibility(View.VISIBLE);
                        tvBottomButtons[i].setText(mMenuBottom.get(i).title);
                        ivBottomButtons[i].setImageBitmap(Image.convertBase64ToBitmap(mMenuBottom.get(i).iconItem));
                        if (!TextUtils.isEmpty(mMenuBottom.get(i).colorItem)) {
                            GradientDrawable drawable = new GradientDrawable();
                            drawable.setShape(GradientDrawable.RECTANGLE);
                            if (!TextUtils.isEmpty(mMenuBottom.get(i).borderColor) && mMenuBottom.get(i).borderWidth
                                    != null) {
                                drawable.setStroke(Image.getDpi(mMenuBottom.get(i).borderWidth, mActivity.get()), Color
                                        .parseColor(mMenuBottom.get(i).borderColor));
                            }
                            drawable.setCornerRadius(Image.getDpi(5, mActivity.get()));
                            drawable.setColor(Color.parseColor(mMenuBottom.get(i).colorItem));
                            llBottomButtons[i].setBackgroundDrawable(drawable);
                        }
                    }
                    llBtn_MMF.setVisibility(View.VISIBLE);
                } else {
                    llBtn_MMF.setVisibility(View.GONE);
                }
                loaderMenuBottomFinish = true;
                showView();
                break;

            case LOADER_IMAGE_CITY_ID:
                if (Constants.cityImage == null) {
                    if (_data != null) {
                        Constants.cityImage = new BitmapDrawable(getResources(), Image.convertBase64ToBitmap((String)
                                _data));

                        rlMenu_MMF.setBackgroundDrawable(Constants.cityImage);
                    }
                } else {
                    rlMenu_MMF.setBackgroundDrawable(Constants.cityImage);
                }
                loaderCityImageFinish = true;
                showView();
                break;

            case LOADER_IMAGE_LOGO_ID:
                if (Constants.logoImage == null) {
                    if (_data != null) {
                        Constants.logoImage = Image.convertBase64ToBitmap((String) _data);
                        ivTown_MA.setImageBitmap(Constants.logoImage);
                    }
                } else {
                    ivTown_MA.setImageBitmap(Constants.logoImage);
                }
                loaderLogoImageFinish = true;
                showView();
                break;

            default:
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Object> loader) {

    }

    @NonNull
    private RecyclerItemClickListener.OnItemClickListener handleItemClick() {
        return (_context, _view, _position) -> {
            MenuModel menuModel = mAdapter.getItem(_position);
            Constants.PREVIOUSTITLE = menuModel.title;
            Boolean isLogIn = SPManager.getInstance(mActivity.get()).getIsLoggedStatus();
            if (Boolean.parseBoolean(menuModel.requestLogin)) {
                if (isLogIn) {
                    if (menuModel.menu != null) {
                        mCallBackMenuModel.onMenuModelPrepared(menuModel.menu);
                    } else {
                        mCallBackMenuModel.onItemAction(menuModel);
                    }
                } else {
                    Intent intent = new Intent(mActivity.get(), DialogActivity.class);
                    EnumUtil.serialize(DialogType.class, DialogType.LOGIN).to(intent);
                    startActivity(intent);
                }
            } else {
                if (menuModel.menu != null) {
                    mCallBackMenuModel.onMenuModelPrepared(menuModel.menu);
                } else {
                    mCallBackMenuModel.onItemAction(menuModel);
                }
            }
        };
    }

    @NonNull
    private View.OnClickListener handleBtnClick() {
        return v -> {
            Boolean isLogIn = SPManager.getInstance(mActivity.get()).getIsLoggedStatus();
            switch (v.getId()) {
                case R.id.llBtnFirst_MMF:
                    checkRootLogin(mMenuBottom, 0, isLogIn);
                    break;

                case R.id.llBtnSecond_MMF:
                    checkRootLogin(mMenuBottom, 1, isLogIn);
                    break;

                case R.id.llBtnThird_MMF:
                    checkRootLogin(mMenuBottom, 2, isLogIn);
                    break;
            }
        };
    }

    private void checkRootLogin(List<MenuModel> mMenuBottom, int position, boolean isLogin) {
        if (Boolean.parseBoolean(mMenuBottom.get(position).requestLogin)) {
            if (isLogin) {
                mCallBackMenuModel.onItemAction(mMenuBottom.get(position));
            } else {
                Intent intent = new Intent(mActivity.get(), DialogActivity.class);
                EnumUtil.serialize(DialogType.class, DialogType.LOGIN).to(intent);
                startActivity(intent);
            }
        } else {
            mCallBackMenuModel.onItemAction(mMenuBottom.get(position));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //mRecyclerView.addOnItemTouchListener(null);
//
//        RefWatcher refWatcher = Application.getRefWatcher(getActivity());
//        refWatcher.watch(this);
    }

    private Bundle buildMenuBundle(Context _ctx) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.BUNDLE_CONSTANT_CITY_NUMBER, BuildConfig.CITY_ID);
        bundle.putString(Constants.BUNDLE_CONSTANT_LANG, setLang(_ctx));
        bundle.putInt(Constants.BUNDLE_CONSTANT_RESIDENT_ID, getResidentID(_ctx));
        return bundle;
    }

    private Bundle buildLogoImgBundle(int _imgType) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.BUNDLE_CONSTANT_CITY_NUMBER, BuildConfig.CITY_ID);
        bundle.putInt(Constants.BUNDLE_CONSTANT_LOAD_IMAGE_TYPE, _imgType);
        return bundle;
    }

    private int getResidentID(Context _ctx) {
        return SPManager.getInstance(_ctx).getResidentId();
    }

    private String setLang(Context _ctx) {
        String lng;
        if (SPManager.getInstance(_ctx).getApplicationLanguage().equals("iw")) {
            lng = "he";
        } else {
            lng = "en";
        }
        return lng;
    }
}
