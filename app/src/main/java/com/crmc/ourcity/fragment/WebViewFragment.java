package com.crmc.ourcity.fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.crmc.ourcity.R;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;

/**
 * Created by SetKrul on 20.07.2015.
 */
public class WebViewFragment extends BaseFourStatesFragment {

    private WebView mWebView;
    private String link;
    private ProgressBar pbLoading;
    private static final String CONFIGURATION_KEY = "CONFIGURATION_KEY";

    public static WebViewFragment newInstance(String _link) {
        WebViewFragment mWebViewFragment = new WebViewFragment();
        Bundle args = new Bundle();
        args.putString(CONFIGURATION_KEY, _link);
        mWebViewFragment.setArguments(args);
        return mWebViewFragment;
    }

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        link = getArguments().getString(CONFIGURATION_KEY);
    }

    @Override
    public void onViewCreated(final View _view, final Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
        loadUrl(link);
    }

    @Override
    public void onRetryClick() {
        loadUrl(link);
    }


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initViews() {
        super.initViews();

        mWebView = findView(R.id.webView_WVF);
        pbLoading = findView(R.id.pbLoading_WVF);

        pbLoading.getIndeterminateDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
        pbLoading.getProgressDrawable().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);

        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setInitialScale(100);
        mWebView.getSettings().setSupportZoom(true);
//        mWebView.getSettings().setBuiltInZoomControls(true);
//        mWebView.getSettings().setDisplayZoomControls(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDisplayZoomControls(true);
        mWebView.getSettings().setDomStorageEnabled(true);

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                pbLoading.setProgress(newProgress);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mWebView = null;
    }

    @Override
    protected void setListeners() {
        super.setListeners();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_webview;
    }

    private void loadUrl(String _link) {
        if (_link.contains(".pdf")) {
//            String pdfLink = "http://dlcdnet.asus.com/pub/ASUS/mb/socket775/P5B/e2620_p5b.pdf";
//            if (pdfLink.substring(pdfLink.lastIndexOf(".") + 1).equals(""));
            mWebView.loadUrl("http://docs.google.com/gview?embedded=true&url=" + _link);
//            new DownloadFile().downloadPdf(getActivity(), _link);
//            popBackStack();
        }
//         else if (/*html text*/){
//            _link = new HtmlFormatter(getActivity()).htmlForWebView(_link, "", "justify", "right");
//            mWebView.loadDataWithBaseURL(null, _link, "text/html", "UTF-8", null);
//        }
        mWebView.loadUrl(_link);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView _view, String _url) {
            _view.loadUrl(_url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            showContent();
            pbLoading.setVisibility(View.VISIBLE);
        }

        public void onPageFinished(WebView _view, String _url) {
            pbLoading.setVisibility(View.GONE);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            pbLoading.setVisibility(View.GONE);
        }


        @Override
        public void onReceivedSslError(WebView view, @NonNull SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
            String message = "SSL error: \n";
            switch (error.getPrimaryError()) {
                case SslError.SSL_UNTRUSTED:
                    message += "The certificate authority is not trusted.";
                    break;
                case SslError.SSL_EXPIRED:
                    message += "The certificate has expired.";
                    break;
                case SslError.SSL_IDMISMATCH:
                    message += "The certificate Hostname mismatch.";
                    break;
                case SslError.SSL_NOTYETVALID:
                    message += "The certificate is not yet valid.";
                    break;
            }
            pbLoading.setVisibility(View.GONE);
            showError(message);
            handler.proceed();
        }
    }
}