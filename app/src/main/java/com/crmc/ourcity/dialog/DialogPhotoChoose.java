package com.crmc.ourcity.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.fragment.BaseFragment;

/**
 * Created by SetKrul on 23.07.2015.
 */
public class DialogPhotoChoose extends BaseFragment implements View.OnClickListener {

    private TextView tvCamera;
    private TextView tvGallery;
    private OnActionDialogListener mCallback;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (OnActionDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnActionDialogListener");
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_dialog_photo_choose;
    }

    @Override
    protected void initViews() {
        tvCamera = findView(R.id.tvCamera_CFD);
        tvGallery = findView(R.id.tvGallery_CFD);
    }

    @Override
    protected void setListeners() {
        tvCamera.setOnClickListener(this);
        tvGallery.setOnClickListener(this);
    }

    //for test
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvCamera_CFD:
                mCallback.onActionDialogSelected(DialogType.PHOTOCAM);
                break;
            case R.id.tvGallery_CFD:
                mCallback.onActionDialogSelected(DialogType.PHOTOGALLERY);
                break;
        }
    }
}