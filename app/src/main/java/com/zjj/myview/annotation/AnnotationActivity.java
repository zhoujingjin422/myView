package com.zjj.myview.annotation;

import android.os.Bundle;
import android.util.Log;

import com.zjj.myview.databinding.ActivityAnnotationBinding;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * author:zhoujingjin
 * date:2020/8/7
 */
public class AnnotationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAnnotationBinding binding =   ActivityAnnotationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.button.setOnClickListener(v -> goNext());
    }

    @ClickGap(name = "zjj",age = 30)
    private void goNext()  {
        Log.e("TAG","点击了一次");
        User user = new User();
        Class<? extends User> userClass = user.getClass();
        Method[] methods = userClass.getMethods();
        for (Method method: methods) {
            if (method.isAnnotationPresent(ClickGap.class)){
                ClickGap annotation = method.getAnnotation(ClickGap.class);
                int age = annotation.age();
                String name = annotation.name();
                Log.e("TAG","获取到注解中的数据：name = "+name+"age="+age);
                user.show();
            }

        }
        user.show();
    }
}
