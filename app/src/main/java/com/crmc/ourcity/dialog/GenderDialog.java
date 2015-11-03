package com.crmc.ourcity.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.callback.CallBackWithData;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;

public class GenderDialog extends BaseFourStatesFragment implements View.OnClickListener {

    private static final String CONFIGURATION_KEY = "CONFIGURATION_KEY";
    private TextView btnCancelFilter;
    private TextView btnSelectFilter;
    private CallBackWithData mCallback;
    private int gender;
    private RadioGroup rgGender;

    public static GenderDialog newInstance(Integer _gender) {
        GenderDialog mGenderDialog = new GenderDialog();
        Bundle args = new Bundle();
        args.putInt(CONFIGURATION_KEY, _gender);
        mGenderDialog.setArguments(args);
        return mGenderDialog;
    }

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        gender = getArguments().getInt(CONFIGURATION_KEY);
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
        btnCancelFilter = findView(R.id.btnCancel_DFG);
        btnSelectFilter = findView(R.id.btnOk_DFG);
        rgGender = findView(R.id.rgGender_FDG);
        if (gender != -1) {
            ((RadioButton) rgGender.getChildAt(gender)).setChecked(true);
        }
        showContent();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_dialog_gender;
    }

    @Override
    protected void setListeners() {
        btnCancelFilter.setOnClickListener(this);
        btnSelectFilter.setOnClickListener(this);
    }

    @Override
    public void onClick(View _v) {
        switch (_v.getId()) {
            case R.id.btnCancel_DFG:
                mCallback.onActionDialogCancel(false);
                break;
            case R.id.btnOk_DFG:
                gender = rgGender.indexOfChild(rgGender.findViewById(rgGender.getCheckedRadioButtonId()));
                mCallback.onActionDialogDataInteger(gender);
                break;
        }
    }

    @Override
    public void onRetryClick() {
    }
}
