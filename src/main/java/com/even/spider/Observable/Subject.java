package com.even.spider.Observable;

/**
 * 被观察者接口
 */
public interface Subject {
    public void addObserver(Observer observer);

    public void removeObserver(Observer observer);

    public void notifyObserver(Object o);
}
