package com.crmc.ourcity.fourstatelayout;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.activity.BaseFragmentActivity;
import com.crmc.ourcity.fragment.BaseFragment;
import com.crmc.ourcity.fragment.MainMenuFragment;
import com.crmc.ourcity.fragment.SubMenuFragment;
import com.crmc.ourcity.utils.SPManager;

import java.lang.ref.WeakReference;

/**
 * base class for detail fragment with state: loading, no data, connection error, show content *
 */
public abstract class BaseFourStatesFragment extends BaseFragment implements FourStateLayout.OnRetryClickListener {

    private Button mRetryButton;
    private TextView mErrorTitle, mEmptyTitle, mLoadingTitle;
    private FourStateLayout mainView;
    protected View rootView;
    protected int amountOfVisibleTickets;
    private WeakReference<BaseFragmentActivity> mActivity;

    protected int getLayoutResource() {
        return R.layout.base_four_state_layout;
    }

    protected void setListeners() {
    }

    protected void initViews() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = new WeakReference<>((BaseFragmentActivity) activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity.clear();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity.get().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        amountOfVisibleTickets = getAmountOfVisibleClosedTickets(SPManager.getInstance(mActivity.get()).getAmountOfVisibleTickets());
    }

    @Override
    public View onCreateView(final LayoutInflater _inflater, final ViewGroup _root, final Bundle _savedInstanceState) {
        super.onCreateView(_inflater, _root, _savedInstanceState);
        rootView = _inflater.inflate(getLayoutResource(), _root, false);
        mainView = findView(R.id.four_state);
        ViewGroup mEmptyLayout = (ViewGroup) _inflater.inflate(R.layout.empty_layout, _root, false);
        ViewGroup mErrorLayout = (ViewGroup) _inflater.inflate(R.layout.error_layout, _root, false);
        ViewGroup mLoadingLayout = (ViewGroup) _inflater.inflate(R.layout.loading_layout, _root, false);

        mRetryButton = (Button) mErrorLayout.findViewById(R.id.btn_try_again);
        mEmptyTitle = (TextView) mEmptyLayout.findViewById(R.id.empty_title);
        mErrorTitle = (TextView) mErrorLayout.findViewById(R.id.error_title);
        mLoadingTitle = (TextView) mLoadingLayout.findViewById(R.id.loadingTitle);

        mainView.initFourStates((ViewGroup) _inflater.inflate(getContentView(), null), mLoadingLayout, mEmptyLayout,
                mErrorLayout);
        initViews();
        setListeners();

        Fragment f = getFragmentManager().findFragmentById(R.id.flContainer_MA);

        if (f instanceof MainMenuFragment || f instanceof SubMenuFragment) {
            mActivity.get().findViewById(R.id.ticker_container_MA).setVisibility(View.VISIBLE);
        } else {
            try {
                mActivity.get().findViewById(R.id.ticker_container_MA).setVisibility(View.GONE);
            } catch (Exception _e) {

            }
        }
        return rootView;
    }

    protected abstract int getContentView();

    @Override
    public void onViewCreated(final View _view, final Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
        mRetryButton.setOnClickListener(_v -> _v.post(this::onRetryClick));

    }

    protected Button getRetryButton() {
        return this.mRetryButton;
    }

    protected void showLoading() {
        mainView.showLoading();
    }

    protected void showLoading(final String _message) {
        mLoadingTitle.setText(_message);
        showLoading();
    }


    protected void showEmpty() {
        mainView.showEmpty();
    }

    protected void showError(final String _message) {
        mErrorTitle.setText(_message);
        mainView.showError();
    }

    protected void hideErrorButton() {
        mRetryButton.setVisibility(View.GONE);
    }

    protected void showEmpty(final String _message) {
        mEmptyTitle.setText(_message);
        showEmpty();
    }

    protected void showContent() {
        mainView.showContent();
    }

    @SuppressWarnings("unchecked")
    protected final <T extends View> T findView(@IdRes int _id) {
        return (T) rootView.findViewById(_id);
    }

    protected void configureActionBar(boolean isBackVisible, boolean isHomeVisible, String title) {
        View actionBar = mActivity.get().findViewById(R.id.rlActionBar);
        ImageView mActionBack = (ImageView) actionBar.findViewById(R.id.action_back);
        ImageView mActionHome = (ImageView) actionBar.findViewById(R.id.action_home);
        TextView mTitle = (TextView) actionBar.findViewById(R.id.action_title);

        if (isBackVisible) {
            mActionBack.setVisibility(View.VISIBLE);
        } else {
            mActionBack.setVisibility(View.GONE);
        }
        if (isHomeVisible) {
            mActionHome.setVisibility(View.VISIBLE);
        } else {
            mActionHome.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(title)) mTitle.setText(title);
    }

    protected void checkData(String _text, TextView _tvView, View _view) {
        if (!TextUtils.isEmpty(_text)) {
            _tvView.setText(_text);
        } else {
            _view.setVisibility(View.GONE);
        }
    }

    protected void configureActionBar(boolean isBackVisible, boolean isHomeVisible) {
        View actionBar = mActivity.get().findViewById(R.id.rlActionBar);
        ImageView mActionBack = (ImageView) actionBar.findViewById(R.id.action_back);
        ImageView mActionHome = (ImageView) actionBar.findViewById(R.id.action_home);

        if (isBackVisible) {
            mActionBack.setVisibility(View.VISIBLE);
        } else {
            mActionBack.setVisibility(View.GONE);
        }
        if (isHomeVisible) {
            mActionHome.setVisibility(View.VISIBLE);
        } else {
            mActionHome.setVisibility(View.GONE);
        }
    }

    protected int getAmountOfVisibleClosedTickets(int rbId) {

        int result = 20; //def value
        switch (rbId) {
            case R.id.rbVisibleTickets20_FDVT:
                result = 20;
                break;
            case R.id.rbVisibleTickets50_FDVT:
                result = 50;
                break;
            case R.id.rbVisibleTickets100_FDVT:
                result = 100;
                break;
        }
        return result;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRetryButton != null) mRetryButton.setOnClickListener(null);
//        RefWatcher refWatcher = Application.getRefWatcher(getActivity());
//        refWatcher.watch(this);
    }
}
