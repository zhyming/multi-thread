package com.zhym.observer;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/14 0014 0:34
 */
public class BinaryObserver extends Observer {

    public BinaryObserver(Subject subject) {

        super(subject);
    }

    @Override
    public void update() {
        System.out.println("Binary String : " + Integer.toBinaryString(subject.getState()));
    }
}
