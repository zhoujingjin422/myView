package com.zjj.myview.observer;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

/**
 * author:zhoujingjin
 * date:2020/7/4
 */
public class MyEditText extends EditText implements Observable {
    private List<Observer> observers = new ArrayList<>();
    public MyEditText(Context context) {
        this(context,null);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void registerObservable(Observer observer) {
        if (observer!=null){
            observers.add(observer);
        }
    }

    private String text;
    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        this.text = text.toString();
        notifyObserver();
    }

    @Override
    public void unRegisterObservable(Observer observer) {
        if (observer!=null){
            observers.remove(observer);
        }
    }

    @Override
    public void notifyObserver() {
        if (observers==null)
            return;
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).updateData(text);
        }
    }
}
