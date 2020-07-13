package com.zjj.myview.touch;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;

import com.zjj.myview.R;
import com.zjj.myview.SinWaveView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * author:zhoujingjin
 * date:2020/7/13
 */
public class SinWaveActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sin_wave);
        final SinWaveView sinWaveView = findViewById(R.id.sin_wave_view);
        sinWaveView.post(new Runnable() {
            @Override
            public void run() {
                sinWaveView.setPhaseAngle();
//                WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
//                DisplayMetrics metrics = new DisplayMetrics();
//                windowManager.getDefaultDisplay().getMetrics(metrics);
//                ValueAnimator animator = ValueAnimator.ofInt(0, metrics.widthPixels);
//                animator.setDuration(10000);
//                animator.setInterpolator(new LinearInterpolator());
//                animator.setRepeatCount(ValueAnimator.INFINITE);
//                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        sinWaveView.setPhaseAngle((Integer) animation.getAnimatedValue());
//                    }
//                });
//                animator.start();
            }
        });
    }
}
