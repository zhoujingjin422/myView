package com.zjj.myview.aspectjx;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author : taotie
 * @time : 2020/7/3
 */
@Aspect
public class SectionAspect {
    private static final String TAG = "zhoujingjin";

    /**
     * * *(..)处理所有的方法
     */
    @Pointcut("execution(@com.zjj.myview.aspectjx.CheckNet * *(..))")
    public void checkNetBehavior(){

    }
    /**
     * 处理切面
     */
    @Around("checkNetBehavior()")
    public Object checkNet(ProceedingJoinPoint joinPoint) throws Throwable {
        Log.e(TAG, "CheckNet");
        return joinPoint.proceed();
    }
}
