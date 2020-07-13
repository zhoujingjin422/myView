package com.zjj.myview.observer;

import android.content.Context;
import android.util.AttributeSet;


import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * author:zhoujingjin
 * date:2020/7/4
 */
public class MyTextView extends AppCompatTextView implements Observer {
    public MyTextView(Context context) {
        this(context,null);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void updateData(String text) {
        setText(text);
    }
}
