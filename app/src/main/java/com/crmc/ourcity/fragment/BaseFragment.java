package com.crmc.ourcity.fragment;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

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

    protected void hideKeyboard(Context _context) {
        InputMethodManager inputManager = (InputMethodManager) _context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        // check if no view has focus:
        View v = ((Activity) _context).getCurrentFocus();
        if (v == null)
            return;
        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}