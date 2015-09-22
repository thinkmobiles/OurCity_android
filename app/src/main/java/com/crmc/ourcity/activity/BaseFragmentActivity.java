package com.crmc.ourcity.activity;

import android.app.FragmentManager;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;

/**
 * Created by SetKrul on 14.07.2015.
 */
public abstract class BaseFragmentActivity extends BaseActivity {

    protected final void addFragment(final @IdRes int _containerId, final Fragment _fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(_containerId, _fragment)
                .commit();
    }

    protected final void addFragmentWithBackStack(final @IdRes int _containerId, final Fragment _fragment) {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .add(_containerId, _fragment)
                .commit();
    }

    public final void replaceFragmentWithBackStack(final @IdRes int _containerId, final Fragment _fragment) {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(_containerId, _fragment)
                .commitAllowingStateLoss( );
    }

    public final void replaceFragmentWithoutBackStack(final @IdRes int _containerId, final Fragment _fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(_containerId, _fragment)
                .commit();
    }

    public final void destroyFragment(final Fragment _fragment){
        getSupportFragmentManager().beginTransaction()
                .remove(_fragment)
                .commit();
    }

    @SuppressWarnings("unchecked")
    public final <T extends Fragment> T getFragmentById(final @IdRes int _containerId){
        return (T) getSupportFragmentManager().findFragmentById(_containerId);
    }

    public final void clearBackStack(){
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public final void popBackStack() {
        getSupportFragmentManager().popBackStack();
    }

}

