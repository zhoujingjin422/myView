package com.zjj.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class ShapeView extends View {
    private Paint mPaintCircle;
    private Paint mPaintTriangle;
    private Paint mPaintSquare;

    public ShapeView(Context context) {
        this(context, null);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaintCircle = new Paint();
        mPaintCircle.setColor(Color.RED);
        mPaintCircle.setAntiAlias(true);
        mPaintCircle.setStyle(Paint.Style.FILL);

        mPaintSquare = new Paint();
        mPaintSquare.setColor(Color.YELLOW);
        mPaintSquare.setAntiAlias(true);
        mPaintSquare.setStyle(Paint.Style.FILL);

        mPaintTriangle = new Paint();
        mPaintTriangle.setColor(Color.BLUE);
        mPaintTriangle.setAntiAlias(true);
        mPaintTriangle.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int with = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int min = Math.min(with, height);
        setMeasuredDimension(min, min);
    }

    private Path path;

    @Override
    protected void onDraw(Canvas canvas) {

        switch (shape) {
            case CIRCLE: //画圆
                canvas.drawCircle(getWidth() / 2, getHeight() / 2, getHeight() / 2, mPaintCircle);
                break;
            case SQUARE: //画正方形
                Rect rect = new Rect(0, 0, getWidth(), getHeight());
                canvas.drawRect(rect, mPaintSquare);
                break;
            case TRIANGLE://画三角形
                if (path == null) {
                    path = new Path();
                    path.moveTo(getWidth() / 2, 0);
                    path.lineTo(0, getHeight());
                    path.lineTo(getWidth(), getHeight());
                    path.close();
                }
                canvas.drawPath(path, mPaintTriangle);
                break;
        }
    }

    private Shape shape = Shape.CIRCLE;

    public enum Shape {
        CIRCLE, SQUARE, TRIANGLE
    }

    public void changeShape() {
        if (shape == Shape.CIRCLE) {
            shape = Shape.SQUARE;
        } else if (shape == Shape.SQUARE) {
            shape = Shape.TRIANGLE;
        } else {
            shape = Shape.CIRCLE;
        }
        invalidate();
    }
}
