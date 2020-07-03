package com.zjj.myview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.Nullable;

public class CircleProgressViewWithText extends View {
    private int outlineColor = Color.RED;
    private int inlineColor = Color.YELLOW;
    private int textColor = Color.BLACK;
    private float textSize = sp2px(15);
    private int borderWith = 20;
    private Paint outlinePaint;
    private Paint inlinePaint;
    private Paint textPaint;
    private String text="";
    public CircleProgressViewWithText(Context context) {
        this(context,null);
    }

    public CircleProgressViewWithText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleProgressViewWithText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressViewWithText);
        text = typedArray.getString(R.styleable.CircleProgressViewWithText_CircleProgressViewText);
        outlineColor = typedArray.getColor(R.styleable.CircleProgressViewWithText_CircleProgressViewOutlineColor, outlineColor);
        inlineColor = typedArray.getColor(R.styleable.CircleProgressViewWithText_CircleProgressViewInlineColor, inlineColor);
        textColor = typedArray.getColor(R.styleable.CircleProgressViewWithText_CircleProgressViewTextColor,textColor);
        borderWith = (int) typedArray.getDimension(R.styleable.CircleProgressViewWithText_CircleProgressViewBorderWidth,borderWith);
        textSize =  (int)typedArray.getDimension(R.styleable.CircleProgressViewWithText_CircleProgressViewTextSize,textSize);
        typedArray.recycle();
        outlinePaint = new Paint();
        outlinePaint.setAntiAlias(true);
        outlinePaint.setColor(outlineColor);
        outlinePaint.setStrokeWidth(borderWith);
        outlinePaint.setStrokeCap(Paint.Cap.ROUND);
        outlinePaint.setStyle(Paint.Style.STROKE);

        inlinePaint = new Paint();
        inlinePaint.setAntiAlias(true);
        inlinePaint.setColor(inlineColor);
        inlinePaint.setStrokeWidth(borderWith);
        inlinePaint.setStrokeCap(Paint.Cap.ROUND);
        inlinePaint.setStyle(Paint.Style.STROKE);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(textSize);
        textPaint.setColor(textColor);
    }
    private float sp2px(int sp){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
       setMeasuredDimension( MeasureSpec.getSize(widthMeasureSpec),MeasureSpec.getSize(heightMeasureSpec));
    }

    private float progress = 0.0f;
    @Override
    protected void onDraw(Canvas canvas) {
        //画圆
        canvas.drawCircle(getHeight()/2,getHeight()/2,getHeight()/2-borderWith/2,outlinePaint);
        //画弧
        RectF rectF = new RectF(borderWith/2,borderWith/2,getWidth()-borderWith/2,getHeight()-borderWith/2);
        canvas.drawArc(rectF,270,360*progress,false,inlinePaint);
        //画文字
        Rect rect = new Rect();
        text = (int)(100*progress)+"%";
        textPaint.getTextBounds(text,0,text.length(),rect);
        Paint.FontMetricsInt fontMetricsInt = textPaint.getFontMetricsInt();
        int baseLine = getHeight()/2+(fontMetricsInt.bottom-fontMetricsInt.top)/2-fontMetricsInt.bottom;
        canvas.drawText(text,getWidth()/2-rect.width()/2,baseLine,textPaint);
    }

    public void setProgress(float v) {
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, v);
        valueAnimator.setDuration(1500);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                progress = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();

    }
}
