package com.zjj.myview.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author:zhoujingjin
 * date:2020/8/6
 */
@Inherited
@Target({ElementType.METHOD})//作用的区域
@Retention(RetentionPolicy.RUNTIME)//一般用运行时
@Documented //javadoc生成时候会显示
public @interface ClickGap {
    String name() default "你好";
    int age() default 12;
}
