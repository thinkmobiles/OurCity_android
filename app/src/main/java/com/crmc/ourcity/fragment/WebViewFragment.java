package com.crmc.ourcity.fragment;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.crmc.ourcity.R;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;

/**
 * Created by SetKrul on 20.07.2015.
 */
public class WebViewFragment extends BaseFourStatesFragment {

    private WebView mWebView;

    public static WebViewFragment newInstance() {
        return new WebViewFragment();
    }

    @Override
    public void onViewCreated(final View _view, final Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
    }

    @Override
    protected void initViews() {
        super.initViews();
        mWebView = findView(R.id.webView);
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("http://google.com.ua");
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