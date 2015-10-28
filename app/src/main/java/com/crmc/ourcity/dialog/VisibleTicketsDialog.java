package com.crmc.ourcity.dialog;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.crmc.ourcity.R;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.utils.SPManager;

/**
 * Created by andrei on 21.10.15.
 */
public class VisibleTicketsDialog extends BaseFourStatesFragment {

    private Button btnSave;
    private RadioGroup rgVisibleTickets;
    private RadioButton rbVisibleTickets20;
    private RadioButton rbVisibleTickets50;
    private RadioButton rbVisibleTickets100;
    private int selectedItemID;

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
        selectedItemID = SPManager.getInstance(getActivity()).getAmountOfVisibleTickets();
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
         SPManager.getInstance(getActivity()).setAmountOfVisibleTickets(selectedItemID);
         popBackStack();
        };
    }

    @Override
    public void onRetryClick() {

    }

//    private int getAmountOfVisibleClosedTickets(int rbId){
//        //def value
//        int result = 20;
//        switch (rbId){
//            case R.id.rbVisibleTickets20_FDVT:
//                result = 20;
//                break;
//            case R.id.rbVisibleTickets50_FDVT:
//                result = 50;
//                break;
//            case R.id.rbVisibleTickets100_FDVT:
//                result = 100;
//                break;
//        }
//        return result;
//    }
}
