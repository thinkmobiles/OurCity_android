package com.crmc.ourcity.fragment;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.crmc.ourcity.R;
import com.crmc.ourcity.callback.LocationCallBack;
import com.crmc.ourcity.location.GetLocation;
import com.crmc.ourcity.model.ModelLocation;

/**
 * Created by SetKrul on 14.07.2015.
 */
public class MainMenuFragment extends BaseFragment {

    public static MainMenuFragment newInstance() {
        return new MainMenuFragment();
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        WebView mWebView = findView(R.id.webView);
        WebSettings settings = mWebView.getSettings();
        settings.setDefaultTextEncodingName("utf-8");
        String imageUrl = "http://artkiev.com/blog/wp-content/uploads/2012/12/Android1.png";
        String textWebView = getResources().getString(R.string.long_text);
        String htmlWebView = htmlForWebView(textWebView, imageUrl, "justify", "right", 10);
        mWebView.loadDataWithBaseURL(null, htmlWebView, "text/html", "UTF-8", null);
        getLocation();
    }

    public void getLocation(){
        LocationCallBack location = new LocationCallBack() {
            @Override
            public void onSuccess(ModelLocation modelLocation) {
                Toast.makeText(getActivity(), modelLocation.nameCity, Toast.LENGTH_SHORT).show();
                //Log.d("TAG", modelLocation.nameCity);
            }

            @Override
            public void onFailure(boolean result) {
                if (!result){
                    Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
                }
                //.d("TAG", result + " result");
            }
        };
        new GetLocation(getActivity(), location);
    }

    /*
     * @param text  - text for view
     * @param urlImage - address image
     * @param textAlign - right, left, justify
     * @param imageAlign - right, left
     * @param imageMargin - number margin
     * @return - html text
     */
    public String htmlForWebView(String text, String urlImage, String textAlign, String imageAlign, int imageMargin) {
        String htmlText = "<html><head></head><style>#right { text-align: right; } #" +
                "left { text-align: left; } #justify {text-align: justify} .left {float:left;margin: " + imageMargin
                + "px " + imageMargin + "px " + imageMargin + "px 0;} .right  {float: right; margin:" + imageMargin +
                "px 0 " + imageMargin + "px " + imageMargin + "px;}</style><body><div id=\"" + textAlign +
                "\"><p><img src=\"" + urlImage + "\" class=\"" + imageAlign + "\">" + text + "</p></div></body></html>";
        return htmlText;
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
        return R.layout.main_menu_fragment;
    }

}
