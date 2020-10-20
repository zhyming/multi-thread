package com.zhym.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/14 0014 0:29
 */
public class Subject {

    private List<Observer> observers = new ArrayList<>();

    private int state;

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        if (state == this.state) {
            return;
        }
        this.state = state;
        notifyAllObserver();
    }

    private void notifyAllObserver() {
        observers.stream().forEach(o -> {
            o.update();
        });
    }

    public void attach(Observer observer) {
        observers.add(observer);
    }
}
