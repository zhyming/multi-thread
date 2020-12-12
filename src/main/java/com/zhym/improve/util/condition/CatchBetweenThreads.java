package com.zhym.improve.util.condition;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: zhym
 * @time: 2020/12/2 0002 1:37
 */
public class CatchBetweenThreads {

    private static int data = 0;

    private static boolean notUse = true;

    private final static Object MONITOR = new Object();

    public static void main(String[] args) {

        new Thread(() -> {for(;;) {
            buildData();
        }}).start();

        new Thread(() -> {for(;;) {
            useData();
        }}).start();
    }

    private static void buildData() {
        synchronized (MONITOR) {
            while (notUse) {
                try {
                    MONITOR.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            data ++;
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(" P: " + data);
            notUse = true;
            MONITOR.notifyAll();
        }
    }

    private static void useData() {
        synchronized (MONITOR) {
            while (!notUse) {
                try {
                    MONITOR.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(" C: " + data);
            notUse = false;
            MONITOR.notifyAll();
        }
    }
}
