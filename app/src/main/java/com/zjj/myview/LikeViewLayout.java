package com.zjj.myview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatDrawableManager;

import java.util.Random;

public class LikeViewLayout extends RelativeLayout {
    private int[] drawables = {R.drawable.ic_baseline_favorite_blue,
            R.drawable.ic_baseline_favorite_red,
            R.drawable.ic_baseline_favorite_green};
    private Paint mPaint;
    private int with;
    private int height;
    private int drawableIntrinsicWidth;
    private int drawableIntrinsicHeight;

    public LikeViewLayout(Context context) {
        this(context,null);
    }

    public LikeViewLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LikeViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Drawable drawable = context.getResources().getDrawable(drawables[0]);
        drawableIntrinsicWidth = drawable.getIntrinsicWidth();
        drawableIntrinsicHeight = drawable.getIntrinsicHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        with = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
    }

    public void addLike() {
        AnimatorSet firstAnimation = new AnimatorSet();
        AnimatorSet animatorSet = new AnimatorSet();
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(drawables[new Random().nextInt(3)]);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(CENTER_HORIZONTAL);
        imageView.setLayoutParams(layoutParams);
        addView(imageView);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(imageView, "scaleX", 0.0f, 1.0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(imageView, "scaleY", 0.0f, 1.0f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(imageView, "alpha", 0.2f, 1.0f);

        firstAnimation.setDuration(350);
        firstAnimation.playTogether(scaleX,scaleY,alpha);
        animatorSet.playSequentially(firstAnimation,getBezierAnimation(imageView));
        animatorSet.start();
    }

    private Animator getBezierAnimation(final ImageView imageView) {
        AnimatorSet animatorSet = new AnimatorSet();
        //起始点
        PointF pointStart = new PointF(with/2-drawableIntrinsicWidth/2,height-drawableIntrinsicHeight);//底部中心点
        PointF pointEnd = new PointF(new Random().nextInt(with),0f);//顶部随机一个点
        PointF point1 = getPoint(1);//底部到中间线随机一个点
        PointF point2 = getPoint(2);//中间线到顶部随机一个点
        BezierEvaluator bezierEvaluator = new BezierEvaluator(point1,point2);
        ValueAnimator valueAnimator = ObjectAnimator.ofObject(bezierEvaluator, pointStart, pointEnd);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(imageView, "alpha", 1.0f, 0.0f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(imageView, "scaleX", 1.0f, 2.5f,1.0f,0.2f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(imageView, "scaleY", 1.0f, 2.5f,1.0f,0.2f);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
               PointF pointF = (PointF) animation.getAnimatedValue();
                imageView.setX(pointF.x);
                imageView.setY(pointF.y);
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                removeView(imageView);
            }
        });
        animatorSet.setDuration(2500);
        animatorSet.playTogether(alpha,scaleX,scaleY,valueAnimator);
        return animatorSet;
    }

    private PointF getPoint(int i) {
        PointF pointF = new PointF();
        pointF.x = new Random().nextInt(with);
        pointF.y = new Random().nextInt(height/2)+(i-1)*height/2;
        return pointF;
    }


}
