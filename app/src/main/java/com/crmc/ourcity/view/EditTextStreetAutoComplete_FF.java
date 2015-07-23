package com.crmc.ourcity.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

/**
 * Created by SetKrul on 23.07.2015.
 */
public class EditTextStreetAutoComplete_FF extends AutoCompleteTextView {

    public EditTextStreetAutoComplete_FF(Context context) {
        super(context);
    }

    public EditTextStreetAutoComplete_FF(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
    }

    public EditTextStreetAutoComplete_FF(Context arg0, AttributeSet arg1, int arg2) {
        super(arg0, arg1, arg2);
    }

    @Override
    public boolean enoughToFilter() {
        return true;
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction,
                                  Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused) {
            performFiltering(getText(), 0);
        }
    }

}