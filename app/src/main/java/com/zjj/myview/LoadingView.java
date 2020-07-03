package com.zjj.myview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class LoadingView extends LinearLayout {

    private CircleView circleViewLeft;
    private CircleView circleViewRight;
    private final long DURATION_TIME = 1500L;
    private float moveDistance = dp2px(15);
    private float moveDistanceZ = dp2px(15);
    public LoadingView(Context context) {
        this(context,null);
    }
    private boolean doAnimation = true;

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout();
        post(new Runnable() {
            @Override
            public void run() {
                startMoveAnimation(circleViewLeft,circleViewRight);
            }
        });
    }

    private float dp2px(int dp){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,getResources().getDisplayMetrics());
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(INVISIBLE);
        doAnimation = false;
        if (circleViewLeft!=null)
            circleViewLeft.clearAnimation();
        if (circleViewRight!=null)
            circleViewRight.clearAnimation();
    }

//    public void destroyView(){
//        doAnimation = false;
//        if (circleViewLeft!=null)
//            circleViewLeft.clearAnimation();
//        if (circleViewRight!=null)
//            circleViewRight.clearAnimation();
//    }
    private void startMoveAnimation(CircleView circleViewLeft,CircleView circleViewRight) {
        if (!doAnimation)
            return;
        //左边的圆缩放
        ObjectAnimator leftScaleX = ObjectAnimator.ofFloat(circleViewLeft, "scaleX", 0.65f, 1.0f,0.65f,0.3f,0.65f);
        ObjectAnimator leftScaleY = ObjectAnimator.ofFloat(circleViewLeft, "scaleY", 0.65f, 1.0f,0.65f,0.3f,0.65f);
        //左边的圆往左移动
        ObjectAnimator leftTranslationX = ObjectAnimator.ofFloat(circleViewLeft, "translationX", 0, moveDistance,2*moveDistance,moveDistance,0);
        //左边的圆往Z移动
        ObjectAnimator leftTranslationZ = ObjectAnimator.ofFloat(circleViewLeft, "translationZ", 0, moveDistanceZ,0,-moveDistanceZ,0);


        ObjectAnimator rightScaleX = ObjectAnimator.ofFloat(circleViewRight, "scaleX", 0.65f, 0.3f,0.65f,1.0f,0.65f);
        ObjectAnimator rightScaleY = ObjectAnimator.ofFloat(circleViewRight, "scaleY", 0.65f, 0.3f,0.65f,1.0f,0.65f);
        //右边的圆往左移动
        ObjectAnimator rightTranslationX = ObjectAnimator.ofFloat(circleViewRight, "translationX", 0, -moveDistance,-moveDistance*2,-moveDistance,0);
        //右边的圆往Z移动
        ObjectAnimator rightTranslationZ = ObjectAnimator.ofFloat(circleViewRight, "translationZ", 0, -moveDistanceZ,0,moveDistanceZ,0);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.setDuration(DURATION_TIME);
        animatorSet.playTogether(leftScaleX,leftScaleY,leftTranslationX,leftTranslationZ,rightScaleX,rightScaleY,rightTranslationX,rightTranslationZ);
        animatorSet.addListener(new AnimatorListenerAdapter(){
            @Override
            public void onAnimationEnd(Animator animation) {
                startMoveAnimation(LoadingView.this.circleViewLeft,LoadingView.this.circleViewRight);
            }
        });
        animatorSet.start();
    }
   /* private void startMoveAnimation(CircleView circleViewLeft,CircleView circleViewRight) {
        if (!doAnimation)
            return;
        //左边的圆缩放
        ObjectAnimator leftScaleX = ObjectAnimator.ofFloat(circleViewLeft, "scaleX", 0.5f, 1.0f);
        ObjectAnimator leftScaleY = ObjectAnimator.ofFloat(circleViewLeft, "scaleY", 0.5f, 1.0f);
        //左边的圆往左移动
        ObjectAnimator leftTranslationX = ObjectAnimator.ofFloat(circleViewLeft, "translationX", 0, moveDistance);
        //左边的圆往Z移动
        ObjectAnimator leftTranslationZ = ObjectAnimator.ofFloat(circleViewLeft, "translationZ", 0, moveDistanceZ);


         ObjectAnimator rightScaleX = ObjectAnimator.ofFloat(circleViewRight, "scaleX", 0.5f, 0.3f);
        ObjectAnimator rightScaleY = ObjectAnimator.ofFloat(circleViewRight, "scaleY", 0.5f, 0.3f);
        //右边的圆往左移动
        ObjectAnimator rightTranslationX = ObjectAnimator.ofFloat(circleViewRight, "translationX", 0, -moveDistance);
        //右边的圆往Z移动
        ObjectAnimator rightTranslationZ = ObjectAnimator.ofFloat(circleViewRight, "translationZ", 0, -moveDistanceZ);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(DURATION_TIME);
        animatorSet.playTogether(leftScaleX,leftScaleY,leftTranslationX,leftTranslationZ,rightScaleX,rightScaleY,rightTranslationX,rightTranslationZ);
        animatorSet.addListener(new AnimatorListenerAdapter(){
            @Override
            public void onAnimationEnd(Animator animation) {
                startMoveAnimation2(LoadingView.this.circleViewLeft,LoadingView.this.circleViewRight);
            }
        });
        animatorSet.start();
    }
    private void startMoveAnimation2(CircleView circleViewLeft,CircleView circleViewRight) {
        if (!doAnimation)
            return;
        //左边的圆缩放
        ObjectAnimator leftScaleX = ObjectAnimator.ofFloat(circleViewLeft, "scaleX", 1.0f, 0.5f);
        ObjectAnimator leftScaleY = ObjectAnimator.ofFloat(circleViewLeft, "scaleY", 1.0f, 0.5f);
        //左边的圆往左移动
        ObjectAnimator leftTranslationX = ObjectAnimator.ofFloat(circleViewLeft, "translationX", moveDistance, 2*moveDistance);
        //左边的圆往Z移动
        ObjectAnimator leftTranslationZ = ObjectAnimator.ofFloat(circleViewLeft, "translationZ", moveDistanceZ, 0);


        ObjectAnimator rightScaleX = ObjectAnimator.ofFloat(circleViewRight, "scaleX", 0.3f, 0.5f);
        ObjectAnimator rightScaleY = ObjectAnimator.ofFloat(circleViewRight, "scaleY", 0.3f, 0.5f);
        //右边的圆往左移动
        ObjectAnimator rightTranslationX = ObjectAnimator.ofFloat(circleViewRight, "translationX", -moveDistance, -moveDistance*2);
        //右边的圆往Z移动
        ObjectAnimator rightTranslationZ = ObjectAnimator.ofFloat(circleViewRight, "translationZ", -moveDistanceZ, 0);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(DURATION_TIME);
        animatorSet.playTogether(leftScaleX,leftScaleY,leftTranslationX,leftTranslationZ,rightScaleX,rightScaleY,rightTranslationX,rightTranslationZ);
        animatorSet.addListener(new AnimatorListenerAdapter(){
            @Override
            public void onAnimationEnd(Animator animation) {
                startMoveAnimation3(LoadingView.this.circleViewLeft,LoadingView.this.circleViewRight);
            }
        });
        animatorSet.start();
    }
    private void startMoveAnimation3(CircleView circleViewLeft,CircleView circleViewRight) {
        if (!doAnimation)
            return;
        //左边的圆缩放
        ObjectAnimator leftScaleX = ObjectAnimator.ofFloat(circleViewLeft, "scaleX", 0.5f, 0.3f);
        ObjectAnimator leftScaleY = ObjectAnimator.ofFloat(circleViewLeft, "scaleY", 0.5f, 0.3f);
        //左边的圆往左移动
        ObjectAnimator leftTranslationX = ObjectAnimator.ofFloat(circleViewLeft, "translationX",  2*moveDistance, moveDistance);
        //左边的圆往Z移动
        ObjectAnimator leftTranslationZ = ObjectAnimator.ofFloat(circleViewLeft, "translationZ", 0, -moveDistanceZ);


        ObjectAnimator rightScaleX = ObjectAnimator.ofFloat(circleViewRight, "scaleX", 0.5f, 1.0f);
        ObjectAnimator rightScaleY = ObjectAnimator.ofFloat(circleViewRight, "scaleY", 0.5f, 1.0f);
        //右边的圆往左移动
        ObjectAnimator rightTranslationX = ObjectAnimator.ofFloat(circleViewRight, "translationX", -moveDistance*2, -moveDistance);
        //右边的圆往Z移动
        ObjectAnimator rightTranslationZ = ObjectAnimator.ofFloat(circleViewRight, "translationZ", 0, moveDistanceZ);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(DURATION_TIME);
        animatorSet.playTogether(leftScaleX,leftScaleY,leftTranslationX,leftTranslationZ,rightScaleX,rightScaleY,rightTranslationX,rightTranslationZ);
        animatorSet.addListener(new AnimatorListenerAdapter(){
            @Override
            public void onAnimationEnd(Animator animation) {
                startMoveAnimation4(LoadingView.this.circleViewLeft,LoadingView.this.circleViewRight);
            }
        });
        animatorSet.start();
    }
    private void startMoveAnimation4(CircleView circleViewLeft,CircleView circleViewRight) {
        if (!doAnimation)
            return;
        //左边的圆缩放
        ObjectAnimator leftScaleX = ObjectAnimator.ofFloat(circleViewLeft, "scaleX", 0.3f, 0.5f);
        ObjectAnimator leftScaleY = ObjectAnimator.ofFloat(circleViewLeft, "scaleY", 0.3f, 0.5f);
        //左边的圆往左移动
        ObjectAnimator leftTranslationX = ObjectAnimator.ofFloat(circleViewLeft, "translationX",  moveDistance, 0);
        //左边的圆往Z移动
        ObjectAnimator leftTranslationZ = ObjectAnimator.ofFloat(circleViewLeft, "translationZ", -moveDistanceZ, 0);


        ObjectAnimator rightScaleX = ObjectAnimator.ofFloat(circleViewRight, "scaleX", 1.0f, 0.5f);
        ObjectAnimator rightScaleY = ObjectAnimator.ofFloat(circleViewRight, "scaleY", 1.0f, 0.5f);
        //右边的圆往左移动
        ObjectAnimator rightTranslationX = ObjectAnimator.ofFloat(circleViewRight, "translationX", -moveDistance, 0);
        //右边的圆往Z移动
        ObjectAnimator rightTranslationZ = ObjectAnimator.ofFloat(circleViewRight, "translationZ", moveDistanceZ, 0);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(DURATION_TIME);
        animatorSet.playTogether(leftScaleX,leftScaleY,leftTranslationX,leftTranslationZ,rightScaleX,rightScaleY,rightTranslationX,rightTranslationZ);
        animatorSet.addListener(new AnimatorListenerAdapter(){
            @Override
            public void onAnimationEnd(Animator animation) {
                startMoveAnimation(LoadingView.this.circleViewLeft,LoadingView.this.circleViewRight);
            }
        });
        animatorSet.start();
    }*/
    private void initLayout() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.loading_layout, null);
        circleViewLeft = view.findViewById(R.id.circle_view_left);
        circleViewRight = view.findViewById(R.id.circle_view_right);
        addView(view);
    }
}
