package com.zjj.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;


public class TextView extends View {

    private String text;
    private int color = Color.BLACK;
    private int textSize = 15;
    private Paint mPaint;
    public TextView(Context context) {
        this(context,null);
    }

    public TextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.zjjText);
        if (typedArray!=null){
            text = typedArray.getString(R.styleable.zjjText_zjjText);
            color = typedArray.getColor(R.styleable.zjjText_zjjTextColor, color);
            textSize = typedArray.getDimensionPixelSize(R.styleable.zjjText_zjjTextSize,textSize);
            typedArray.recycle();
        }
        mPaint = new Paint();
        mPaint.setTextSize(textSize);
        mPaint.setColor(color);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int modeWith = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int with = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (modeWith==MeasureSpec.AT_MOST){
            Rect bounds = new Rect();
            mPaint.getTextBounds(text,0,text.length(),bounds);
            with = bounds.width()+getPaddingLeft()+getPaddingRight();
        }
        if (modeHeight==MeasureSpec.AT_MOST){
            Rect bounds = new Rect();
            mPaint.getTextBounds(text,0,text.length(),bounds);
            height = bounds.height()+getPaddingTop()+getPaddingBottom();
        }
        setMeasuredDimension(with,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();
        int baseLine = getHeight()/2+(fontMetricsInt.bottom-fontMetricsInt.top)/2-fontMetricsInt.bottom;
        canvas.drawText(text,getPaddingLeft(),baseLine,mPaint);
    }
}
