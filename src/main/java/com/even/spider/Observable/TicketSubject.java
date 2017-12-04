package com.even.spider.Observable;

/**
 * 被观察者实现类，用于通知观察者
 */

public class TicketSubject implements Subject {
    private Observer observer;

    public void addObserver(Observer observer) {
        this.observer = observer;
    }

    public void removeObserver(Observer observer) {
        this.observer = null;
    }

    public void notifyObserver(Object o) {
        observer.update(o);
    }
}
