package com.zjj.myview;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * 贝塞尔曲线得出每个点的坐标
 */
public class BezierEvaluator implements TypeEvaluator<PointF> {
    private PointF point1,point2;

    public BezierEvaluator(PointF point1, PointF point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    @Override
    public PointF evaluate(float t, PointF startValue, PointF endValue) {
        PointF pointF = new PointF();
        //贝塞尔公式计算 x和y
        pointF.x = (float) (startValue.x*Math.pow(1-t,3)+3*point1.x*t*Math.pow(1-t,2)+3*point2.x*Math.pow(t,2)*(1-t)+endValue.x*Math.pow(t,3));
        pointF.y = (float) (startValue.y*Math.pow(1-t,3)+3*point1.y*t*Math.pow(1-t,2)+3*point2.y*Math.pow(t,2)*(1-t)+endValue.y*Math.pow(t,3));
        return pointF;
    }
}
