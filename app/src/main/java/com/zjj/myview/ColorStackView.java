package com.zjj.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

public class ColorStackView extends androidx.appcompat.widget.AppCompatTextView {
    private int originColor = Color.BLACK;
    private int changeColor = Color.RED;
    private Paint originPaint;
    private Paint changePaint;
    private float progress=0.0f;
    private Direction direction = Direction.LEFT_TO_RIGHT;
    private int baseLine;

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public ColorStackView(Context context) {
        this(context,null);
    }

    public ColorStackView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ColorStackView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorStackView);
        if (typedArray!=null){
            originColor = typedArray.getColor(R.styleable.ColorStackView_originColor,originColor);
            changeColor = typedArray.getColor(R.styleable.ColorStackView_changeColor,changeColor);
            typedArray.recycle();
        }
        originPaint = new Paint();
        originPaint.setAntiAlias(true);
        originPaint.setTextSize(getTextSize());
        originPaint.setColor(originColor);

        changePaint = new Paint();
        changePaint.setAntiAlias(true);
        changePaint.setTextSize(getTextSize());
        changePaint.setColor(changeColor);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        String text = getText().toString();
        originPaint.getTextBounds(text,0,text.length(), new Rect());
        Paint.FontMetricsInt fontMetricsInt = originPaint.getFontMetricsInt();
        baseLine = getHeight()/2+(fontMetricsInt.bottom-fontMetricsInt.top)/2-fontMetricsInt.bottom+getPaddingTop()-getPaddingTop();
        if (direction==Direction.LEFT_TO_RIGHT){
            canvas.save();
            drawText(canvas,(int) (getWidth()*progress),getWidth(),originPaint);
            canvas.restore();
            drawText(canvas,0,(int) (getWidth()*progress),changePaint);
        }else {
            canvas.save();
            drawText(canvas,getWidth()-(int) (getWidth()*progress),getWidth(),changePaint);
            canvas.restore();
            drawText(canvas,0,getWidth()-(int) (getWidth()*progress),originPaint);
        }
//        String text = getText().toString();
//        int width = getWidth();
//        Rect rect = new Rect((int) (width*progress),0,getWidth(),getHeight());
//        canvas.clipRect(rect);
//        Rect bounds = new Rect();
//        originPaint.getTextBounds(text,0,text.length(),bounds);
//        Paint.FontMetricsInt fontMetricsInt = originPaint.getFontMetricsInt();
//        int baseLine = bounds.height()/2+(fontMetricsInt.bottom-fontMetricsInt.top)/2-fontMetricsInt.bottom;
//        canvas.drawText(text,0,baseLine,originPaint);
//        canvas.restore();
//        Rect rectChange = new Rect(0,0,(int) (width*progress),getHeight());
//        canvas.clipRect(rectChange);
//        canvas.drawText(text,0,baseLine,changePaint);
    }
    private void drawText(Canvas canvas,int start,int end,Paint paint){
        String text = getText().toString();
        Rect rect = new Rect(start,0,end,getHeight());
        canvas.clipRect(rect);

        canvas.drawText(text,0,baseLine,paint);
    }
    public void setProgress(float progress){
        this.progress = progress;
        invalidate();
    }
}
