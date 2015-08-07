package com.crmc.ourcity.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

/**
 * Created by SetKrul on 23.07.2015.
 */
public class EditTextStreetAutoComplete_FF extends AutoCompleteTextView {

    public EditTextStreetAutoComplete_FF(Context _context) {
        super(_context);
    }

    public EditTextStreetAutoComplete_FF(Context _arg0, AttributeSet _arg1) {
        super(_arg0, _arg1);
    }

    public EditTextStreetAutoComplete_FF(Context _arg0, AttributeSet _arg1, int _arg2) {
        super(_arg0, _arg1, _arg2);
    }

    @Override
    public boolean enoughToFilter() {
        return true;
    }

    @Override
    protected void onFocusChanged(boolean _focused, int _direction,
                                  Rect _previouslyFocusedRect) {
        super.onFocusChanged(_focused, _direction, _previouslyFocusedRect);
        if (_focused) {
            performFiltering(getText(), 0);
        }
    }
}