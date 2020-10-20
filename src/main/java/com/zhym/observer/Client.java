package com.zhym.observer;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/14 0014 0:39
 */
public class Client {

    public static void main(String[] args) {

        final Subject subject = new Subject();

        new BinaryObserver(subject);
        new OctalObserver(subject);

        System.out.println("------------------------------");

        subject.setState(10);

        System.out.println("------------------------------");

        subject.setState(10);

        System.out.println("---------------------------------");
        subject.setState(12);

    }
}
