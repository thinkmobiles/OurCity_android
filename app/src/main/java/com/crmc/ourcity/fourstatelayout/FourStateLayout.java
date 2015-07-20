package com.crmc.ourcity.fourstatelayout;

import android.animation.AnimatorInflater;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.crmc.ourcity.R;

/**
 *
 * Created by Klim on 15.07.2015.
 */
public final class FourStateLayout extends LinearLayout {
    private ViewGroup mContentLayout, mLoadingLayout, mEmptyLayout, mErrorLayout;
    private int mState = States.STATE_LOADING;

    public FourStateLayout(final Context _context) {
        super(_context);
        init();
    }

    public FourStateLayout(final Context _context, final AttributeSet _set) {
        super(_context, _set);
        init();
    }

    public final void init() {
        setBackgroundColor(getResources().getColor(R.color.white));
        LayoutTransition transition = new LayoutTransition();
        ObjectAnimator fadeIn = (ObjectAnimator) AnimatorInflater.loadAnimator(getContext(), R.animator.layout_fade_in);
        ObjectAnimator fadeOut = (ObjectAnimator) AnimatorInflater.loadAnimator(getContext(), R.animator.layout_fade_out);
        transition.setAnimator(LayoutTransition.APPEARING, fadeIn);
        transition.setAnimator(LayoutTransition.DISAPPEARING, fadeOut);
        setLayoutTransition(transition);
    }

    public final void initDefaultState(final int _state) {
        mState = _state;
    }

    public final void initFourStates(final ViewGroup _contentLayout, final ViewGroup _loadingLayout, final ViewGroup _emptyLayout, final ViewGroup _errorLayout) {
        setOrientation(VERTICAL);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mContentLayout = _contentLayout;
        mLoadingLayout = _loadingLayout;
        mEmptyLayout = _emptyLayout;
        mErrorLayout = _errorLayout;

        addView(mContentLayout, params);
        addView(mLoadingLayout, params);
        addView(mEmptyLayout, params);
        addView(mErrorLayout, params);

        updateVisibilityForState();
    }


    private void updateVisibilityForState() {
        if (mContentLayout != null && mEmptyLayout != null && mErrorLayout != null && mLoadingLayout != null) {
            switch (mState) {
                case States.STATE_CONTENT:
                    mContentLayout.setVisibility(View.VISIBLE);
                    mErrorLayout.setVisibility(View.GONE);
                    mLoadingLayout.setVisibility(GONE);
                    mEmptyLayout.setVisibility(GONE);
                    break;
                case States.STATE_LOADING:
                    mContentLayout.setVisibility(GONE);
                    mErrorLayout.setVisibility(View.GONE);
                    mLoadingLayout.setVisibility(VISIBLE);
                    mEmptyLayout.setVisibility(GONE);
                    break;
                case States.STATE_ERROR:
                    mContentLayout.setVisibility(GONE);
                    mErrorLayout.setVisibility(VISIBLE);
                    mLoadingLayout.setVisibility(GONE);
                    mEmptyLayout.setVisibility(GONE);
                    break;
                case States.STATE_EMPTY:
                    mContentLayout.setVisibility(GONE);
                    mErrorLayout.setVisibility(View.GONE);
                    mLoadingLayout.setVisibility(GONE);
                    mEmptyLayout.setVisibility(VISIBLE);
                    break;
            }
        }
    }

    public final void showLoading() {
        mState = States.STATE_LOADING;
        updateVisibilityForState();
    }

    public final void showError() {
        mState = States.STATE_ERROR;
        updateVisibilityForState();
    }

    public final void showContent() {
        mState = States.STATE_CONTENT;
        updateVisibilityForState();
    }

    public final void showEmpty() {
        mState = States.STATE_EMPTY;
        updateVisibilityForState();
    }

    public final int getCurrentState() {
        return mState;
    }

    public final static class States {
        private static final int STATE_LOADING = 0;
        private static final int STATE_CONTENT = 1;
        private static final int STATE_ERROR = 2;
        private static final int STATE_EMPTY = 3;
    }

    public interface OnRetryClickListener {
        void onRetryClick();
    }
}




