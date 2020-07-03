package com.zjj.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class MessageBubbleView extends View {
    private Paint mPaint;
    private float downX;
    private float downY;
    private float circle_small_max_radius = dp2px(15);
    private float circle_big_radius = dp2px(20);
    private float moveX;
    private float moveY;
    private float distance;

    private float dp2px(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    public MessageBubbleView(Context context) {
        this(context, null);
    }

    public MessageBubbleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MessageBubbleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = event.getX();
                moveY = event.getY();
                //根据滑动来缩小小圆的半径
                distance = (float) getDistance();
//                Log.e("TAG",String.valueOf(distance));
                circle_small_max_radius = circle_small_max_radius - distance / dp2px(250);
                if (circle_big_radius > dp2px(8)) {
                    circle_big_radius = dp2px(8);
                }
                Log.e("TAG", String.valueOf(circle_small_max_radius));

                break;
            case MotionEvent.ACTION_UP:
                moveX = downX;
                moveY = downY;
                circle_small_max_radius = dp2px(8);
                break;

        }
        invalidate();
        return true;
    }

    private double getDistance() {
        float x = moveX - downX;
        float y = moveY - downY;
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画大圆
        canvas.drawCircle(moveX, moveY, circle_big_radius, mPaint);
        //计算贝塞尔曲线
        Path path = getBesanPath();
        if (path != null) {
            //画小圆
            canvas.drawPath(path, mPaint);
            canvas.drawCircle(downX, downY, circle_small_max_radius, mPaint);
        }


    }

    private Path getBesanPath() {
        if (circle_small_max_radius < 2)
            return null;
        float dy = Math.abs(moveY - downY);
        float dx = Math.abs(moveX - downX);
        Path path = new Path();
        Point p0 = new Point();
        Point pc = new Point();
        pc.x = (int) (downX + dx / 2);
        pc.y = (int) (downY - dy / 2);
        p0.x = (int) (downX + distance * dy / circle_small_max_radius);
        p0.y = (int) (downY + distance * dx / circle_small_max_radius);
        Point p1 = new Point();
        p1.x = (int) (moveX + distance * dy / circle_big_radius);
        p1.y = (int) (moveY + distance * dx / circle_big_radius);
        Point p2 = new Point();
        p2.x = (int) (moveX - distance * dy / circle_big_radius);
        p2.y = (int) (moveY - distance * dx / circle_big_radius);
        Point p3 = new Point();
        p3.x = (int) (downX - distance * dy / circle_small_max_radius);
        p3.y = (int) (downY - distance * dx / circle_small_max_radius);
        path.moveTo(p0.x, p0.y);
        path.cubicTo(p0.x, p0.y, pc.x, pc.y, p1.x, p1.y);
        path.lineTo(p2.x, p2.y);
        path.cubicTo(p2.x, p2.y, pc.x, pc.y, p3.x, p3.y);
        path.close();
        return path;
    }
}
