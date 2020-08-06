package com.zjj.myview.annotation;

import android.util.Log;

/**
 * author:zhoujingjin
 * date:2020/8/6
 */
public class User {
    @ClickGap(name = "wgg",age = 31)
    public void show(){
        Log.e("TAG","show");
    }
}
