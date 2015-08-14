package com.crmc.ourcity.utils;

import android.content.Context;

import com.crmc.ourcity.R;

/**
 * Created by SetKrul on 20.07.2015.
 */
public class HtmlFormatter {

    Context mContext;

    public HtmlFormatter(Context _context) {
        this.mContext = _context;
    }

    /**
     * @param _text _text for view
     * @param _urlImage address image
     * @param _textAlign right, left, justify
     * @param _imageAlign right, left
     * @return html _text
     */
    public String htmlForWebView(String _text, String _urlImage, String _textAlign, String _imageAlign) {
        final int imageMargin = mContext.getResources().getDimensionPixelSize(R.dimen.msf_image_magrin);
        String htmlText = "<html><head></head><style>#right { _text-align: right; } #" +
                "left { _text-align: left; } #justify {_text-align: justify} .left {float:left;margin: " + imageMargin
                + "px " + imageMargin + "px " + imageMargin + "px 0;} .right  {float: right; margin:" + imageMargin +
                "px 0 " + imageMargin + "px " + imageMargin + "px;}</style><body><div id=\"" + _textAlign +
                "\"><p><img src=\"" + _urlImage + "\" class=\"" + _imageAlign + "\">" + _text + "</p></div></body></html>";
        return htmlText;
    }
}
