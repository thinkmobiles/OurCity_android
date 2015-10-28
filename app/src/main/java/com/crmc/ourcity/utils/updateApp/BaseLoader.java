package com.crmc.ourcity.utils.updateApp;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by SetKrul on 28.07.2015.
 */
public abstract class BaseLoader<T> extends AsyncTaskLoader<T> {

    private T mData;

    public BaseLoader(final Context _context) {
        super(_context);
    }

    @Override
    protected void onStartLoading() {
        if (mData == null) {
            forceLoad();
        } else {
            deliverResult(mData);
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    public void deliverResult(T _data) {
        mData = _data;
        if (isStarted()) {
            super.deliverResult(_data);
        }
    }

    protected String changeResidentIdInJson(String _json, int currentResidentId) {
        if (_json.contains("ResidentId") || _json.contains("residentId")) {
            String re1 = ".*?"; // Non-greedy match on filler
            String re2 = "(\\d+)"; // Integer Number 1
            Pattern p = Pattern.compile(re1 + re2, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
            Matcher m = p.matcher(_json);
            if (m.find()) {
                _json = _json.replaceAll(m.group(1), String.valueOf(currentResidentId));
            }
        }
        return _json;
    }

}
