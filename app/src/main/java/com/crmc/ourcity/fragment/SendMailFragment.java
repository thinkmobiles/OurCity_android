package com.crmc.ourcity.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.crmc.ourcity.R;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.utils.Image;

/**
 * Created by SetKrul on 03.09.2015.
 */
public class SendMailFragment extends BaseFourStatesFragment implements View.OnClickListener {

    private View vTopLine_SMF;
    private View vUnderLine_SMF;
    private EditText etFirstName_SMF;
    private EditText etLastName_SMF;
    private EditText etMail_SMF;
    private EditText etDescription_SMF;
    private Button btnCancel_SMF;
    private Button btnOk_SMF;
    private String color;
    private String mail;

    public static SendMailFragment newInstance(String _colorItem, String _mail) {
        SendMailFragment mSendMailFragment = new SendMailFragment();
        Bundle args = new Bundle();
        args.putString(Constants.CONFIGURATION_KEY_COLOR, _colorItem);
        args.putString(Constants.CONFIGURATION_KEY_MAIL, _mail);
        mSendMailFragment.setArguments(args);
        return mSendMailFragment;
    }

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        color = getArguments().getString(Constants.CONFIGURATION_KEY_COLOR);
        mail = getArguments().getString(Constants.CONFIGURATION_KEY_MAIL);
    }

    @Override
    public void onViewCreated(final View _view, final Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
        showContent();
    }

    @Override
    protected void initViews() {
        super.initViews();
        vTopLine_SMF = findView(R.id.vTopLine_SMF);
        vUnderLine_SMF = findView(R.id.vUnderLine_SMF);
        etFirstName_SMF = findView(R.id.etFirstName_SMF);
        etLastName_SMF = findView(R.id.etLastName_SMF);
        etMail_SMF = findView(R.id.etMail_SMF);
        etDescription_SMF = findView(R.id.etDescription_SMF);
        btnCancel_SMF = findView(R.id.btnCancel_SMF);
        btnOk_SMF = findView(R.id.btnOk_SMF);

        Image.init(Color.parseColor(color));
        Image.setBackgroundColorArrayView(getActivity(), new View[]{etFirstName_SMF,
                etLastName_SMF, etMail_SMF, etDescription_SMF}, R.drawable.boarder_round_green_ff);
        vUnderLine_SMF.setBackgroundColor(Image.darkenColor(0.0));
        vTopLine_SMF.setBackgroundColor(Image.darkenColor(0.0));
        Image.setBackgroundColorArrayView(getActivity(), new View[]{btnCancel_SMF, btnOk_SMF}, R.drawable
                .selector_button_green_ff);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        btnCancel_SMF.setOnClickListener(this);
        btnOk_SMF.setOnClickListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_send_mail;
    }


    @Override
    public void onClick(View _view) {
        switch (_view.getId()) {
            case R.id.btnCancel_SMF:
                popBackStack();
                break;
            case R.id.btnOk_SMF:
                String[] send = {mail};
                String title = "יצירת קשר אפליקציה ";
                String subject = "שם" + ":  " + etFirstName_SMF.getText().toString() + " " + etLastName_SMF.getText()
                        .toString() + "\n" + "כתובת דואר אלקטרוני" + ":  " + etMail_SMF.getText().toString() + "\n" +
                        "מידע" + ":  " + etDescription_SMF.getText().toString();

                sendMail(send, title, subject);
                break;
        }
    }

    public final void sendMail(final String[] _mailAdress, final String title, final String text) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, _mailAdress);
        i.putExtra(Intent.EXTRA_SUBJECT, title);
        i.putExtra(Intent.EXTRA_TEXT, text);
        getActivity().startActivity(Intent.createChooser(i, "Send mail..."));

    }

    @Override
    public void onRetryClick() {

    }
}
