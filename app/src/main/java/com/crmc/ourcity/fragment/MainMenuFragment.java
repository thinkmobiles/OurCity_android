package com.crmc.ourcity.fragment;

import android.os.Bundle;
import android.view.View;

import com.crmc.ourcity.R;

/**
 * Created by SetKrul on 14.07.2015.
 */
public class MainMenuFragment extends BaseFragment {

    public static MainMenuFragment newInstance() {
        return new MainMenuFragment();
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void initViews() {
        super.initViews();
    }

    @Override
    protected void setListeners() {
        super.setListeners();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.main_menu_fragment;
    }
}
