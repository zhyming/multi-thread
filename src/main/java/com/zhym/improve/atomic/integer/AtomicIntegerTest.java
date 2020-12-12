package com.zhym.improve.atomic.integer;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description:
 * @author: zhym
 * @time: 2020/11/4 0004 0:21
 */
public class AtomicIntegerTest {


    /**
     * volatile
     * 1.保证可见性、有序性
     * 2.不能保证原子性
     */
    private static volatile int value;


    public static void main(String[] args) throws InterruptedException {
        AtomicInteger val = new AtomicInteger();
        Thread t1 = new Thread(()-> {
            int x = 0;
            while (x <= 50) {
                int tmp = val.getAndIncrement();
                System.out.println(Thread.currentThread().getName() + " : " + tmp);
                x ++;

                /**
                 * value ++
                 * 1.从主存获取值到本地内存
                 * 2.执行累加
                 * 3.把值赋给value
                 * 4.把本地内存刷新到主存
                 */
            }
        }, "线程一");

        Thread t2 = new Thread(()-> {
            int x = 0;
            while (x <= 50) {
                int tmp = val.getAndIncrement();
                System.out.println(Thread.currentThread().getName() + " : " + tmp);
                x ++;
            }
        }, "线程二");

        Thread t3 = new Thread(()-> {
            int x = 0;
            while (x <= 50) {
                int tmp = val.getAndIncrement();
                System.out.println(Thread.currentThread().getName() + " : " + tmp);
                x ++;
            }
        }, "线程三");

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();
    }

    public static void main2(String[] args) throws InterruptedException {


        Thread t1 = new Thread(()-> {
            int x = 0;
            while (x <= 50) {
                int tmp = value;
                System.out.println(Thread.currentThread().getName() + " : " + tmp);
                value ++;
                x ++;

                /**
                 * value ++
                 * 1.从主存获取值到本地内存
                 * 2.执行累加
                 * 3.把值赋给value
                 * 4.把本地内存刷新到主存
                 */
            }
        }, "线程一");

        Thread t2 = new Thread(()-> {
            int x = 0;
            while (x <= 50) {
                int tmp = value;
                System.out.println(Thread.currentThread().getName() + " : " + tmp);
                value ++;
                x ++;
            }
        }, "线程二");

        Thread t3 = new Thread(()-> {
            int x = 0;
            while (x <= 50) {
                int tmp = value;
                System.out.println(Thread.currentThread().getName() + " : " + tmp);
                value ++;
                x ++;
            }
        }, "线程三");

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();



    }

}
