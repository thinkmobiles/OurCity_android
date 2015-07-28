package com.crmc.ourcity.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

/**
 * Created by SetKrul on 28.07.2015.
 */
public abstract class BaseLoader<T> extends AsyncTaskLoader<T> {

    private T mData;
    public BaseLoader(final Context context) {
        super(context);
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
    public void deliverResult(T data) {
        mData = data;
        if (isStarted()) {
            super.deliverResult(data);
        }
    }
}
