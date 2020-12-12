package com.zhym.basic.immutable;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/15 0015 0:59
 */
public class UserPersonThread extends Thread{

    private Person person;

    public UserPersonThread(Person person) {
        this.person = person;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(Thread.currentThread().getName() + " print " + person.toString());
        }

    }
}
