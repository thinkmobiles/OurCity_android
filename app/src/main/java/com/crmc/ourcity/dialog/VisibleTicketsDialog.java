package com.crmc.ourcity.dialog;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.crmc.ourcity.R;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.utils.SPManager;

import java.lang.ref.WeakReference;

public class VisibleTicketsDialog extends BaseFourStatesFragment {

    private Button btnSave;
    private RadioGroup rgVisibleTickets;
    private RadioButton rbVisibleTickets20;
    private RadioButton rbVisibleTickets50;
    private RadioButton rbVisibleTickets100;
    private int selectedItemID;
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
        return R.layout.fragment_dialog_visible_tickets;
    }

    @Override
    protected void initViews() {
        super.initViews();

        btnSave = findView(R.id.btnSave_FDVT);
        rgVisibleTickets = findView(R.id.rgVisibleTickets_FDVT);
        rbVisibleTickets20 = findView(R.id.rbVisibleTickets20_FDVT);
        rbVisibleTickets50 = findView(R.id.rbVisibleTickets50_FDVT);
        rbVisibleTickets100 = findView(R.id.rbVisibleTickets100_FDVT);
        setPreviousCheckedRadioButton();

        showContent();
    }

    private void setPreviousCheckedRadioButton() {
        selectedItemID = SPManager.getInstance(mActivity.get()).getAmountOfVisibleTickets();
        switch (selectedItemID) {
            case R.id.rbVisibleTickets20_FDVT:
                rbVisibleTickets20.setChecked(true);
                break;
            case R.id.rbVisibleTickets50_FDVT:
                rbVisibleTickets50.setChecked(true);
                break;
            case R.id.rbVisibleTickets100_FDVT:
                rbVisibleTickets100.setChecked(true);
                break;
        }
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        btnSave.setOnClickListener(handleClick());
        rgVisibleTickets.setOnCheckedChangeListener(handleRbChecking());
    }

    @NonNull
    private RadioGroup.OnCheckedChangeListener handleRbChecking() {
        return (group, checkedId) -> {
            switch (group.getCheckedRadioButtonId()) {
                case R.id.rbVisibleTickets20_FDVT:
                    selectedItemID = checkedId;
                    break;
                case R.id.rbVisibleTickets50_FDVT:
                    selectedItemID = checkedId;
                    break;
                case R.id.rbVisibleTickets100_FDVT:
                    selectedItemID = checkedId;
                    break;
            }
        };
    }

    @NonNull
    private View.OnClickListener handleClick() {
        return v -> {
         SPManager.getInstance(mActivity.get()).setAmountOfVisibleTickets(selectedItemID);
         popBackStack();
        };
    }

    @Override
    public void onRetryClick() {

    }
}
