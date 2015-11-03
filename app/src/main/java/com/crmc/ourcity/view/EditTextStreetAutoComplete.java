package com.crmc.ourcity.view;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

public class EditTextStreetAutoComplete extends AutoCompleteTextView {

    private int myThreshold;

    public EditTextStreetAutoComplete(Context _context) {
        super(_context);
    }

    public EditTextStreetAutoComplete(Context _arg0, AttributeSet _arg1) {
        super(_arg0, _arg1);
    }

    public EditTextStreetAutoComplete(Context _arg0, AttributeSet _arg1, int _arg2) {
        super(_arg0, _arg1, _arg2);
    }

    @Override
    public void setThreshold(int threshold) {
        if (threshold < 0) {
            threshold = 0;
        }
        myThreshold = threshold;
    }

    @Override
    public boolean enoughToFilter() {
        return getText().length() >= myThreshold;
    }

    @Override
    public int getThreshold() {
        return myThreshold;
    }

    @Override
    protected void onFocusChanged(boolean _focused, int _direction, Rect _previouslyFocusedRect) {
        super.onFocusChanged(_focused, _direction, _previouslyFocusedRect);
        if (_focused) {
            if (!TextUtils.isEmpty(getText().toString())) {
                performFiltering(getText(), 0);
            }
            showDropDown();
        }
    }
}