package com.zjj.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CircleView extends View {
    private Paint mPaint;
    private int mColor = 0xFFFF9800;//0xFFFF5722
    private int min;

    public CircleView(Context context) {
        this(context,null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        mColor = typedArray.getColor(R.styleable.CircleView_circle_color,mColor);
        typedArray.recycle();
        mPaint= new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mColor);
        mPaint.setStyle(Paint.Style.FILL);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
////                int with = MeasureSpec.getSize(widthMeasureSpec);
////        int height = MeasureSpec.getSize(heightMeasureSpec);
//////        min = Math.min(with, height);
//    }

    //    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super(widthMeasureSpec,heightMeasureSpec);
//        int with = MeasureSpec.getSize(widthMeasureSpec);
//        int height = MeasureSpec.getSize(heightMeasureSpec);
////        min = Math.min(with, height);
////        setMeasuredDimension(min, min);
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(getWidth()/2,getHeight()/2,getHeight()/2,mPaint);
    }

    public void changeColor() {
        if (mColor== 0xFFFF9800){
            mColor = 0xFFFF5722;
        }else {
            mColor = 0xFFFF9800;
        }
        invalidate();
    }
}
