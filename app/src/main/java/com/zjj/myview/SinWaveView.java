package com.zjj.myview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

/**
 * author:zhoujingjin
 * date:2020/7/13
 * 正玄线
 */
public class SinWaveView extends View {
    private int width;
    private int height;

    private Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path linePath;
    private Path linePath1;

    private double amplitude;
    private double default_amplitude;

    //角频率， 控制周期
    private double angularFrequency = 4 * 1.0f / 2;
    //初次相位角,
    private double phaseAngle = 0 * Math.PI / 180 + Math.PI / 2 * -1;

    public SinWaveView(Context context) {
        this(context, null);
    }

    public SinWaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        amplitude = height / 4-2;
        default_amplitude = height / 4-2;
        setMeasuredDimension(width, height);
    }

    public SinWaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        linePaint.setColor(Color.parseColor("#23a393"));
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(4);
        linePath = new Path();
        linePath1 = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        linePath.reset();
        linePath1.reset();

        linePath.moveTo(0, height/2);
        linePath1.moveTo(0, height/2);
        linePath.lineTo(width/8, height/2);
        linePath1.lineTo(width/8, height/2);
        for (int i = width/8; i < width*7/8; i++) {
            double angle = i * 1F / width * 2 * Math.PI;
            double y = amplitude * Math.sin(angle * angularFrequency + phaseAngle);
            linePath.lineTo(i, (float) ( height / 2+y));
            linePath1.lineTo(i, (float) (-y + height / 2));
        }
        linePath.lineTo(width, height/2);
        linePath1.lineTo(width, height/2);
        canvas.drawPath(linePath, linePaint);
        canvas.drawPath(linePath1, linePaint);
    }

    public void setAmplitude(int progress) {
        amplitude = progress * 1.0F / 100 * height / 2;
        invalidate();
    }

    public void setAngularFrequency(int progress) {
        angularFrequency = progress * 1.0F / 4;
        invalidate();
    }

    public void setPhaseAngle() {
        ValueAnimator animator = ValueAnimator.ofInt(width, width/8);
        animator.setDuration(2000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer animatedValue = (Integer) animation.getAnimatedValue();
                phaseAngle = animatedValue * Math.PI / 180 + Math.PI / 2 * -1;
                amplitude = default_amplitude*animatedValue/width;
                invalidate();
            }
        });

        animator.start();

    }
}
