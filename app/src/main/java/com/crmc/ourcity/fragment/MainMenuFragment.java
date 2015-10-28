package com.crmc.ourcity.fragment;

import android.app.Activity;
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

import java.util.List;
import java.util.Locale;

import static com.crmc.ourcity.global.Constants.LOADER_IMAGE_CITY_ID;
import static com.crmc.ourcity.global.Constants.LOADER_IMAGE_LOGO_ID;
import static com.crmc.ourcity.global.Constants.LOADER_MENU_BOTTOM_ID;
import static com.crmc.ourcity.global.Constants.LOADER_MENU_ID;

/**
 * Created by SetKrul on 28.07.2015.
 */
public class MainMenuFragment extends BaseFourStatesFragment implements LoaderManager.LoaderCallbacks<Object> {

    View actionBar;
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

    LinearLayout[] llBottomButtons;
    TextView[] tvBottomButtons;
    ImageView[] ivBottomButtons;

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
        // ((AppCompatActivity) getActivity()).getDelegate().getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        actionBar = getActivity().findViewById(R.id.rlActionBar);
//        ImageView mActionBack = (ImageView) actionBar.findViewById(R.id.action_back);
//        ImageView mActionHome = (ImageView) actionBar.findViewById(R.id.action_home);
//        mActionHome.setVisibility(View.GONE);
//        mActionBack.setVisibility(View.GONE);
        configureActionBar(false, false);
        setHasOptionsMenu(true);
        llBtn_MMF = findView(R.id.llBtn_MMF);
        llBtnFirst_MMF = findView(R.id.llBtnFirst_MMF);
        llBtnSecond_MMF = findView(R.id.llBtnSecond_MMF);
        llBtnThird_MMF = findView(R.id.llBtnThird_MMF);
        llBottomButtons = new LinearLayout[]{llBtnFirst_MMF, llBtnSecond_MMF, llBtnThird_MMF};

        rlMenu_MMF = findView(R.id.rlMenu_MMF);

        ivBtnFirst_MMF = findView(R.id.ivBtnFirst_MMF);
        ivBtnSecond_MMF = findView(R.id.ivBtnSecond_MMF);
        ivBtnThird_MMF = findView(R.id.ivBtnThird_MMF);
        ivBottomButtons = new ImageView[]{ivBtnFirst_MMF, ivBtnSecond_MMF, ivBtnThird_MMF};

        ivTown_MA = (ImageView) getActivity().findViewById(R.id.ivTown_MA);

        tvBtnFirst_MMF = findView(R.id.tvBtnFirst_MMF);
        tvBtnSecond_MMF = findView(R.id.tvBtnSecond_MMF);
        tvBtnThird_MMF = findView(R.id.tvBtnThird_MMF);
        tvBottomButtons = new TextView[]{tvBtnFirst_MMF, tvBtnSecond_MMF, tvBtnThird_MMF};

        mRecyclerView = findView(R.id.rvMenu_FMM);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity().getApplicationContext(),
                handleItemClick()));
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
        mLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        cityNumber = getResources().getInteger(R.integer.city_id);
        if (Locale.getDefault().toString().equals("en_US")) {
            lng = "en";
        } else {
            lng = "he";
        }
        residentId = SPManager.getInstance(getActivity()).getResidentId();
        //((AppCompatActivity) getActivity()).getDelegate().getSupportActionBar().setTitle("");
        TextView mTitle = (TextView) actionBar.findViewById(R.id.action_title);
        mTitle.setText("");
        Constants.PREVIOUSTITLE = "";
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.BUNDLE_CONSTANT_CITY_NUMBER, cityNumber);
        bundle.putString(Constants.BUNDLE_CONSTANT_LANG, lng);
        bundle.putInt(Constants.BUNDLE_CONSTANT_RESIDENT_ID, residentId);
        getLoaderManager().initLoader(Constants.LOADER_MENU_ID, bundle, this);
        getLoaderManager().initLoader(Constants.LOADER_MENU_BOTTOM_ID, bundle, this);

        Bundle bundle1 = new Bundle();
        bundle1.putInt(Constants.BUNDLE_CONSTANT_CITY_NUMBER, cityNumber);
        bundle1.putInt(Constants.BUNDLE_CONSTANT_LOAD_IMAGE_TYPE, Constants.BUNDLE_CONSTANT_LOAD_IMAGE_TYPE_LOGO);
        getLoaderManager().initLoader(Constants.LOADER_IMAGE_LOGO_ID, bundle1, this);

        Bundle bundle2 = new Bundle();
        bundle2.putInt(Constants.BUNDLE_CONSTANT_CITY_NUMBER, cityNumber);
        bundle2.putInt(Constants.BUNDLE_CONSTANT_LOAD_IMAGE_TYPE, Constants.BUNDLE_CONSTANT_LOAD_IMAGE_TYPE_CITY);
        getLoaderManager().initLoader(Constants.LOADER_IMAGE_CITY_ID, bundle2, this);
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
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.BUNDLE_CONSTANT_CITY_NUMBER, cityNumber);
        bundle.putString(Constants.BUNDLE_CONSTANT_LANG, lng);
        bundle.putInt(Constants.BUNDLE_CONSTANT_RESIDENT_ID, residentId);
        getLoaderManager().restartLoader(Constants.LOADER_MENU_ID, bundle, this).forceLoad();
        getLoaderManager().restartLoader(Constants.LOADER_MENU_BOTTOM_ID, bundle, this).forceLoad();
        Bundle bundle1 = new Bundle();
        bundle1.putInt(Constants.BUNDLE_CONSTANT_CITY_NUMBER, cityNumber);
        bundle1.putInt(Constants.BUNDLE_CONSTANT_LOAD_IMAGE_TYPE, Constants.BUNDLE_CONSTANT_LOAD_IMAGE_TYPE_LOGO);
        getLoaderManager().restartLoader(Constants.LOADER_IMAGE_LOGO_ID, bundle1, this).forceLoad();
        Bundle bundle2 = new Bundle();
        bundle2.putInt(Constants.BUNDLE_CONSTANT_CITY_NUMBER, cityNumber);
        bundle2.putInt(Constants.BUNDLE_CONSTANT_LOAD_IMAGE_TYPE, Constants.BUNDLE_CONSTANT_LOAD_IMAGE_TYPE_CITY);
        getLoaderManager().restartLoader(Constants.LOADER_IMAGE_CITY_ID, bundle2, this).forceLoad();
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        menu.getItem(0).setVisible(false);
//        super.onCreateOptionsMenu(menu, inflater);
//    }

    @Override
    public Loader<Object> onCreateLoader(int id, Bundle args) {
        Loader loader = null;
        MainActivity activity = (MainActivity) getActivity();
        switch (id) {
            case LOADER_MENU_ID:
                loader = new MenuLoader(activity, args);
                break;
            case LOADER_MENU_BOTTOM_ID:
                loader = new MenuBottomLoader(activity, args);
                break;
            case LOADER_IMAGE_CITY_ID:
                loader = new ImageLoader(activity, args);
                break;
            case LOADER_IMAGE_LOGO_ID:
                loader = new ImageLoader(activity, args);
                break;
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Object> loader, Object _data) {
        MainActivity activity = (MainActivity) getActivity();
        switch (loader.getId()) {

            case LOADER_MENU_ID:
                if (Constants.mMenuFull == null) {
                    Constants.mMenuFull = (MenuFull) _data;
                }
                if (Constants.mMenuFull != null && Constants.mMenuFull.getNodes() != null && Constants.mMenuFull
                        .getSize() > 0) {
                    mAdapter = new MenuGridAdapter(Constants.mMenuFull.getNodes(), activity);
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
                                drawable.setStroke(Image.getDpi(mMenuBottom.get(i).borderWidth, getActivity()), Color
                                        .parseColor(mMenuBottom.get(i).borderColor));
                            }
                            drawable.setCornerRadius(Image.getDpi(5, getActivity()));
                            drawable.setColor(Color.parseColor(mMenuBottom.get(i).colorItem));
                            llBottomButtons[i].setBackground(drawable);
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

                        rlMenu_MMF.setBackground(Constants.cityImage);
                    }
                } else {
                    rlMenu_MMF.setBackground(Constants.cityImage);
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
            Boolean isLogIn = SPManager.getInstance(getActivity()).getIsLoggedStatus();
            if (Boolean.parseBoolean(menuModel.requestLogin)) {
                if (isLogIn) {
                    if (menuModel.menu != null) {
                        mCallBackMenuModel.onMenuModelPrepared(menuModel.menu);
                    } else {
                        mCallBackMenuModel.onItemAction(menuModel);
                    }
                } else {
                    Intent intent = new Intent(getActivity(), DialogActivity.class);
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
            Boolean isLogIn = SPManager.getInstance(getActivity()).getIsLoggedStatus();
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
                Intent intent = new Intent(getActivity(), DialogActivity.class);
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
        mRecyclerView.addOnItemTouchListener(null);
    }
}
