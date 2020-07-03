package com.zjj.myview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

public class SplashLayout  extends View {
    //七种颜色
    private int[] colors ={
            R.color.color1,
            R.color.color2,
            R.color.color3,
            R.color.color4,
            R.color.color5,
            R.color.color6,
            R.color.color7
    };
    //画笔
    private Paint mPaint;
    private int with;//控件宽
    private int height;//控件高
    private int backColor = Color.WHITE;
    private int circleRadius;//七个起始圆的半径 宽的1/10；
    private PointF pointCenter;//中心点
    private int distance ;//中心点到七个圆中心的距离，宽的1/4
    private AnimationType animationType = new rotationType();
    private int bigCircleRadius;
    public SplashLayout(Context context) {
        this(context,null);
    }

    public SplashLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SplashLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        with = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        circleRadius = with/20;
        pointCenter = new PointF(with/2,height/2);
        distance = with/4;
        bigCircleRadius = (int) Math.sqrt((Math.pow(with,2)+Math.pow(height,2)))/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画七个圆
        //确定七个圆的位置
        if (animationType instanceof rotationType){
            animationType.onDraw(canvas);
        }
        if (animationType instanceof PolymerizationType){
            animationType.onDraw(canvas);
        }
        if (animationType instanceof ExpandType){
            animationType.onDraw(canvas);
        }
        //旋转起来
    }
    private float angle;
    public abstract class AnimationType{
        public abstract void onDraw(Canvas canvas);
    }
    //旋转
    public class rotationType extends AnimationType{
        ValueAnimator valueAnimator;
        @Override
        public void onDraw(final Canvas canvas) {
            canvas.drawColor(backColor);//画一个背景
            if (valueAnimator==null){
                valueAnimator = ObjectAnimator.ofFloat(0, (float) (2 * Math.PI));
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        angle = (float) animation.getAnimatedValue();
                        invalidate();
                    }
                });
                valueAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animationType = new PolymerizationType();
                    }
                });
                valueAnimator.setDuration(3000);
                valueAnimator.setInterpolator(new LinearInterpolator());//匀速
                valueAnimator.start();
            }
            final double v = 2 * Math.PI / colors.length;//七个圆把2π分为一个角度
            for (int i = 0; i < colors.length; i++) {
                float x = (float) (pointCenter.x+distance*Math.cos(v*i+angle));
                float y = (float) (pointCenter.y+distance*Math.sin(v*i+angle));
                Log.e("TAG", "x: "+x+ "y: "+y);
                mPaint.setColor(getResources().getColor(colors[i]));
                canvas.drawCircle(x,y,circleRadius,mPaint);
            }
        }
    }
    //集合
    public class PolymerizationType extends AnimationType {
        ValueAnimator valueAnimator;
        private float distanceNow;

        @Override
        public void onDraw(Canvas canvas) {
            if (valueAnimator == null) {
                valueAnimator = ObjectAnimator.ofFloat(distance, 0);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        distanceNow = (float) animation.getAnimatedValue();
                        invalidate();
                    }
                });
                valueAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animationType=  new ExpandType();
                    }
                });
                valueAnimator.setDuration(1500);
                valueAnimator.setInterpolator(new AnticipateInterpolator());//匀速
                valueAnimator.start();
            }
            canvas.drawColor(Color.WHITE);
            final double v = 2 * Math.PI / colors.length;//七个圆把2π分为一个角度
            for (int i = 0; i < colors.length; i++) {
                float x = (float) (pointCenter.x + distanceNow * Math.cos(v * i+angle));
                float y = (float) (pointCenter.y + distanceNow * Math.sin(v * i+angle));
                Log.e("TAG", "x: " + x + "y: " + y);
                mPaint.setColor(getResources().getColor(colors[i]));
                canvas.drawCircle(x, y, circleRadius, mPaint);
            }
        }


    }
    //扩散
    public class ExpandType extends AnimationType {
        ValueAnimator valueAnimator;
        float with;
        @Override
        public void onDraw(Canvas canvas) {
            if (valueAnimator==null){
                valueAnimator = ObjectAnimator.ofFloat(bigCircleRadius, 0);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        with = (float) animation.getAnimatedValue();
                        Log.e("TAG", "with: "+with);
                        invalidate();
                    }
                });
                valueAnimator.setDuration(1500);
                valueAnimator.start();
            }
            mPaint.setColor(Color.WHITE);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(with);
            canvas.drawCircle(pointCenter.x,pointCenter.y,bigCircleRadius-with/2,mPaint);
        }
    }
}
