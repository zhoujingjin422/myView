package com.zjj.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author : "zhoujingjin"
 * @timer : 2020/7/15
 */
public class UserLearnProgressView extends View {
    private int blu_back_color;//进度条底部背景颜色
    private int blu_progress_color;//进度条颜色
    private int blu_progress_text_color;//进度文字颜色
    private int blu_total_text_color;//全部进度文字颜色
    private int blu_level_text_color;//级别文字颜色
    private float blu_text_size;//进度文字大小
    private int blu_back_height;//进度底部高度
    private int blu_progress_height;//进度高度
    private float blu_text_level_size;//级别文字大小
    private int blu_level_src;//级别图片
    private String blu_level_text="";//级别文字
    private int blu_total;//总进度文字
    private int blu_progress;//当前进度文字文字
    private Bitmap bitmap;//level图片
    private Paint mPaintBack;//进度条背景画笔
    private Paint mPaintProgress;//进度条画笔
    private Paint mPaintText;//进度文字画笔
    private Paint mPaintTotalText;
    private Paint mPaintLevelText;
    private float space = dp2px(4);//底部文字和顶部进度条之间的间距
    private int with;
    private int height;
    private String progress_text="0";
    public UserLearnProgressView(Context context) {
        this(context,null);
    }

    public UserLearnProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public UserLearnProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.UserLearnProgressView);
        blu_back_color = typedArray.getColor(R.styleable.UserLearnProgressView_blu_back_color,getResources().getColor(R.color.white30));
        blu_progress_color = typedArray.getColor(R.styleable.UserLearnProgressView_blu_progress_color,getResources().getColor(R.color.e54525));
        blu_progress_text_color = typedArray.getColor(R.styleable.UserLearnProgressView_blu_progress_text_color,getResources().getColor(R.color.white));
        blu_total_text_color = typedArray.getColor(R.styleable.UserLearnProgressView_blu_total_text_color,getResources().getColor(R.color.white65));
        blu_level_text_color = typedArray.getColor(R.styleable.UserLearnProgressView_blu_level_text_color,getResources().getColor(R.color.white));
        blu_text_size = typedArray.getDimension(R.styleable.UserLearnProgressView_blu_text_size,sp2px(11));
        blu_back_height = (int) typedArray.getDimension(R.styleable.UserLearnProgressView_blu_back_height,dp2px(7));
        blu_progress_height = (int) typedArray.getDimension(R.styleable.UserLearnProgressView_blu_progress_height,dp2px(7));
        blu_text_level_size = typedArray.getDimension(R.styleable.UserLearnProgressView_blu_text_level_size,sp2px(11));
        blu_level_src =  typedArray.getResourceId(R.styleable.UserLearnProgressView_blu_level_src,0);
        blu_total =  typedArray.getInteger(R.styleable.UserLearnProgressView_blu_total,100);
        blu_progress =  typedArray.getInteger(R.styleable.UserLearnProgressView_blu_progress,0);
        progress_text = String.valueOf(blu_progress);

        if (blu_progress>0){
            blu_progress = Math.max(blu_progress,5);
        }
        blu_level_text =  typedArray.getString(R.styleable.UserLearnProgressView_blu_level_text);
        if (blu_level_src!=0){
            if (Build.VERSION.SDK_INT> Build.VERSION_CODES.LOLLIPOP){
                Drawable vectorDrawable = context.getDrawable(blu_level_src);
                bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                        vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                vectorDrawable.draw(canvas);
            }else {
                bitmap = BitmapFactory.decodeResource(context.getResources(), blu_level_src);
            }
        }
        initPaint();
    }

    private void initPaint() {
        mPaintBack = new Paint();
        mPaintBack.setAntiAlias(true);
        mPaintBack.setColor(blu_back_color);
        mPaintBack.setStrokeWidth(blu_back_height);
        mPaintBack.setStrokeCap(Paint.Cap.ROUND);//带圆弧

        mPaintProgress = new Paint();
        mPaintProgress.setAntiAlias(true);
        mPaintProgress.setStrokeWidth(blu_progress_height);
        mPaintProgress.setColor(blu_progress_color);
        mPaintProgress.setStrokeCap(Paint.Cap.ROUND);//带圆弧

        mPaintText = new Paint();
        mPaintText.setAntiAlias(true);
        mPaintText.setColor(blu_progress_text_color);
        mPaintText.setTextSize(blu_text_size);

        mPaintTotalText = new Paint();
        mPaintTotalText.setAntiAlias(true);
        mPaintTotalText.setColor(blu_total_text_color);
        mPaintTotalText.setTextSize(blu_text_size);


        mPaintLevelText = new Paint();
        mPaintLevelText.setAntiAlias(true);
        mPaintLevelText.setColor(blu_level_text_color);
        mPaintLevelText.setTypeface(Typeface.DEFAULT_BOLD);//粗体
        mPaintLevelText.setTextSize(blu_text_level_size);
    }

    private float sp2px(int px){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,px,getResources().getDisplayMetrics());
    }
 private int dp2px(int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        with = MeasureSpec.getSize(widthMeasureSpec);
        Rect rect = new Rect();
        mPaintText.getTextBounds("22",0,2,rect);
       int  height1 = (int) (bitmap.getHeight()/2+blu_back_height/2+space+rect.height());//计算出高度,取最大的值
       int  height2 = bitmap.getHeight();//计算出高度,取最大的值
        height =Math.max(height1,height2)+dp2px(5);
        setMeasuredDimension(with,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //先画底部进度条
        drawBackProgress(canvas, (with-blu_back_height/2),mPaintBack);
        //画进度条
        drawProgress(canvas, (with-blu_back_height/2) * blu_progress / blu_total,mPaintProgress);
        //画图片
        drawLevelPic(canvas);
        //画图片上的文字
        drawLevelText(canvas);
        //画底部进度文字
        drawProgressText(canvas);
        //画底部总进度文字
        drawTotalText(canvas);
    }
    private void drawTotalText(Canvas canvas) {
        Rect rect = new Rect();
        Rect rect1 = new Rect();
        String str1 = progress_text + "/" + blu_total;
        mPaintText.getTextBounds(progress_text,0,progress_text.length(),rect);
        mPaintTotalText.getTextBounds(str1,0,str1.length(),rect1);
        Paint.FontMetricsInt fontMetricsInt = mPaintText.getFontMetricsInt();
        int height = (int) (bitmap.getHeight()/2+blu_progress_height/2+space+rect.height()/2+(fontMetricsInt.bottom- fontMetricsInt.top)/2-fontMetricsInt.bottom);
        canvas.drawText("/"+blu_total,with/2-rect1.width()/2+rect.width(),height,mPaintTotalText);
    }
    private void drawProgressText(Canvas canvas) {
        Rect rect = new Rect();
        String str = progress_text + "/" + blu_total;
        mPaintText.getTextBounds(str,0,str.length(),rect);
        Paint.FontMetricsInt fontMetricsInt = mPaintText.getFontMetricsInt();
        int height = (int) (bitmap.getHeight()/2+blu_progress_height/2+space+rect.height()/2+(fontMetricsInt.bottom- fontMetricsInt.top)/2-fontMetricsInt.bottom);
        canvas.drawText(progress_text,with/2-rect.width()/2,height,mPaintText);
    }

    private void drawLevelText(Canvas canvas) {
        Rect rect1 = new Rect();
        mPaintLevelText.getTextBounds(blu_level_text,0,blu_level_text.length(),rect1);
        Paint.FontMetricsInt fontMetricsInt = mPaintLevelText.getFontMetricsInt();
        int height = bitmap.getHeight()/2+(fontMetricsInt.bottom-fontMetricsInt.top)/2-fontMetricsInt.bottom;
        canvas.drawText(blu_level_text,bitmap.getWidth()/2-rect1.width()/2,height,mPaintLevelText);
    }

    private void drawLevelPic(Canvas canvas) {
        if (bitmap!=null)
        canvas.drawBitmap(bitmap,0,0,new Paint());
    }

    private void drawProgress(Canvas canvas, int i,Paint paint) {
        if (i>0){
            i = Math.min(i,with-blu_back_height/2);
            canvas.drawLine(bitmap.getWidth()*4/5+blu_back_height/2,bitmap.getWidth() / 2,i,bitmap.getWidth() / 2,paint);
        }
    }

    private void drawBackProgress(Canvas canvas, int with,Paint paint) {
        canvas.drawLine(bitmap.getWidth()*4/5+blu_back_height/2,bitmap.getHeight() / 2 ,with-blu_back_height/2,bitmap.getHeight() / 2 ,paint);
    }
    public void setBluProgress(int blu_progress){
        this.blu_progress = blu_progress;
        invalidate();
    }
    public void setLevel(String level){
        blu_level_text = level;
        invalidate();
    }
}
