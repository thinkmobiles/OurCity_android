package com.crmc.ourcity.utils;

import android.content.Context;

import com.crmc.ourcity.R;

/**
 * Created by SetKrul on 20.07.2015.
 */
public class HtmlFormater {

    Context mContext;

    public HtmlFormater(Context context) {
        this.mContext = context;
    }

    /**
     * @param text text for view
     * @param urlImage address image
     * @param textAlign right, left, justify
     * @param imageAlign right, left
     * @return html text
     */
    public String htmlForWebView(String text, String urlImage, String textAlign, String imageAlign) {
        final int imageMargin = mContext.getResources().getDimensionPixelSize(R.dimen.msf_image_magrin);
        String htmlText = "<html><head></head><style>#right { text-align: right; } #" +
                "left { text-align: left; } #justify {text-align: justify} .left {float:left;margin: " + imageMargin
                + "px " + imageMargin + "px " + imageMargin + "px 0;} .right  {float: right; margin:" + imageMargin +
                "px 0 " + imageMargin + "px " + imageMargin + "px;}</style><body><div id=\"" + textAlign +
                "\"><p><img src=\"" + urlImage + "\" class=\"" + imageAlign + "\">" + text + "</p></div></body></html>";
        return htmlText;
    }
}
