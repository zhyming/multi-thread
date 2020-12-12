package com.zhym.basic.threadlocal;

import java.util.Random;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/16 0016 23:42
 */
public class ThreadLocalComplexTest {

    private final static ThreadLocal<String> threadlocal = new ThreadLocal<>();

    //seed
    private final static Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) throws InterruptedException {
        threadlocal.set("Thread-MAIN");

        Thread t1 = new Thread(() -> {
            threadlocal.set("Thread-T1");
            try {
                Thread.sleep(random.nextInt(1_000));
                System.out.println(Thread.currentThread().getName() + " -> " + threadlocal.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            threadlocal.set("Thread-T2");
            try {
                Thread.sleep(random.nextInt(1_000));
                System.out.println(Thread.currentThread().getName() + " -> " + threadlocal.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        t1.start();
        t2.start();

        t1.join();
        t2.join();


        System.out.println("========================================");
        System.out.println(Thread.currentThread().getName() + " -> " + threadlocal.get());

    }
}
