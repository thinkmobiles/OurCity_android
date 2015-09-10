package com.crmc.ourcity.fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.DocumentsLoader;
import com.crmc.ourcity.rest.responce.events.Documents;
import com.crmc.ourcity.utils.Image;

/**
 * Created by SetKrul on 20.07.2015.
 */
public class WebViewFragment extends BaseFourStatesFragment implements LoaderManager.LoaderCallbacks<Documents> {

    private WebView mWebView;
    private View vUnderLine_WVF;
    private TextView tvTitle_WVF;
    private String link;
    private ProgressBar pbLoading;
    private String color;
    private String json;
    private String route;
    private boolean error = false;

    public static WebViewFragment newInstance(String _link, String _colorItem) {
        WebViewFragment mWebViewFragment = new WebViewFragment();
        Bundle args = new Bundle();
        args.putString(Constants.CONFIGURATION_KEY_LINK, _link);
        args.putString(Constants.CONFIGURATION_KEY_COLOR, _colorItem);
        mWebViewFragment.setArguments(args);
        return mWebViewFragment;
    }

    public static WebViewFragment newInstance(String _colorItem, String _requestJson, String _requestRoute) {
        WebViewFragment mWebViewFragment = new WebViewFragment();
        Bundle args = new Bundle();
        args.putString(Constants.CONFIGURATION_KEY_COLOR, _colorItem);
        args.putString(Constants.CONFIGURATION_KEY_JSON, _requestJson);
        args.putString(Constants.CONFIGURATION_KEY_ROUTE, _requestRoute);
        mWebViewFragment.setArguments(args);
        return mWebViewFragment;
    }

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        //noinspection ConstantConditions
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        link = getArguments().getString(Constants.CONFIGURATION_KEY_LINK);
        color = getArguments().getString(Constants.CONFIGURATION_KEY_COLOR);
        json = getArguments().getString(Constants.CONFIGURATION_KEY_JSON);
        route = getArguments().getString(Constants.CONFIGURATION_KEY_ROUTE);
    }

    @Override
    public void onViewCreated(final View _view, final Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
        loadUrl(link);
    }

    @Override
    public void onLoadFinished(Loader<Documents> _loader, Documents _data) {
        //String html = new HtmlFormatter(getActivity()).htmlForWebView(_data.documentData, "", "justify", "right");
        if (_data != null) {
            mWebView.loadDataWithBaseURL(null, "<meta name=\"viewport\" content=\"width=device-width\">" + _data.documentData, "text/html", "UTF-8", null);
            tvTitle_WVF.setText(_data.documentTitle);
            showContent();
        } else {
            showError("Server do not response");
        }
    }

    @Override
    public Loader<Documents> onCreateLoader(int _id, Bundle _args) {
        return new DocumentsLoader(getActivity(), _args);
    }

    @Override
    public void onLoaderReset(Loader<Documents> _loader) {
    }


    @Override
    public void onRetryClick() {
        error = false;
//        showContent();
        loadUrl(link);
    }


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initViews() {
        super.initViews();
        tvTitle_WVF = findView(R.id.tvTitle_WFV);
        mWebView = findView(R.id.webView_WVF);
        pbLoading = findView(R.id.pbLoading_WVF);
        vUnderLine_WVF = findView(R.id.vUnderLine_WVF);
        Image.init(Color.parseColor(color));
        vUnderLine_WVF.setBackgroundColor(Image.darkenColor(0.2));
        pbLoading.getIndeterminateDrawable().setColorFilter(Image.lighterColor(0.2), PorterDuff.Mode.SRC_IN);
        pbLoading.getProgressDrawable().setColorFilter(Image.darkenColor(0.2), PorterDuff.Mode.SRC_IN);
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDisplayZoomControls(true);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                pbLoading.setProgress(newProgress);
//                if (newProgress == 100){
//                    showContent();
//                }
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
        if (!TextUtils.isEmpty(_link) && _link.contains(".pdf")) {
//            String pdfLink = "http://dlcdnet.asus.com/pub/ASUS/mb/socket775/P5B/e2620_p5b.pdf";
//            if (pdfLink.substring(pdfLink.lastIndexOf(".") + 1).equals(""));
            mWebView.loadUrl("http://docs.google.com/gview?embedded=true&url=" + _link);
            tvTitle_WVF.setVisibility(View.GONE);
//            new DownloadFile().downloadPdf(getActivity(), _link);
//            popBackStack();
        } else if (!TextUtils.isEmpty(json)) {
            //mWebView.setInitialScale(100);
//            mWebView.getSettings().setMinimumFontSize(20);
            Bundle bundle = new Bundle();
            bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_JSON, json);
            bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_ROUTE, route);
            getLoaderManager().initLoader(Constants.LOADER_DOCUMENTS_ID, bundle, this);
        } else {
            tvTitle_WVF.setVisibility(View.GONE);
            mWebView.loadUrl(_link);
        }
    }


    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView _view, String _url) {
            if (_url.contains(".pdf") && !(_url.contains("docs.google.com"))) {
                _view.loadUrl("http://docs.google.com/gview?embedded=true&url=" + _url);

            } else {
                _view.loadUrl(_url);
            }
            return false;
        }


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (!error) {
                showContent();
            }
            pbLoading.setVisibility(View.VISIBLE);
        }

        public void onPageFinished(WebView _view, String _url) {
            if (error) {
                showError("Server do not response!");
            }
            pbLoading.setVisibility(View.GONE);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            pbLoading.setVisibility(View.GONE);
            if (isAdded()) {
                mWebView.loadUrl("about:blank");
            }
            error = true;
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
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
