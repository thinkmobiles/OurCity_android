package com.crmc.ourcity.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;


/**
 * Created by podo on 03.09.15.
 */
public class HotCallsDialog extends HotCallsEditableDialog {

    @Override
    public void onViewCreated(View _view, @Nullable Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
        editMode(false);
    }
    //TODO:finish activity when onBackPressed
}
