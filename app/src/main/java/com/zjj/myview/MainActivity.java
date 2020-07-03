package com.zjj.myview;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.Printer;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private ColorStackView colorStackView;
    private CircleProgressViewWithText circleProgress;
    private ShapeView shapeView;
    private LikeViewLayout like_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("TAG","onCreate");
        setContentView(R.layout.activity_main);
        Looper.getMainLooper().setMessageLogging(new Printer() {
            long currentTime = -1;

            @Override
            public void println(String x) {
                if (x.contains(">>>>> Dispatching to ")) {
                    currentTime = System.currentTimeMillis();
                    Log.e("TAG>>>>> Dispatching to",x);
                } else if (x.contains("<<<<< Finished to ")) {
                    long executeTime = System.currentTimeMillis() - currentTime;
                    // 原则上是 16 毫秒，一般没办法做到这么严格
                    if (executeTime > 60) {
                        Log.e("TAG", "主线程出现方法耗时");
                    }
                }
            }
        });
        /*like_view = findViewById(R.id.like_view);
        like_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                like_view.addLike();
            }
        });*/
//        colorStackView = findViewById(R.id.colorStackView);
//        circleProgress = findViewById(R.id.circleProgress);
//        shapeView = findViewById(R.id.shapeView);
//        colorStackView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                colorStackView.setClickable(false);
//                Direction direction = colorStackView.getDirection();
//                change(direction);
//            }
//        });
//        circleProgress.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                changeProgress();
//            }
//        });
//        shapeView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                changeShape();
//            }
//        });
    }

    private void changeShape() {
        CountDownTimer countDownTimer = new CountDownTimer(60*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                shapeView.changeShape();
            }

            @Override
            public void onFinish() {

            }
        };
        countDownTimer.start();
    }

    private void changeProgress() {
        circleProgress.setProgress(1.0f);
    }

    private void change(final Direction direction) {
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = (float) animation.getAnimatedValue();
                colorStackView.setProgress(progress);
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (direction==Direction.LEFT_TO_RIGHT){
                    colorStackView.setDirection(Direction.RIGHT_TO_LEFT);
                }else {
                    colorStackView.setDirection(Direction.LEFT_TO_RIGHT);
                }
                colorStackView.setClickable(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("Tag","onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Tag","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("Tag","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("Tag","onStop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Tag","onDestroy");

    }
}