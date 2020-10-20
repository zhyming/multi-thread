package com.zhym.concurrent.volatilechapter;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/12 0012 23:51
 */
public class VolatileTest2 {

    /**
     *
     *
     *
     *
     */
    private static int INIT_VALUES = 0;

    private final static int MAX_LIMIT = 50;

    public static void main(String[] args) {
        new Thread(() -> {
            while (INIT_VALUES < MAX_LIMIT) {
                System.out.println("T1 -> " + ++INIT_VALUES);
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "ADDER-1").start();

        new Thread(() -> {
            while (INIT_VALUES < MAX_LIMIT) {
                System.out.println("T2 -> " + INIT_VALUES ++);
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }, "ADDER-2").start();
    }
}
