package com.zhym.basic.observer;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/14 0014 0:31
 */
public abstract class Observer {

    protected Subject subject;

    public Observer(Subject subject) {
        this.subject = subject;
        this.subject.attach(this);
    }

    public abstract void update();

}
