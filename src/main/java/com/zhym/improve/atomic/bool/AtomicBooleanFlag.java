package com.zhym.improve.atomic.bool;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @description:
 * @author: zhym
 * @time: 2020/11/5 0005 0:39
 */
public class AtomicBooleanFlag {

    //private final static AtomicBoolean flag = new AtomicBoolean(true);
    private static boolean flag = true;

    /*public static void main2(String[] args) throws InterruptedException {
        new Thread(() -> {
            while (flag.get()) {
                try {
                    Thread.sleep(1_000);
                    System.out.println(" I am working ");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(" I am finished. ");
        }).start();

        Thread.sleep(5_000);
        flag.set(false);
    }*/

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            while (flag) {
                try {
                    Thread.sleep(1_000);
                    //System.out.println(" I am working ");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(" I am finished. ");
        }).start();

        Thread.sleep(5_000);

        new Thread(() -> flag = false ).start();
    }
}
