package com.crmc.ourcity.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crmc.ourcity.R;

/**
 * Created by podo on 21.07.15.
 */
public class TestDialog extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater _inflater, ViewGroup _container, Bundle _savedInstanceState) {
        return _inflater.inflate(R.layout.fragment_dialog_registration, _container, false);
    }
}
