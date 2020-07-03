package com.zjj.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class LetterView extends View {
    private String[] letters={"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","#"};
    private Paint mPaintNormal;
    private Paint mPaintSelected;
    private int itemHeight;

    public LetterView(Context context) {
        this(context,null);
    }

    public LetterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LetterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaintNormal = new Paint();
        mPaintNormal.setAntiAlias(true);
        mPaintNormal.setColor(Color.BLACK);
        mPaintNormal.setTextSize(sp2px(15));

        mPaintSelected = new Paint();
        mPaintSelected.setAntiAlias(true);
        mPaintSelected.setColor(Color.RED);
        mPaintSelected.setTextSize(sp2px(15));
    }

    private float sp2px(int sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int with = (int) (getPaddingLeft()+getPaddingRight()+mPaintNormal.measureText("A"));
        Rect rect = new Rect();
        mPaintNormal.getTextBounds("A",0,1,rect);
        int height1 = getPaddingTop()+getPaddingBottom()+rect.height()*letters.length;
        int  height = MeasureSpec.getSize(heightMeasureSpec);
        Log.e("TAG",height+"");
        Log.e("TAG",height1+"");
        setMeasuredDimension(with,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < letters.length; i++) {
            float v = mPaintNormal.measureText(letters[i]);
            int x = (int) ((getWidth()-v)/2);
            itemHeight = (getHeight() - getPaddingTop() - getPaddingBottom()) / letters.length;
            Paint.FontMetricsInt fontMetricsInt = mPaintNormal.getFontMetricsInt();
            int baseLine = itemHeight /2+(fontMetricsInt.bottom-fontMetricsInt.top)/2-fontMetricsInt.bottom+getPaddingTop()+ itemHeight *i;
            if (i==current_position)
            canvas.drawText(letters[i],x,baseLine,mPaintSelected);
            else             canvas.drawText(letters[i],x,baseLine,mPaintNormal);
        }
    }

    private int current_position = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
                int nowPosition = (int) ((y-getPaddingTop())/itemHeight);
                if (nowPosition<0)
                    nowPosition = 0;
                if (nowPosition>letters.length-1)
                    nowPosition = letters.length-1;
                current_position = nowPosition;
                invalidate();
                break;
        }
        return true;
    }
}
