package com.crmc.ourcity.dialog;

import android.content.Intent;
import android.os.Bundle;

import com.crmc.ourcity.R;
import com.crmc.ourcity.activity.BaseFragmentActivity;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.utils.EnumUtil;

public class DialogActivity extends BaseFragmentActivity implements OnActionDialogListener {

    private Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog);

        DialogType type = EnumUtil.deserialize(DialogType.class).from(getIntent());
        switch (type){

            case PHOTO:
                DialogPhotoChoose dialogPhotoChoose = new DialogPhotoChoose();
                replaceFragmentWithoutBackStack(R.id.fragment_dialog_container, dialogPhotoChoose);
                break;

            case SETTING:
                SettingDialog settingDialog = new SettingDialog();
                replaceFragmentWithoutBackStack(R.id.fragment_dialog_container, settingDialog);
                break;
        }
    }

    @Override
    public void onActionDialogSelected(DialogType action) {
        switch (action){
            case PHOTOCAM:
                intent.putExtra(Constants.REQUEST_INTENT_TYPE_PHOTO, Constants.REQUEST_PHOTO);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case PHOTOGALLERY:
                intent.putExtra(Constants.REQUEST_INTENT_TYPE_PHOTO, Constants.REQUEST_GALLERY_IMAGE);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case REGISTER:
                TestDialog testDialog = new TestDialog();
                replaceFragmentWithBackStack(R.id.fragment_dialog_container, testDialog);
                break;
            case CONFIRMATION:
                ConfirmationDialog confirmationDialog = new ConfirmationDialog();
                replaceFragmentWithBackStack(R.id.fragment_dialog_container, confirmationDialog);
                break;
        }
    }
}
