package com.crmc.ourcity.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

public class HotCallsDialog extends HotCallsEditableDialog {

    @Override
    public void onViewCreated(View _view, @Nullable Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
        editMode(false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    }

    @Override
    public void onDetach() {
        getActivity().finish();
        super.onDetach();
    }
}
