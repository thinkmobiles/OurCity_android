package com.crmc.ourcity.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.crmc.ourcity.R;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.utils.SPManager;

public class LanguageDialog extends BaseFourStatesFragment {


    private Button btnSave;
    private RadioGroup rgLanguages;
    private RadioButton rbLangEn;
    private RadioButton rbLangHe;
    private int selectedItemID;

    @Override
    protected void initViews() {
        super.initViews();
        rgLanguages = findView(R.id.rgLanguage_FDL);
        rbLangEn = findView(R.id.rbLanguage_EN_FDL);
        rbLangHe = findView(R.id.rbLanguage_HE_FDL);
        btnSave = findView(R.id.btnSave_FDL);
        setPreviousCheckedLanguage();
        showContent();
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        rgLanguages.setOnCheckedChangeListener(handleRbChecking());
        btnSave.setOnClickListener(v -> {
            if (selectedItemID != SPManager.getInstance(getActivity()).getAppLanguage()) {
                MyAlertDialogFragment frag = MyAlertDialogFragment.newInstance(selectedItemID);
                frag.show(getActivity().getSupportFragmentManager(), "dialog");
            } else popBackStack();
        });
    }

    @NonNull
    private RadioGroup.OnCheckedChangeListener handleRbChecking() {
        return (group, checkedId) -> {
            switch (group.getCheckedRadioButtonId()) {
                case R.id.rbLanguage_EN_FDL:
                    selectedItemID = checkedId;
                    break;
                case R.id.rbLanguage_HE_FDL:
                    selectedItemID = checkedId;
                    break;
                default:
                    break;
            }
        };
    }

    private void setPreviousCheckedLanguage() {
        selectedItemID = SPManager.getInstance(getActivity()).getAppLanguage();
        switch (selectedItemID) {
            case R.id.rbLanguage_EN_FDL:
                rbLangEn.setChecked(true);
                break;
            case R.id.rbLanguage_HE_FDL:
                rbLangHe.setChecked(true);
                break;
            default:
                break;
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_dialog_languages;
    }

    @Override
    public void onRetryClick() {

    }

    public static class MyAlertDialogFragment extends DialogFragment {
        public static MyAlertDialogFragment newInstance(int _selectedItemID) {
            MyAlertDialogFragment frag = new MyAlertDialogFragment();
            Bundle args = new Bundle();
            args.putInt("selectedItemID", _selectedItemID);
            frag.setArguments(args);
            return frag;

        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            int selectedItemID = getArguments().getInt("selectedItemID");



            return new AlertDialog.Builder(getActivity())
                    .setTitle("Внимание")
                    .setMessage("Изменения вступят в силу после перезагрузки")
                    .setPositiveButton("ок", (dialog, which) -> {

                String lang = "";
                String country = "";

                switch (selectedItemID) {
                    case R.id.rbLanguage_HE_FDL:
                        lang = "iw";
                        country = "IL";
                        break;
                    case R.id.rbLanguage_EN_FDL:
                        lang = "en";
                        country = "GB";
                        break;
                    default:
                        break;
                }

                SPManager.getInstance(getActivity()).setAppLanguage(selectedItemID);
                SPManager.getInstance(getActivity()).setApplicationLanguage(lang);
                SPManager.getInstance(getActivity()).setApplicationCountry(country);
                ((DialogActivity) getActivity()).doPositiveClick();
            }).show();
        }
    }
}
