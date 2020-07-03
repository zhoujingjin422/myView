package com.zjj.myview.aspectjx;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)//在方法上可以用：ElementType.METHOD 在类上可以用：ElementType.TYPE，在变量上可以用：ElementType.FIELD
@Retention(RetentionPolicy.RUNTIME)//RetentionPolicy.RUNTIME：运行时检测，RetentionPolicy.CLASS编译时
public @interface CheckNet {
}
