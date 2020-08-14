package com.zjj.myview.reflect;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.zjj.myview.databinding.ActivityReflectBinding;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * author:zhoujingjin
 * date:2020/8/9
 */
public class ReflectActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ActivityReflectBinding.inflate(getLayoutInflater()).getRoot());
        getReflect();
    }

    private void getReflect() {
        Person wgg = new Person("wgg", 18);
        Class wggClass = wgg.getClass();
        wggClass.getDeclaredFields();
    }
}
