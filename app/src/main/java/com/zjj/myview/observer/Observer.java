package com.zjj.myview.observer;

/**
 * author:zhoujingjin
 * date:2020/7/4
 * 观察者需要实现这个接口，来接收被观察者变化时候发出来的消息
 */
public interface Observer {
    void updateData(String text);
}
