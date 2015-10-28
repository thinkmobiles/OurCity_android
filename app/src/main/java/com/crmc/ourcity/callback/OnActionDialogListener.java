package com.crmc.ourcity.callback;

/**
 * Created by podo on 22.07.15.
 */

import com.crmc.ourcity.dialog.DialogType;

/**
 * @Interface for communicating fragment (dialog)  with host activity
 */

public interface OnActionDialogListener {
    void onActionDialogSelected(DialogType _action);
    void onLogoutListener();
}
