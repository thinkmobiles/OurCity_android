package com.crmc.ourcity.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.callback.CallBackWithData;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;

public class AgeDialog extends BaseFourStatesFragment implements View.OnClickListener {

    private static final String CONFIGURATION_KEY = "CONFIGURATION_KEY";
    private TextView btnCancelFilter;
    private TextView btnSelectFilter;
    private CallBackWithData mCallback;
    private NumberPicker npAge;
    private final static int minAge = 1;
    private final static int maxAge = 100;
    private int nowAge;

    public static AgeDialog newInstance(Integer _age) {
        AgeDialog mAgeDialog = new AgeDialog();
        Bundle args = new Bundle();
        args.putInt(CONFIGURATION_KEY, _age);
        mAgeDialog.setArguments(args);
        return mAgeDialog;
    }

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        nowAge = getArguments().getInt(CONFIGURATION_KEY);
    }

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);
        try {
            mCallback = (CallBackWithData) _activity;
        } catch (ClassCastException _e) {
            throw new ClassCastException(_activity.toString() + " must implement OnActionDialogListenerWithData");
        }
    }

    @Override
    public void onDetach() {
        mCallback = null;
        super.onDetach();
    }

    @Override
    protected void initViews() {
        btnCancelFilter = findView(R.id.btnCancel_DFA);
        btnSelectFilter = findView(R.id.btnOk_DFA);
        npAge = findView(R.id.npAge_DFA);
        npAge.setMinValue(minAge);
        npAge.setMaxValue(maxAge);
        npAge.setValue(nowAge);
        npAge.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        showContent();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_dialog_age;
    }

    @Override
    protected void setListeners() {
        btnCancelFilter.setOnClickListener(this);
        btnSelectFilter.setOnClickListener(this);
    }

    @Override
    public void onClick(View _v) {
        switch (_v.getId()) {
            case R.id.btnCancel_DFA:
                mCallback.onActionDialogCancel(false);
                break;
            case R.id.btnOk_DFA:
                mCallback.onActionDialogDataInteger(npAge.getValue());
                break;
        }
    }

    @Override
    public void onRetryClick() {
    }
}