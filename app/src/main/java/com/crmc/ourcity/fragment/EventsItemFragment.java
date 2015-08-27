package com.crmc.ourcity.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crmc.ourcity.R;
import com.crmc.ourcity.callback.OnListItemActionListener;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.responce.events.Events;
import com.crmc.ourcity.utils.HtmlFormatter;
import com.crmc.ourcity.utils.Image;
import com.crmc.ourcity.utils.IntentUtils;

/**
 * Created by SetKrul on 16.07.2015.
 */
public class EventsItemFragment extends BaseFourStatesFragment implements View.OnClickListener {

    private Events mEvents;
    private TextView tvTitle;
    private TextView tvDate_Text;
    private TextView tvAddress_Text;
    private TextView tvEmail_Text;
    private TextView tvMobile_Text;
    private TextView tvPhone_Text;
    private TextView tvPrice_Text;
    private TextView tvDescription_Text;

    private LinearLayout llDate;
    private LinearLayout llAddress;
    private LinearLayout llEmail;
    private LinearLayout llMobile;
    private LinearLayout llPhone;
    private LinearLayout llPrice;
    private LinearLayout llDescription;

    private ImageView ivCallSkype;
    private ImageView ivSendMail;
    private ImageView ivLink;

    private WebView mWebView;
    private OnListItemActionListener mOnListItemActionListener;

    public static EventsItemFragment newInstance(Events _events) {
        EventsItemFragment mEventsItemFragment = new EventsItemFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.CONFIGURATION_KEY_EVENTS, _events);
        mEventsItemFragment.setArguments(args);
        return mEventsItemFragment;
    }

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        mEvents = getArguments().getParcelable(Constants.CONFIGURATION_KEY_EVENTS);
    }

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);
        try {
            mOnListItemActionListener = (OnListItemActionListener) _activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(_activity.toString() + " must implement OnListItemActionListener");
        }
    }

    @Override
    public void onDetach() {
        mOnListItemActionListener = null;
        super.onDetach();
    }

    @Override
    protected void initViews() {
        super.initViews();
        tvTitle = findView(R.id.tvTitle_EIF);
        tvDate_Text = findView(R.id.tvDate_Text_EIF);
        tvAddress_Text = findView(R.id.tvAddress_Text_EIF);
        tvEmail_Text = findView(R.id.tvEmail_Text_EIF);
        tvMobile_Text = findView(R.id.tvMobile_Text_EIF);
        tvPhone_Text = findView(R.id.tvPhone_Text_EIF);
        tvPrice_Text = findView(R.id.tvPrice_Text_EIF);
        tvDescription_Text = findView(R.id.tvDescription_Text_EIF);

        llDate = findView(R.id.llDate_EIF);
        llAddress = findView(R.id.llAddress_EIF);
        llEmail = findView(R.id.llEmail_EIF);
        llMobile = findView(R.id.llMobile_EIF);
        llPhone = findView(R.id.llPhone_EIF);
        llPrice = findView(R.id.llPrice_EIF);
        llDescription = findView(R.id.llDescription_EIF);

        ivCallSkype = findView(R.id.ivCallSkype_EIF);
        ivSendMail = findView(R.id.ivSendMail_EIF);
        ivLink = findView(R.id.ivLink_EIF);

        mWebView = findView(R.id.webView_EIF);
    }

    @Override
    public void onViewCreated(final View _view, final Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
        checkData(mEvents.title, tvTitle, tvTitle);
        checkData(mEvents.eventDateToMobileClient, tvDate_Text, llDate);
        checkData(mEvents.address, tvAddress_Text, llAddress);
        checkData(mEvents.email, tvEmail_Text, llEmail);
        checkData(mEvents.mobile, tvMobile_Text, llMobile);
        checkData(mEvents.phone, tvPhone_Text, llPhone);
        checkData(mEvents.price, tvPrice_Text, llPrice);
        checkData(mEvents.description, tvDescription_Text, llDescription);
        setWebView(mEvents.notes);
        setImage();
    }

    private void checkData(String _text, TextView _tvView, View _view){
        if (!TextUtils.isEmpty(_text)){
            _tvView.setText(_text);
        } else {
            _view.setVisibility(View.GONE);
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setWebView(String _html){
        if (!TextUtils.isEmpty(_html)) {
            mWebView.setWebViewClient(new MyWebViewClient());
            mWebView.getSettings().setJavaScriptEnabled(true);
            String htmlWebView = new HtmlFormatter(getActivity()).htmlForWebView(_html, "", "justify", "right");
            mWebView.loadDataWithBaseURL(null, htmlWebView, "text/html", "UTF-8", null);
        } else {
            mWebView.setVisibility(View.GONE);
            showContent();
        }
    }

    private void setImage(){
        if (!TextUtils.isEmpty(mEvents.phone)){
            ivCallSkype.setImageDrawable(Image.setDrawableImageColor(getActivity(), R.drawable.phone, Image
                    .darkenColor(0.2)));
        } else {
            ivCallSkype.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(mEvents.email)){
            ivSendMail.setImageDrawable(Image.setDrawableImageColor(getActivity(), R.drawable.mail, Image
                    .darkenColor(0.2)));
        } else {
            ivSendMail.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(mEvents.link)){
            ivLink.setImageDrawable(Image.setDrawableImageColor(getActivity(), R.drawable.link, Image
                    .darkenColor(0.2)));
        } else {
            ivLink.setVisibility(View.GONE);
        }
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        ivCallSkype.setOnClickListener(this);
        ivSendMail.setOnClickListener(this);
        ivLink.setOnClickListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_events_item;
    }

    @Override
    public void onRetryClick() {
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivCallSkype_EIF:
                try {
                    startActivity(Intent.createChooser(IntentUtils.getIntentCall(mEvents.phone), getResources().getString(R
                            .string.call_hint)));
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.app_no_call_client), Toast
                            .LENGTH_SHORT).show();
                }
                break;
            case R.id.ivSendMail_EIF:
                try {
                    startActivity(Intent.createChooser(IntentUtils.getIntentMail(mEvents.email), getResources().getString(R
                            .string.send_mail_hint)));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.app_no_mail_client), Toast
                            .LENGTH_SHORT).show();
                }
                break;
            case R.id.ivLink_EIF:
                mOnListItemActionListener.onEventsClickLinkAction(mEvents.link);
                break;
        }
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView _view, String _url) {
            _view.loadUrl(_url);
            return true;
        }

        public void onPageFinished(WebView _view, String _url) {
            showContent();
        }
    }
}
