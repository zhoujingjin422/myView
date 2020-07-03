package com.zjj.myview;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author : "zhoujingjin"
 * @timer : 2020/7/3
 */
public class LikeActivity extends AppCompatActivity {

    private LikeViewLayout like;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);
        like = findViewById(R.id.like_view);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                like.addLike();
            }
        });
    }
}
