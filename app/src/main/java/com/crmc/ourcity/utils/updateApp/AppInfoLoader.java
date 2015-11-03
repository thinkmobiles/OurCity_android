package com.crmc.ourcity.utils.updateApp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AppInfoLoader extends BaseLoader<AppInfo> {

    private String url;
    private static final String TAG = "AppInfoLoader";

    public AppInfoLoader(Context _context, Bundle args) {
        super(_context);
        url = args.getString(VersionManagerConstants.BUNDLE_URL);


    }


    @Override
    public AppInfo loadInBackground() {
        AppInfoParserHelper helper = null;
        int statusCode;
        DefaultHttpClient client = new DefaultHttpClient();
        HttpResponse httpResponse = null;


        try {
            HttpGet httpget = new HttpGet(url);
            httpResponse = client.execute(httpget);
            statusCode = httpResponse.getStatusLine().getStatusCode();

            if (statusCode == HttpStatus.SC_OK) {
                helper = new AppInfoParserHelper(Jsoup.connect(url).get());
                helper.setStatusCode(statusCode);
            }

        } catch (Exception e) {
            Log.e(TAG, e.toString());
            helper = null;
        }
//         try {
//             helper = new AppInfoParserHelper(Jsoup.connect(url).get());
//         } catch (Exception e) {
//             helper = null;
//         }

        return helper != null ? helper.getAppInfo() : null;
    }

    @Override
    public void deliverResult(AppInfo _data) {
        super.deliverResult(_data);
    }

    @Override
    public void registerListener(int id, OnLoadCompleteListener<AppInfo> listener) {
        super.registerListener(id, listener);
    }
}
