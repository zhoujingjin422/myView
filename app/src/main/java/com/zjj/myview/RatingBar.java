package com.zjj.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class RatingBar extends View {
    private Bitmap normalBitmap;
    private Bitmap selectedBitmap;
    private int space = 5;
    private int starNum = 5;
    public RatingBar(Context context) {
        this(context,null);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatingBar);
        if (typedArray!=null){
            int resourceId = typedArray.getResourceId(R.styleable.RatingBar_normalImage, 0);
            if (resourceId==0){
               throw  new RuntimeException("请设置normalImage");
            }
            if (Build.VERSION.SDK_INT> Build.VERSION_CODES.LOLLIPOP){
                Drawable vectorDrawable = context.getDrawable(resourceId);
                normalBitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                        vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(normalBitmap);
                vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                vectorDrawable.draw(canvas);
            }else {
                normalBitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
            }

            int resourceId1 = typedArray.getResourceId(R.styleable.RatingBar_selectedImage, 0);
            if (resourceId1==0){
                throw  new RuntimeException("请设置normalImage");
            }
            if (Build.VERSION.SDK_INT> Build.VERSION_CODES.LOLLIPOP){
                Drawable vectorDrawable = context.getDrawable(resourceId1);
                selectedBitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                        vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(selectedBitmap);
                vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                vectorDrawable.draw(canvas);
            }else {
                selectedBitmap = BitmapFactory.decodeResource(context.getResources(), resourceId1);
            }
            space = typedArray.getDimensionPixelSize(R.styleable.RatingBar_starSpace,space);
            starNum = typedArray.getInteger(R.styleable.RatingBar_starNum,starNum);
            typedArray.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = normalBitmap.getHeight();
        int with = normalBitmap.getWidth()*starNum+space*(starNum-1);
        setMeasuredDimension(with,height);
    }

    private float touch_x;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                touch_x = event.getX();
                Log.e("TAG",touch_x+"");
                invalidate();
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < starNum; i++) {
            int x = normalBitmap.getWidth()*i+i*space;
            canvas.drawBitmap(normalBitmap,x,0,null);
        }
        canvas.save();
        canvas.restore();
        Rect rect = new Rect(0, 0, (int) touch_x, getHeight());
        canvas.clipRect(rect);
        for (int i = 0; i < starNum; i++) {
            int x = normalBitmap.getWidth()*i+i*space;
            canvas.drawBitmap(selectedBitmap,x,0,null);
        }
        canvas.save();
    }
}
