package com.zhym.basic.observer;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/14 0014 0:36
 */
public class OctalObserver extends Observer {
    public OctalObserver(Subject subject) {

        super(subject);
    }

    @Override
    public void update() {
        System.out.println("Octal String : " + Integer.toOctalString(subject.getState()));
    }
}
