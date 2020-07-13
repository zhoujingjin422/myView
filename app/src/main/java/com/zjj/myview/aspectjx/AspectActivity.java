package com.zjj.myview.aspectjx;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zjj.myview.R;

/**
 * @author : "zhoujingjin"
 * @timer : 2020/7/3
 */
public class AspectActivity extends AppCompatActivity {
    private static final String TAG = "zhoujingjin";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aspect);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goNex();
            }
        });

    }

    @CheckNet
    private void goNex() {
        //如果那边给拦截了，这里就不会打印
        Log.e(TAG, "goNex");
    }
}
