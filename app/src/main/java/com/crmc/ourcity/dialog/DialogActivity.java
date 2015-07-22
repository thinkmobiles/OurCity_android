package com.crmc.ourcity.dialog;

import android.os.Bundle;

import com.crmc.ourcity.R;
import com.crmc.ourcity.activity.BaseFragmentActivity;
import com.crmc.ourcity.utils.EnumUtil;

public class DialogActivity extends BaseFragmentActivity implements OnActionDialogListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog);

        DialogType type = EnumUtil.deserialize(DialogType.class).from(getIntent());
        switch (type){
            case SETTING:
                SettingDialog settingDialog = new SettingDialog();
                replaceFragmentWithoutBackStack(R.id.fragment_dialog_container, settingDialog);
        }
    }

    @Override
    public void onActionDialogSelected(DialogType action) {
        switch (action){
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
