package com.crmc.ourcity.activity;

import android.app.FragmentManager;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;

/**
 * Created by SetKrul on 14.07.2015.
 */
public abstract class BaseFragmentActivity extends BaseActivity {

    protected final void addFragment(final @IdRes int containerId, final Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(containerId, fragment)
                .commit();
    }

    public final void replaceFragmentWithBackStack(final @IdRes int containerId, final Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(containerId, fragment)
                .commit();
    }

    public final void replaceFragmentWithoutBackStack(final @IdRes int containerId, final Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(containerId, fragment)
                .commit();
    }

    public final void dectroyFragment(final Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .remove(fragment)
                .commit();
    }


    public final <T extends Fragment> T getFragmentById(final @IdRes int containerId){
        return (T) getSupportFragmentManager().findFragmentById(containerId);
    }

    public final void clearBackStack(){
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public final void popBackStack() {
        getSupportFragmentManager().popBackStack();
    }

}

