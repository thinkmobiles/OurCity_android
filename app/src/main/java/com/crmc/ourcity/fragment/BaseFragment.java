package com.crmc.ourcity.fragment;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

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

    public final void popBackStackImmediate() {
        getFragmentManager().popBackStackImmediate();
    }

    protected void hideKeyboard(Context _ctx) {
        InputMethodManager inputManager = (InputMethodManager) _ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        // check if no view has focus:
        View v = ((Activity) _ctx).getCurrentFocus();
        if (v == null) return;
        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    protected void hideKeyboard(Context _ctx,  View _v) {
        _v.requestFocus();
        InputMethodManager inputManager = (InputMethodManager) _ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(_v.getWindowToken(), 0);
    }

    protected void showKeyboard(Context _context, View _view) {
        InputMethodManager inputMethodManager = (InputMethodManager) _context.getSystemService(Context
                .INPUT_METHOD_SERVICE);
      //  if (!inputMethodManager.isAcceptingText()) {
            inputMethodManager.showSoftInput(_view, 0);
        }
//    }
//


//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        RefWatcher refWatcher = Application.getRefWatcher(getActivity());
//        refWatcher.watch(this);
//    }
}