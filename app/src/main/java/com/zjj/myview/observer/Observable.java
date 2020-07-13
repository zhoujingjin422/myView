package com.zjj.myview.observer;

/**
 * author:zhoujingjin
 * date:2020/7/4
 * 抽象被观察者接口，将来被观察者需要实现这个接口
 */
public interface Observable {
    public void registerObservable(Observer observer);//注册观察者
    public void unRegisterObservable(Observer observer);//观察者解除观察
    void notifyObserver();//通知观察者
}
