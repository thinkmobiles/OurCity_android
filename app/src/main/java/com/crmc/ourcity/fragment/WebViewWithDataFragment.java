package com.crmc.ourcity.fragment;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.utils.HtmlFormater;

/**
 * Created by SetKrul on 17.07.2015.
 */
public class WebViewWithDataFragment extends BaseFourStatesFragment {

    public static WebViewWithDataFragment newInstance() {
        return new WebViewWithDataFragment();

    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        WebView mWebView = findView(R.id.webView);
        WebSettings settings = mWebView.getSettings();
        settings.setDefaultTextEncodingName("utf-8");
        String imageUrl = "http://artkiev.com/blog/wp-content/uploads/2012/12/Android1.png";
        String textWebView = getResources().getString(R.string.long_text);
        String htmlWebView = new HtmlFormater(getActivity()).htmlForWebView(textWebView, imageUrl, "justify", "right");
        mWebView.loadDataWithBaseURL(null, htmlWebView, "text/html", "UTF-8", null);
    }

    @Override
    protected void initViews() {
        super.initViews();
    }

    @Override
    protected void setListeners() {
        super.setListeners();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_webview;
    }

    @Override
    public void onRetryClick() {

    }
}