package com.crmc.ourcity.fourstatelayout;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.fragment.BaseFragment;

/**
 * base class for detail fragment with state: loading, no data, connection error, show content
 * Created by Klim on 15.07.2015.
 */
public abstract class BaseFourStatesFragment extends BaseFragment implements FourStateLayout.OnRetryClickListener {

    private Button mRetryButton;
    private TextView mErrorTitle, mEmptyTitle, mLoadingTitle;
    private FourStateLayout mainView;
    private View rootView;


    protected int getLayoutResource() {
        return R.layout.base_four_state_layout;
    }

    protected void setListeners() {
    }

    protected void initViews() {}

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
        return rootView;
    }

    protected abstract int getContentView();


    @Override
    public void onViewCreated(final View _view, final Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
        mRetryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(final View _v) {
                onRetryClick();
            }
        });

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

    protected void configureActionBar (boolean isBackVisible, boolean isHomeVisible, String title) {
        View actionBar = getActivity().findViewById(R.id.rlActionBar);
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

    protected void configureActionBar (boolean isBackVisible, boolean isHomeVisible) {
        View actionBar = getActivity().findViewById(R.id.rlActionBar);
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
    }
}
