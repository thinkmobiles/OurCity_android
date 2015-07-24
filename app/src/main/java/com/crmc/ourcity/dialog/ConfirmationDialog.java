package com.crmc.ourcity.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.crmc.ourcity.R;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;


/**
 * Created by podo on 21.07.15.
 */
public class ConfirmationDialog extends BaseFourStatesFragment implements View.OnClickListener {

    private TextView tvOk;
    private TextView tvCancel;
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
        tvOk = findView(R.id.tvOk);
        tvCancel = findView(R.id.tvCancel);
        showContent();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_dialog_confirmation;
    }

    @Override
    protected void setListeners() {
        tvOk.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }

    //for test
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvOk:
                mCallback.onActionDialogSelected(DialogType.REGISTER);
                break;
            case R.id.tvCancel:
                Toast.makeText(getActivity().getApplicationContext(), "btnCancel is clicked", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onRetryClick() {

    }
}
