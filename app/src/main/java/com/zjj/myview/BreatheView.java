package com.zjj.myview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author : "zhoujingjin"
 * @timer : 2020/7/15
 */
public class BreatheView extends View {


    private Paint paint;
    private Paint paint1;
    private Paint paint2;
    private int mWith;
    private float space = dp2px(20);
    private float space_now;
    private ValueAnimator valueAnimator;

    public BreatheView(Context context) {
        this(context,null);
    }

    public BreatheView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }
    private float dp2px(int dp){
        return  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,getResources().getDisplayMetrics());
    }
    private float sp2px(int px){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,px,getResources().getDisplayMetrics());
    }
    public BreatheView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getResources().getColor(R.color.e54525));
        paint.setAntiAlias(true);

        paint1 = new Paint();
        paint1.setStyle(Paint.Style.FILL);
        paint1.setColor(getResources().getColor(R.color.white30));
        paint1.setAntiAlias(true);
        paint2 = new Paint();
        paint2.setColor(Color.WHITE);
        paint2.setTypeface(Typeface.DEFAULT_BOLD);//粗体
        paint2.setTextSize(sp2px(26));
        paint2.setAntiAlias(true);
        post(new Runnable() {
            @Override
            public void run() {
                valueAnimator = ObjectAnimator.ofFloat(0, space, 0);
                valueAnimator.setDuration(1500);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {


                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        space_now = (float) animation.getAnimatedValue();
                        invalidate();
                    }
                });
                valueAnimator.setRepeatCount(-1);
                valueAnimator.start();
            }
        });
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility==INVISIBLE||visibility==GONE){
            if (valueAnimator!=null)
                valueAnimator.pause();
        }else {
            if (valueAnimator!=null)
                valueAnimator.resume();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int with = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        mWith = Math.min(with,height);
        setMeasuredDimension(mWith, mWith);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(mWith/2,mWith/2,mWith/2-space_now/2,paint1);
        canvas.drawCircle(mWith/2,mWith/2,mWith/2-space/2,paint);
        String str = "GO";
        Rect rect = new Rect();
        paint2.getTextBounds(str,0,str.length(),rect);
        Paint.FontMetricsInt fontMetricsInt = paint2.getFontMetricsInt();
        int height = mWith/2+(fontMetricsInt.bottom-fontMetricsInt.top)/2-fontMetricsInt.bottom;
        canvas.drawText(str,mWith/2-rect.width()/2,height,paint2);
    }
}
