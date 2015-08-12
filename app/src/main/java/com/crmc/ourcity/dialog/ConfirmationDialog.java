package com.crmc.ourcity.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.crmc.ourcity.R;
import com.crmc.ourcity.callback.OnActionDialogListener;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;


/**
 * Created by podo on 21.07.15.
 */
public class ConfirmationDialog extends BaseFourStatesFragment implements View.OnClickListener {

    private TextView tvHeader;
    private TextView tvOk;
    private TextView tvCancel;
    private OnActionDialogListener mCallback;

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);

        try{
            mCallback = (OnActionDialogListener) _activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(_activity.toString()
                    + " must implement OnActionDialogListener");
        }
    }

    @Override
    protected void initViews() {
        tvHeader = findView(R.id.tvHeader_CD);
        tvHeader.setText(getResources().getText(R.string.txt_login));
        tvOk = findView(R.id.tvOk_CD);
        tvCancel = findView(R.id.tvCancel_CD);
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
    public void onClick(View _view) {
        switch (_view.getId()) {
            case R.id.tvOk_CD:
                mCallback.onActionDialogSelected(DialogType.REGISTER);
                break;
            case R.id.tvCancel_CD:
                Toast.makeText(getActivity().getApplicationContext(), "btnCancel is clicked", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onRetryClick() {

    }
}
