package com.crmc.ourcity.ticker;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by klim on 21.07.15.
 */
public class Ticker extends TextView implements Animator.AnimatorListener {
    private ObjectAnimator mAimator;
    private List<String> mData;
    private int count;

    public Ticker(Context context) {
        this(context, null, 0);
    }

    public Ticker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Ticker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        count = 0;

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;

        mAimator = ObjectAnimator.ofFloat(this, "translationX", -1 * width, width);
        mAimator.setDuration(7000);
        mAimator.addListener(this);
        mAimator.setRepeatCount(ValueAnimator.INFINITE);

    }

    /**
     * instert real data when it will be ready
     * @param _data
     */
    public void setData(List<String> _data) {
        List tempTicker = new ArrayList();
        tempTicker.add("test string1 for ticker");
        tempTicker.add("test string2 for ticker");
        tempTicker.add("test string3 for ticker");
        tempTicker.add("test string4 for ticker");

        mData = tempTicker;
        setText(mData.get(0).toString());
    }

    public void startAnimation() {
        if (mData != null) {
            mAimator.start();
        } else {
            Log.e("Ticker", "No data!!!");
        }
    }

    public void stopAnimation() {
        mAimator.cancel();
    }

    @Override
    public void onAnimationStart(Animator animation) {
        count = 0;
    }

    @Override
    public void onAnimationEnd(Animator animation) {

    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {
        if (count < mData.size() - 1) {
            count++;
        } else {
            count = 0;
        }
        setText(mData.get(count));
    }
}
