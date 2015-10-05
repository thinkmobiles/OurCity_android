package com.crmc.ourcity.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.crmc.ourcity.callback.CallBackWithData;


/**
 * Created by podo on 03.09.15.
 */
public class HotCallsDialog extends HotCallsEditableDialog {

    @Override
    public void onViewCreated(View _view, @Nullable Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
        editMode(false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (isAdded()) {
            getActivity().onBackPressed();
        }
    }
}
