package com.crmc.ourcity.ticker;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.crmc.ourcity.callback.OnListItemActionListener;
import com.crmc.ourcity.rest.responce.ticker.TickerModel;

import java.util.List;

/**
 * Created by klim on 21.07.15.
 */
public class Ticker extends TextView implements Animator.AnimatorListener, View.OnClickListener {
    private ObjectAnimator mAimator;
    private List<TickerModel> mData;
    private int count;
    private OnListItemActionListener onTickerActionListener;

    public Ticker(Context _context) {
        this(_context, null, 0);
    }

    public Ticker(Context _context, AttributeSet _attrs) {
        this(_context, _attrs, 0);
    }

    public Ticker(Context _context, AttributeSet _attrs, int _defStyleAttr) {
        super(_context, _attrs, _defStyleAttr);

        count = 0;

        DisplayMetrics metrics = _context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;

        setOnClickListener(this);
        mAimator = ObjectAnimator.ofFloat(this, "translationX", -1 * width, width);
        mAimator.setDuration(12000);
        mAimator.addListener(this);
        mAimator.setRepeatCount(ValueAnimator.INFINITE);

    }

    /**
     * instert real data when it will be ready
     * @param _data
     */
    public void setData(List<TickerModel> _data) {
        if (_data.size() > 0){
            mData = _data;
            setText(mData.get(0).title);
        }
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
    public void onAnimationStart(Animator _animation) {
        count = 0;
    }

    @Override
    public void onAnimationEnd(Animator _animation) {
    }

    @Override
    public void onAnimationCancel(Animator _animation) {
    }

    @Override
    public void onAnimationRepeat(Animator _animation) {
        if (count < mData.size() - 1) {
            count++;
        } else {
            count = 0;
        }
        setText(mData.get(count).title);
    }

    public void setOnTickerActionListener(OnListItemActionListener _listener) {
        onTickerActionListener = _listener;
    }

    @Override
    public void onClick(View v) {
        if (onTickerActionListener != null && mData != null && !mData.isEmpty()) {
            onTickerActionListener.onTickerAction(v, mData.get(count).link, mData.get(count).title);
        }
    }
}
