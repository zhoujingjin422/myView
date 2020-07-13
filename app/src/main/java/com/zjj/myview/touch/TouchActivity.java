package com.zjj.myview.touch;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.zjj.myview.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * author:zhoujingjin
 * date:2020/7/5
 */
public class TouchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.touch_activity);
        findViewById(R.id.my_frame_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("MyFrameLayout", "onClick: ");
            }
        });
        findViewById(R.id.my_frame_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("MyView", "onClick: ");
            }
        });
    }
}
