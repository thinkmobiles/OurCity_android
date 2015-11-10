package com.crmc.ourcity.dialog;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.crmc.ourcity.R;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.utils.IntentUtils;
import com.crmc.ourcity.utils.SPManager;

import java.lang.ref.WeakReference;

public class HotCallsEditableDialog extends BaseFourStatesFragment {

    private EditText etFirstEmergencyNumber;
    private EditText etSecondEmergencyNumber;
    private EditText etThirdEmergencyNumber;
    private Button btnSavePhones;
    private ImageView ivCallFirst;
    private ImageView ivCallSecond;
    private ImageView ivCallThird;
    private WeakReference<DialogActivity> mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = new WeakReference<>((DialogActivity) activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity.clear();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_dialog_hotcalls;
    }

    @Override
    protected void initViews() {
        super.initViews();

        etFirstEmergencyNumber = findView(R.id.etFirstEmergencyNumber_FDHC);
        etSecondEmergencyNumber = findView(R.id.etSecondEmergencyNumber_FDHC);
        etThirdEmergencyNumber = findView(R.id.etThirdEmergencyNumber_FDHC);
        ivCallFirst = findView(R.id.ivCallFirst_FDHC);
        ivCallSecond = findView(R.id.ivCallSecond_FDHC);
        ivCallThird = findView(R.id.ivCallThird_FDHC);
        btnSavePhones = findView(R.id.btnSavePhones_FDHC);
        editMode(true);
        showContent();
    }

    @Override
    protected void setListeners() {
        ivCallFirst.setOnClickListener(handleClick());
        ivCallSecond.setOnClickListener(handleClick());
        ivCallThird.setOnClickListener(handleClick());
        btnSavePhones.setOnClickListener(handleClick());
    }

    private void enableInput(boolean _isEnabled) {
        etFirstEmergencyNumber.setEnabled(_isEnabled);
        etSecondEmergencyNumber.setEnabled(_isEnabled);
        etThirdEmergencyNumber.setEnabled(_isEnabled);
        if (_isEnabled) {
            btnSavePhones.setVisibility(View.VISIBLE);
        } else {
            btnSavePhones.setVisibility(View.GONE);
        }
    }

    private void putValue() {
        if (SPManager.getInstance(mActivity.get()).getFirstEmergencyNum().length() > 0) {
            etFirstEmergencyNumber.setText(SPManager.getInstance(mActivity.get()).getFirstEmergencyNum());
        }
        if (SPManager.getInstance(mActivity.get()).getSecondEmergencyNum().length() > 0) {
            etSecondEmergencyNumber.setText(SPManager.getInstance(mActivity.get()).getSecondEmergencyNum());
        }
        if (SPManager.getInstance(mActivity.get()).getThirdEmergencyNum().length() > 0) {
            etThirdEmergencyNumber.setText(SPManager.getInstance(mActivity.get()).getThirdEmergencyNum());
        }
    }

    protected void editMode(boolean _isEditableMode) {
        putValue();
        enableInput(_isEditableMode);
    }

    private void doCall(String _phone) {
        if (_phone.length() > 0) {
            try {
                startActivityForResult(Intent.createChooser(IntentUtils.getIntentCall(_phone), getResources()
                        .getString(R.string.call_hint)), 57);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(mActivity.get(), getResources().getString(R.string.app_no_call_client),
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(mActivity.get(), getResources().getString(R.string.ivalid_number), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String getFirstEmergencyNumber() {
        return etFirstEmergencyNumber.getText().toString();
    }

    private String getSecondEmergencyNumber() {
        return etSecondEmergencyNumber.getText().toString();
    }

    private String getThirdEmergencyNumber() {
        return etThirdEmergencyNumber.getText().toString();
    }

    @NonNull
    private View.OnClickListener handleClick() {
        return v -> {
            switch (v.getId()) {
                case R.id.ivCallFirst_FDHC:
                    doCall(getFirstEmergencyNumber());
                    break;

                case R.id.ivCallSecond_FDHC:
                    doCall(getSecondEmergencyNumber());
                    break;

                case R.id.ivCallThird_FDHC:
                    doCall(getThirdEmergencyNumber());
                    break;

                case R.id.btnSavePhones_FDHC:
                    saveEmergencyNumbers();
                    hideKeyboard(mActivity.get());
                    popBackStack();
                    break;
            }
        };
    }

    private void saveEmergencyNumbers() {
        SPManager.getInstance(mActivity.get()).setFirstEmergencyNumber(getFirstEmergencyNumber());
        SPManager.getInstance(mActivity.get()).setSecondEmergencyNum(getSecondEmergencyNumber());
        SPManager.getInstance(mActivity.get()).setThirdEmergencyNumber(getThirdEmergencyNumber());
    }

    @Override
    public void onRetryClick() {
    }
}
