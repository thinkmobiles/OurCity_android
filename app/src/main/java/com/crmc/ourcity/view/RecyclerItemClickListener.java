package com.crmc.ourcity.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {


    private OnItemClickListener mListener;
    private GestureDetector mGestureDetector;


    public interface OnItemClickListener {
        void onItemClick(Context _context, View _view, int _position);
    }

    public RecyclerItemClickListener(Context _context, OnItemClickListener _listener) {
        mListener = _listener;
        mGestureDetector = new GestureDetector(_context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView _rv, MotionEvent _e) {
        View childView = _rv.findChildViewUnder(_e.getX(), _e.getY());
        if(childView != null && mListener != null && mGestureDetector.onTouchEvent(_e)) {
            mListener.onItemClick(_rv.getContext(), childView, _rv.getChildAdapterPosition(childView));
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView _rv, MotionEvent _e) {

    }
}
