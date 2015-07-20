package com.crmc.ourcity.fourstatelayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.fragment.BaseFragment;

/**
 * base class for detail fragment with state: loading, no data, connection error, show content
 * Created by Klim on 15.07.2015.
 */
public abstract class BaseFourStatesFragment extends BaseFragment implements FourStateLayout.OnRetryClickListener {

    private Button mRetryButton;
    private TextView mErrorTitle, mEmptyTitle;
    private FourStateLayout mainView;


    @Override
    protected int getLayoutResource() {
        return R.layout.base_four_state_layout;
    }

    @Override
    public View onCreateView(final LayoutInflater _inflater, final ViewGroup _root, final Bundle _savedInstanceState) {
        View view = super.onCreateView(_inflater, _root, _savedInstanceState);
        mainView = findView(R.id.four_state);
        ViewGroup mEmptyLayout = (ViewGroup) _inflater.inflate(R.layout.empty_layout, null);
        ViewGroup mErrorLayout = (ViewGroup) _inflater.inflate(R.layout.error_layout, null);
        ViewGroup mLoadingLayout = (ViewGroup) _inflater.inflate(R.layout.loading_layout, null);

        mRetryButton = (Button) mErrorLayout.findViewById(R.id.btn_try_again);
        mEmptyTitle = (TextView) mEmptyLayout.findViewById(R.id.empty_title);
        mErrorTitle = (TextView) mErrorLayout.findViewById(R.id.error_title);

        mainView.initFourStates((ViewGroup)getContentView(_inflater), mLoadingLayout, mEmptyLayout, mErrorLayout);
        return view;

    }

    protected abstract View getContentView(final LayoutInflater _inflater);


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


}
