package com.crmc.ourcity.fragment;


import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;

/**
 * Created by SetKrul on 14.07.2015.
 */
public abstract class BaseFragment extends Fragment {

    protected final void addFragment(final @IdRes int containerId, final Fragment fragment) {
        getChildFragmentManager().beginTransaction().add(containerId, fragment).commit();
    }

    public final void replaceFragmentWithBackStack(final @IdRes int containerId, final Fragment fragment) {
        getChildFragmentManager().beginTransaction().addToBackStack(null).replace(containerId, fragment).commit();
    }

    public final void replaceFragmentWithoutBackStack(final @IdRes int containerId, final Fragment fragment) {
        getChildFragmentManager().beginTransaction().replace(containerId, fragment).commit();
    }


    public final void destroyFragment(final Fragment fragment) {
        getChildFragmentManager().beginTransaction().remove(fragment).commit();
    }

    @SuppressWarnings("unchecked")
    public final <T extends Fragment> T getFragmentById(final @IdRes int containerId) {
        return (T) getChildFragmentManager().findFragmentById(containerId);
    }
}