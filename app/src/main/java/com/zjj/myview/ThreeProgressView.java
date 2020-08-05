package com.zjj.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author : "zhoujingjin"
 * @timer : 2020/7/15
 */
public class ThreeProgressView extends View {

    private int tpv_progress1_color;
    private int tpv_progress2_color;
    private int tpv_progress3_color;
    private int tpv_back_color;
    private int tpv_total;
    private int tpv_progress1;
    private int tpv_progress2;
    private int tpv_progress3;
    private Paint mPaintProgress1;
    private Paint mPaintProgress2;
    private Paint mPaintProgress3;
    private Paint mPaintBack;
    private Paint mPaintText;
    private int with;
    private int height;
    private int blu_back_height;
    private boolean show_text;
    public ThreeProgressView(Context context) {
        this(context,null);
    }

    public ThreeProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ThreeProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ThreeProgressView);
        tpv_progress1_color = typedArray.getColor(R.styleable.ThreeProgressView_tpv_progress1_color,getResources().getColor(R.color.e54525));
        tpv_progress2_color = typedArray.getColor(R.styleable.ThreeProgressView_tpv_progress2_color,getResources().getColor(R.color.ff9782));
        tpv_progress3_color = typedArray.getColor(R.styleable.ThreeProgressView_tpv_progress3_color,getResources().getColor(R.color.ffe2d2));
        tpv_back_color = typedArray.getColor(R.styleable.ThreeProgressView_tpv_back_color,getResources().getColor(R.color.white30));
        tpv_total =  typedArray.getInteger(R.styleable.ThreeProgressView_tpv_total,100);
        tpv_progress1 =  typedArray.getInteger(R.styleable.ThreeProgressView_tpv_progress1,0);
        tpv_progress2 =  typedArray.getInteger(R.styleable.ThreeProgressView_tpv_progress2,0);
        tpv_progress3 =  typedArray.getInteger(R.styleable.ThreeProgressView_tpv_progress3,0);
        show_text =  typedArray.getBoolean(R.styleable.ThreeProgressView_tpv_show_text,false);
        blu_back_height = (int) typedArray.getDimension(R.styleable.ThreeProgressView_tpv_back_height,dp2px(7));
    }
    private float sp2px(int px){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,px,getResources().getDisplayMetrics());
    }
    private int dp2px(int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,getResources().getDisplayMetrics());
    }
    private void initPaint() {
        mPaintProgress1 = new Paint();
        mPaintProgress1.setStrokeWidth(blu_back_height);
        mPaintProgress1.setAntiAlias(true);
        mPaintProgress1.setColor(tpv_progress1_color);

        mPaintProgress2 = new Paint();
        mPaintProgress2.setStrokeWidth(blu_back_height);
        mPaintProgress2.setAntiAlias(true);
        mPaintProgress2.setColor(tpv_progress2_color);

        mPaintProgress3 = new Paint();
        mPaintProgress3.setStrokeWidth(blu_back_height);
        mPaintProgress3.setAntiAlias(true);
        mPaintProgress3.setColor(tpv_progress3_color);

        mPaintBack = new Paint();
        mPaintBack.setStrokeWidth(blu_back_height);
        mPaintBack.setAntiAlias(true);
        mPaintBack.setColor(tpv_back_color);

        mPaintText = new Paint();
        mPaintText.setAntiAlias(true);
        mPaintText.setTypeface(Typeface.DEFAULT_BOLD);//粗体
        mPaintText.setTextSize(sp2px(11));
        mPaintText.setColor(getResources().getColor(R.color._21242b));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        with = MeasureSpec.getSize(widthMeasureSpec);
         height =blu_back_height;
        setMeasuredDimension(with, height);
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画底部进度条
        canvas.drawLine(0,blu_back_height/2,with,blu_back_height/2 ,mPaintBack);
        //画第一条
        canvas.drawLine(0,blu_back_height/2,with*tpv_progress3/tpv_total,blu_back_height/2 ,mPaintProgress3);
        canvas.drawLine(0,blu_back_height/2,with*tpv_progress2/tpv_total,blu_back_height/2 ,mPaintProgress2);
        canvas.drawLine(0,blu_back_height/2,with*tpv_progress1/tpv_total,blu_back_height/2 ,mPaintProgress1);
        if (show_text){
          //画底部文字
        int progress = tpv_progress3*100/ tpv_total;
        String str = progress+"%";
        Rect rect = new Rect();
        mPaintText.getTextBounds(str,0,str.length(),rect);
        int withStr = Math.max(0,with*tpv_progress3/tpv_total -rect.width()/2);
        withStr = Math.min(with-rect.width(),withStr);
        Paint.FontMetricsInt fontMetricsInt = mPaintText.getFontMetricsInt();
        int height =blu_back_height/2+(fontMetricsInt.bottom-fontMetricsInt.top)/2-fontMetricsInt.bottom;
        canvas.drawText(str,withStr,height,mPaintText);
        }
    }
    /**
     * 更新掌握的进度
     * @param progress1
     */
    public void setProgress1(int progress1){
        if (progress1>tpv_progress2)
            return;
        this.tpv_progress1 = progress1;
        invalidate();
    }
    /**
     * 更新熟悉的进度
     * @param progress2
     */
    public void setProgress2(int progress2){
        if (progress2>tpv_progress3)
            return;
        this.tpv_progress2 = progress2;
        invalidate();
    }

    /**
     * 更新听过的进度
     * @param progress3
     */
    public void setProgress3(int progress3){
        if (progress3>tpv_total)
            return;
        this.tpv_progress3 = progress3;
        invalidate();
    }
}


