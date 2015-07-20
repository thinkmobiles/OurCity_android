package com.crmc.ourcity.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.crmc.ourcity.R;
import com.crmc.ourcity.callback.LocationCallBack;
import com.crmc.ourcity.location.MyLocation;
import com.crmc.ourcity.model.LocationModel;
import com.crmc.ourcity.utils.HtmlFormater;

/**
 * Created by SetKrul on 17.07.2015.
 */
public class WebViewWithDataFragment extends BaseFragment {

    private MyLocation getLocation;

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
        getLocation();
    }

    public void getLocation() {
        LocationCallBack locationCallBack = new LocationCallBack() {
            @Override
            public void onSuccess(LocationModel modelLocation) {
                Toast.makeText(getActivity(), modelLocation.nameCity, Toast.LENGTH_SHORT).show();
                Log.d("TAG", modelLocation.nameCity);
            }

            @Override
            public void onFailure(boolean result) {
                if (!result) {
                    Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
                }
                Log.d("TAG", result + " result");
            }
        };
        new MyLocation(getActivity(), locationCallBack);
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
}