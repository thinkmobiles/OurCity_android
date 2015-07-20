package com.crmc.ourcity.fragment;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.crmc.ourcity.R;

/**
 * Created by SetKrul on 20.07.2015.
 */
public class WebViewFragment extends BaseFragment {

    private WebView mWebView;

    public static WebViewFragment newInstance() {
        return new WebViewFragment();
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mWebView = findView(R.id.webView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("http://google.com.ua");
        mWebView.setWebViewClient(new MyWebViewClient());
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
    protected int getLayoutResource() {
        return R.layout.fragment_webview;
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}