package com.crmc.ourcity.fragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by SetKrul on 14.07.2015.
 */
public abstract class BaseFragment extends Fragment {

    private View rootView;

    protected abstract
    @LayoutRes
    int getLayoutResource();

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle
            savedInstanceState) {
        rootView = inflater.inflate(getLayoutResource(), container, false);
        initViews();
        setListeners();
        return rootView;
    }

    protected void initViews() {
    }

    protected void setListeners() {
    }

    protected final void addFragment(final @IdRes int containerId, final Fragment fragment) {
        getChildFragmentManager().beginTransaction()
                .add(containerId, fragment)
                .commit();
    }

    public final void replaceFragmentWithBackStack(final @IdRes int containerId, final Fragment fragment) {
        getChildFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(containerId, fragment)
                .commit();
    }

    public final void replaceFragmentWithoutBackStack(final @IdRes int containerId, final Fragment fragment) {
        getChildFragmentManager().beginTransaction()
                .replace(containerId, fragment)
                .commit();
    }

    public final <T extends Fragment> T getFragmentById(final @IdRes int containerId) {
        return (T) getChildFragmentManager().findFragmentById(containerId);
    }

    protected final <T extends View> T findView(@IdRes int _id) {
        return (T) rootView.findViewById(_id);
    }

    public boolean onBackPressed(){
        return false;
    }
}