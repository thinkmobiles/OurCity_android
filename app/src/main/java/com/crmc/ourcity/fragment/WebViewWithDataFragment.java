package com.crmc.ourcity.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.crmc.ourcity.R;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;

public class WebViewWithDataFragment extends BaseFourStatesFragment {

    public static WebViewWithDataFragment newInstance() {
        return new WebViewWithDataFragment();
    }

    @Override
    public void onViewCreated(final View _view, final Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
    }

    @Override
    protected void initViews() {
        super.initViews();
       // ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        configureActionBar(true, true);
        WebView mWebView = findView(R.id.webView_WVF);
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");
        //String imageUrl = "http://artkiev.com/blog/wp-content/uploads/2012/12/Android1.png";

//        String textWebView = getResources().getString(R.string.long_text);
//        String htmlWebView = new HtmlFormatter(getActivity()).htmlForWebView(textWebView, imageUrl, "justify", "right");
//        mWebView.loadDataWithBaseURL(null, htmlWebView, "text/html", "UTF-8", null);
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