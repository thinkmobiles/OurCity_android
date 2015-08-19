package com.crmc.ourcity.fragment;


import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;

/**
 * Created by SetKrul on 14.07.2015.
 */
public abstract class BaseFragment extends Fragment {

    protected final void addFragment(final @IdRes int _containerId, final Fragment _fragment) {
        getChildFragmentManager().beginTransaction().add(_containerId, _fragment).commit();
    }

    public final void replaceFragmentWithBackStack(final @IdRes int _containerId, final Fragment _fragment) {
        getChildFragmentManager().beginTransaction().addToBackStack(null).replace(_containerId, _fragment).commit();
    }

    public final void replaceFragmentWithoutBackStack(final @IdRes int _containerId, final Fragment _fragment) {
        getChildFragmentManager().beginTransaction().replace(_containerId, _fragment).commit();
    }


    public final void destroyFragment(final Fragment _fragment) {
        getChildFragmentManager().beginTransaction().remove(_fragment).commit();
    }

    @SuppressWarnings("unchecked")
    public final <T extends Fragment> T getFragmentById(final @IdRes int _containerId) {
        return (T) getChildFragmentManager().findFragmentById(_containerId);
    }

    public final void popBackStack() {
        getFragmentManager().popBackStack();
    }
}