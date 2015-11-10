package com.crmc.ourcity.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.crmc.ourcity.R;
import com.crmc.ourcity.activity.MainActivity;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.utils.Image;

import java.lang.ref.WeakReference;

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
    private String title;
    private WeakReference<MainActivity> mActivity;

    public static SendMailFragment newInstance(String _colorItem, String _mail, String _title) {
        SendMailFragment mSendMailFragment = new SendMailFragment();
        Bundle args = new Bundle();
        args.putString(Constants.CONFIGURATION_KEY_COLOR, _colorItem);
        args.putString(Constants.CONFIGURATION_KEY_MAIL, _mail);
        args.putString(Constants.NODE_TITLE, _title);
        mSendMailFragment.setArguments(args);
        return mSendMailFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = new WeakReference<>((MainActivity) activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity.clear();
    }

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        color = getArguments().getString(Constants.CONFIGURATION_KEY_COLOR);
        mail = getArguments().getString(Constants.CONFIGURATION_KEY_MAIL);
        title = getArguments().getString(Constants.NODE_TITLE);
    }

    @Override
    public void onResume() {
        configureActionBar(true, true, title);
        super.onResume();
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

        try {
            Image.init(Color.parseColor(color));
        } catch (Exception e) {
            Image.init(Color.BLACK);
        }
        Image.setBoarderBackgroundColorArray(mActivity.get(), color, 2, 5, "#ffffff",
                new View[]{etFirstName_SMF, etLastName_SMF, etMail_SMF, etDescription_SMF});
        vUnderLine_SMF.setBackgroundColor(Image.darkenColor(0.0));
        vTopLine_SMF.setBackgroundColor(Image.darkenColor(0.0));
        Image.setBoarderBackgroundColorArray(mActivity.get(), color, 2, 5, color,
                new View[]{btnCancel_SMF, btnOk_SMF});
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
                hideKeyboard(mActivity.get());
                popBackStack();
                break;
            case R.id.btnOk_SMF:
                if (checkValidation()) {
                    hideKeyboard(mActivity.get());
                    String[] send = {mail};
                    String title = "יצירת קשר אפליקציה ";
                    String subject = "שם" + ":  " + etFirstName_SMF.getText().toString() + " " + etLastName_SMF.getText()
                            .toString() + "\n" + "כתובת דואר אלקטרוני" + ":  " + etMail_SMF.getText().toString() + "\n" +
                            "מידע" + ":  " + etDescription_SMF.getText().toString();
                    sendMail(send, title, subject);
                }
                break;


        }
    }

    public final void sendMail(final String[] _mailAddress, final String title, final String text) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, _mailAddress);
        i.putExtra(Intent.EXTRA_SUBJECT, title);
        i.putExtra(Intent.EXTRA_TEXT, text);
        mActivity.get().startActivity(Intent.createChooser(i, getResources().getString(R.string.send_mail_hint)));
    }

    @Override
    public void onRetryClick() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideKeyboard(mActivity.get());
    }

    private boolean checkValidation() {
        boolean isValid = true;

        if (TextUtils.isEmpty(etDescription_SMF.getText().toString())) {
            etDescription_SMF.setError(getResources().getString(R.string.sign_up_dialog_error_text));
            isValid = false;
        }
        if (TextUtils.isEmpty(etFirstName_SMF.getText().toString())) {
            etFirstName_SMF.setError(getResources().getString(R.string.sign_up_dialog_error_text));
            isValid = false;
        }
        if (TextUtils.isEmpty(etLastName_SMF.getText().toString())) {
            etLastName_SMF.setError(getResources().getString(R.string.sign_up_dialog_error_text));
            isValid = false;
        }
        if (!TextUtils.isEmpty(etMail_SMF.getText().toString())) {
            if (!Patterns.EMAIL_ADDRESS.matcher(etMail_SMF.getText().toString()).matches()) {
                etMail_SMF.setError(getResources().getString(R.string.sign_up_dialog_incorrect_email));
                isValid = false;
            }
        } else {
            etMail_SMF.setError(getResources().getString(R.string.sign_up_dialog_error_text));
            isValid = false;
        }

        return isValid;
    }

}
