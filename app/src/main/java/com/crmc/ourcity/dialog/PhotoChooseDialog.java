package com.crmc.ourcity.dialog;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.callback.OnActionDialogListener;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.utils.Image;

import java.lang.ref.WeakReference;

public class PhotoChooseDialog extends BaseFourStatesFragment implements OnClickListener {

    private TextView tvCamera;
    private TextView tvGallery;
    private OnActionDialogListener mCallback;
    private View vFstDivider, vScnDivider, vThdDivider;
    private WeakReference<DialogActivity> mActivity;

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);

        try {
            mActivity = new WeakReference<>((DialogActivity) _activity);
            mCallback = (OnActionDialogListener) _activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(_activity.toString() + " must implement OnActionDialogListener");
        }
    }

    @Override
    public void onDetach() {
        mCallback = null;
        mActivity.clear();
        super.onDetach();
    }

    @Override
    protected void initViews() {
        tvCamera = findView(R.id.tvCamera_CFD);
        tvGallery = findView(R.id.tvGallery_CFD);
        vFstDivider = findView(R.id.vFirstDivider_CFD);
        vScnDivider = findView(R.id.vSecondDivider_CFD);
        vThdDivider = findView(R.id.vThirdDivider_CFD);
        Image.setBackgroundColorArrayViewWithoutDrawable(mActivity.get(), new View[]{vFstDivider, vScnDivider, vThdDivider}, Image.darkenColor(0.0));
        showContent();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_dialog_photo_choosing;
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