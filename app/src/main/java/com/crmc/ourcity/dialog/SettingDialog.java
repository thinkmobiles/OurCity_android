package com.crmc.ourcity.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.crmc.ourcity.R;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;

/**
 * Created by podo on 21.07.15.
 */
public class SettingDialog extends BaseFourStatesFragment implements View.OnClickListener {

    private RelativeLayout register;
    private RelativeLayout confirmation;


    private OnActionDialogListener mCallback;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
            mCallback = (OnActionDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                            + " must implement OnActionDialogListener");
        }
    }

    @Override
    protected void initViews() {
        register = findView(R.id.register);
        confirmation = findView(R.id.confirmation);
        showContent();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_dialog_setting_test;
    }

    @Override
    protected void setListeners() {
        register.setOnClickListener(this);
        confirmation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                mCallback.onActionDialogSelected(DialogType.REGISTER);
                break;
            case R.id.confirmation:
                Toast.makeText(getActivity().getApplicationContext(), "Clicked confirmation", Toast.LENGTH_SHORT).show();
                mCallback.onActionDialogSelected(DialogType.CONFIRMATION);
                break;
//            case R.id.button2:
//                break;
//            case R.id.button3:
//                break;
        }
    }

    @Override
    public void onRetryClick() {

    }
}
