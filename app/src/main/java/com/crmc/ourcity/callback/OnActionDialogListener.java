package com.crmc.ourcity.callback;

import com.crmc.ourcity.dialog.DialogType;

/**
 * @Interface for communicating fragment (dialog)  with host activity
 */

public interface OnActionDialogListener {
    void onActionDialogSelected(DialogType _action);
    void onLogoutListener();
}
