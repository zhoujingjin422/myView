package com.zjj.myview;

import android.animation.Animator;
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
import android.view.View;

import androidx.annotation.Nullable;

public class QQStepView extends View {

    private String text="";
    private int outlineColor = Color.RED;
    private int inlineColor = Color.YELLOW;
    private int textColor = Color.BLACK;
    private int textSize = 15;
    private int borderWith = 20;
    private Paint outlinePaint;
    private Paint inlinePaint;
    private Paint textPaint;
    private int maxStep = 5000;
    public QQStepView(Context context) {
        this(context,null);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.QQStepView);
        if (typedArray!=null){
            text = typedArray.getString(R.styleable.QQStepView_text);
            outlineColor = typedArray.getColor(R.styleable.QQStepView_outlineColor, outlineColor);
            inlineColor = typedArray.getColor(R.styleable.QQStepView_inlineColor, inlineColor);
            textColor = typedArray.getColor(R.styleable.QQStepView_textColor,textColor);
            borderWith = (int) typedArray.getDimension(R.styleable.QQStepView_borderWidth,borderWith);
            textSize =  (int)typedArray.getDimension(R.styleable.QQStepView_textSize,textSize);
            maxStep = typedArray.getInteger(R.styleable.QQStepView_maxStep,maxStep);
            typedArray.recycle();
        }
        outlinePaint = new Paint();
        outlinePaint.setColor(outlineColor);
        outlinePaint.setStyle(Paint.Style.STROKE);//设置画圆弧的画笔的属性为描边(空心)
        outlinePaint.setAntiAlias(true);
        outlinePaint.setStrokeWidth(borderWith);
        outlinePaint.setStrokeCap(Paint.Cap.ROUND);
        inlinePaint = new Paint();
        inlinePaint.setColor(inlineColor);
        inlinePaint.setStyle(Paint.Style.STROKE);//设置画圆弧的画笔的属性为描边(空心)
        inlinePaint.setAntiAlias(true);
        inlinePaint.setStrokeWidth(borderWith);
        inlinePaint.setStrokeCap(Paint.Cap.ROUND);
        textPaint = new Paint();
        textPaint.setColor(textColor);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(textSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int with = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int min = Math.min(with, height);
        setMeasuredDimension(min,min);
    }
    private Rect rect = new Rect();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画外圆弧
        RectF rectF = new RectF(borderWith/2,borderWith/2,getHeight()-borderWith/2,getHeight()-borderWith/2);
        canvas.drawArc(rectF,135,270,false,outlinePaint);
        //画内圆弧
        canvas.drawArc(rectF,135,Float.parseFloat(text)*270f/maxStep,false,inlinePaint);
        //画文字
        textPaint.getTextBounds(text,0,text.length(),rect);
        Paint.FontMetricsInt fontMetricsInt = textPaint.getFontMetricsInt();//字体度量
        int descent = fontMetricsInt.descent;
        int top = fontMetricsInt.top;
        int bottom = fontMetricsInt.bottom;
        int leading = fontMetricsInt.leading;
        int ascent = fontMetricsInt.ascent;
        int dy = (fontMetricsInt.bottom-fontMetricsInt.top)/2-fontMetricsInt.bottom;
        canvas.drawText(text,getWidth()/2-rect.width()/2,getWidth()/2+dy,textPaint);
    }

    public void setCurrentStep(int currentStep) {
        ValueAnimator valueAnimator = ObjectAnimator.ofInt(0, currentStep);
        valueAnimator.setDuration(1500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                text = String.valueOf(value);
                invalidate();
            }
        });

        valueAnimator.start();
    }
}
