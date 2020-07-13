package com.zjj.myview.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * author:zhoujingjin
 * date:2020/7/5
 */
public class MyFrameLayout extends FrameLayout {
    private final String TAG = "MyFrameLayout";
    public MyFrameLayout(@NonNull Context context) {
        this(context,null);
    }

    public MyFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "onTouchEvent: ");
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG, "dispatchTouchEvent: ");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e(TAG, "onInterceptTouchEvent: ");
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "onInterceptTouchEvent:ACTION_DOWN");
                return true;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "onInterceptTouchEvent:ACTION_MOVE");
                return true;
        }
        return false;
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        Log.e(TAG, "setOnTouchListener: ");
        super.setOnTouchListener(l);
    }
}
