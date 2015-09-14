package com.crmc.ourcity.dialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.crmc.ourcity.R;
import com.crmc.ourcity.fragment.BaseFragment;
import com.crmc.ourcity.utils.SPManager;

/**
 * Created by podo on 03.09.15.
 */
public class HotCallsEditableDialog extends BaseFragment implements View.OnClickListener {

    private View root;
    private EditText etFirstEmergencyNumber;
    private EditText etSecondEmergencyNumber;
    private EditText etThirdEmergencyNumber;
    private Button btnSavePhones;
    private ImageView ivCallFirst;
    private ImageView ivCallSecond;
    private ImageView ivCallThird;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_dialog_hotcalls, container, false);
        findUI(root);
        setListeners();
        editMode(true);

        return root;
    }

    private void findUI(View root) {
        etFirstEmergencyNumber = (EditText) root.findViewById(R.id.etFirstEmergencyNumber_FDHC);
        etSecondEmergencyNumber = (EditText) root.findViewById(R.id.etSecondEmergencyNumber_FDHC);
        etThirdEmergencyNumber = (EditText) root.findViewById(R.id.etThirdEmergencyNumber_FDHC);
        ivCallFirst = (ImageView) root.findViewById(R.id.ivCallFirst_FDHC);
        ivCallSecond = (ImageView) root.findViewById(R.id.ivCallSecond_FDHC);
        ivCallThird = (ImageView) root.findViewById(R.id.ivCallThird_FDHC);
        btnSavePhones = (Button) root.findViewById(R.id.btnSavePhones_FDHC);
    }

    private void setListeners() {
        ivCallFirst.setOnClickListener(this);
        ivCallSecond.setOnClickListener(this);
        ivCallThird.setOnClickListener(this);
        btnSavePhones.setOnClickListener(this);
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
        if (SPManager.getInstance(getActivity()).getFirstEmergencyNum().length() > 0) {
            etFirstEmergencyNumber.setText(SPManager.getInstance(getActivity()).getFirstEmergencyNum());
        }
        if (SPManager.getInstance(getActivity()).getSecondEmergencyNum().length() > 0) {
            etSecondEmergencyNumber.setText(SPManager.getInstance(getActivity()).getSecondEmergencyNum());
        }
        if (SPManager.getInstance(getActivity()).getThirdEmergencyNum().length() > 0) {
            etThirdEmergencyNumber.setText(SPManager.getInstance(getActivity()).getThirdEmergencyNum());
        }
    }

    protected void editMode(boolean _isEditableMode) {
        putValue();
        enableInput(_isEditableMode);
    }

    private void doCall(String _phone) {
        if (_phone.length() > 0) {
            String uri = "tel:" + _phone;
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse(uri));
            startActivity(intent);
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.ivalid_number), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
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
                SPManager.getInstance(getActivity()).setFirstEmergencyNumber(getFirstEmergencyNumber());
                SPManager.getInstance(getActivity()).setSecondEmergencyNum(getSecondEmergencyNumber());
                SPManager.getInstance(getActivity()).setThirdEmergencyNumber(getThirdEmergencyNumber());
                hideKeyboard(getActivity());
                popBackStack();
                break;
        }
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
}
