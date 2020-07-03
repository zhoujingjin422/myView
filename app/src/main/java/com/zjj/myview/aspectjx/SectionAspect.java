package com.zjj.myview.aspectjx;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author : zhoujingjin
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
    public void checkNet(ProceedingJoinPoint joinPoint) throws Throwable {
        Log.e(TAG, "CheckNet");
      MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        CheckNet checkNet = methodSignature.getMethod().getAnnotation(CheckNet.class);
        if (checkNet!=null){
            //判断网络是否可用
            Object object = joinPoint.getThis();
            Context context = getContext(object);
            if (context!=null){
                boolean networkConnected = isNetworkConnected(context);
                if (!networkConnected){
                    Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            joinPoint.proceed();
        }
    }

    private Context getContext(Object object) {
        if (object instanceof Activity){
            return (Activity) object;
        }else if (object instanceof Fragment){
            return ((Fragment) object).getContext();
        }else if (object instanceof View){
            return ((View) object).getContext();
        }
        return null;
    }

    private  boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            assert mConnectivityManager != null;
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {

                //这种方法也可以
                //return mNetworkInfo .getState()== NetworkInfo.State.CONNECTED

                return mNetworkInfo.isAvailable();

            }
        }
        return false;
    }
}
