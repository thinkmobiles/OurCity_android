package com.crmc.ourcity.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.crmc.ourcity.R;
import com.crmc.ourcity.callback.OnActionDialogListener;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;

/**
 * Created by SetKrul on 23.07.2015.
 */
public class PhotoChooseDialog extends BaseFourStatesFragment implements OnClickListener {

    private TextView tvCamera;
    private TextView tvGallery;
    private OnActionDialogListener mCallback;

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);

        try {
            mCallback = (OnActionDialogListener) _activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(_activity.toString() + " must implement OnActionDialogListener");
        }
    }

    @Override
    protected void initViews() {
        tvCamera = findView(R.id.tvCamera_CFD);
        tvGallery = findView(R.id.tvGallery_CFD);
        showContent();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_dialog_photo_choose;
    }

    @Override
    protected void setListeners() {
        tvCamera.setOnClickListener(this);
        tvGallery.setOnClickListener(this);
    }

    @Override
    public void onClick(View _view) {
        switch (_view.getId()) {
            case R.id.tvCamera_CFD:
                mCallback.onActionDialogSelected(DialogType.PHOTO_CAM);
                break;
            case R.id.tvGallery_CFD:
                mCallback.onActionDialogSelected(DialogType.PHOTO_GALLERY);
                break;
        }
    }

    @Override
    public void onRetryClick() {

    }
}