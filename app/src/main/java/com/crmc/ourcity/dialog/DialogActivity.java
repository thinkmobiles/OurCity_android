package com.crmc.ourcity.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.crmc.ourcity.R;
import com.crmc.ourcity.activity.BaseFragmentActivity;
import com.crmc.ourcity.callback.MapFilterCallBack;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.model.Marker;
import com.crmc.ourcity.utils.EnumUtil;

import java.util.ArrayList;
import java.util.List;

public class DialogActivity extends BaseFragmentActivity implements OnActionDialogListener, MapFilterCallBack {

    private Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle _savedInstanceState) {

        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_dialog);

        DialogType type = EnumUtil.deserialize(DialogType.class).from(getIntent());
        switch (type) {
            case FILTER_MAP:
                ArrayList<Marker> mMarkers = getIntent().getParcelableArrayListExtra(Marker.class.getCanonicalName());
                replaceFragmentWithoutBackStack(R.id.fragment_dialog_container, MarkerFilterDialog.newInstance
                        (mMarkers));
                break;

            case PHOTO:
                replaceFragmentWithoutBackStack(R.id.fragment_dialog_container, new PhotoChooseDialog());
                break;

            case SETTING:
                replaceFragmentWithoutBackStack(R.id.fragment_dialog_container, new SettingDialog());
                break;
        }
    }

    @Override
    public void onActionDialogSelected(DialogType action) {
        switch (action) {
            case PHOTO_CAM:
                intent.putExtra(Constants.REQUEST_INTENT_TYPE_PHOTO, Constants.REQUEST_PHOTO);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case PHOTO_GALLERY:
                intent.putExtra(Constants.REQUEST_INTENT_TYPE_PHOTO, Constants.REQUEST_GALLERY_IMAGE);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case REGISTER:
                replaceFragmentWithBackStack(R.id.fragment_dialog_container, new TestDialog());
                break;
            case CONFIRMATION:
                replaceFragmentWithBackStack(R.id.fragment_dialog_container, new ConfirmationDialog());
                break;
        }
    }

    @Override
    public void onActionDialogDataSelected(List<Marker> _list) {
        intent.putParcelableArrayListExtra(Marker.class.getCanonicalName(), (ArrayList<? extends
                Parcelable>) _list);
        intent.putExtra(Constants.REQUEST_MAP_FILTER_TYPE, Constants.REQUEST_MAP_SELECTED);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onActionDialogDataCancel(boolean check) {
        intent.putExtra(Constants.REQUEST_MAP_FILTER_TYPE, Constants.REQUEST_MAP_CANCEL_SELECTED);
        setResult(RESULT_OK, intent);
        finish();
    }
}
