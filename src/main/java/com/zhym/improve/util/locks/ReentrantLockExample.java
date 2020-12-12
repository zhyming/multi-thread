package com.zhym.improve.util.locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * @description:
 * @author: zhym
 * @time: 2020/12/1 0001 23:35
 */
public class ReentrantLockExample {

    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> testUnInterruptibly());
        t1.start();

        TimeUnit.SECONDS.sleep(1);

        Thread t2 = new Thread(() -> testUnInterruptibly());
        t2.start();

        TimeUnit.SECONDS.sleep(1);
        System.out.println(lock.getQueueLength());
        System.out.println(lock.hasQueuedThreads());
        System.out.println(lock.hasQueuedThreads());
        System.out.println(lock.hasQueuedThread(t1));
        System.out.println(lock.hasQueuedThread(t2));
        System.out.println(lock.isLocked());
        //System.out.println(lock.getQueuedThreads());

    }


    public static void main1(String[] args) throws InterruptedException {

        /*IntStream.rangeClosed(0, 1).forEach(i ->
            new Thread(() -> needLock()).start()
        );*/

        //System.out.println("---------------------------------------");

        /*Thread t1 = new Thread(() -> testUnInterruptibly());
        t1.start();

        TimeUnit.SECONDS.sleep(1);

        Thread t2 = new Thread(() -> testUnInterruptibly());
        t2.start();

        TimeUnit.SECONDS.sleep(1);*/
        //t2.interrupt();
        Thread t1 = new Thread(() -> tryLockTest());
        t1.start();

        TimeUnit.SECONDS.sleep(1);

        Thread t2 = new Thread(() -> tryLockTest());
        t2.start();

    }

    public static void tryLockTest() {
        if (lock.tryLock()) {
            try {
                System.out.println(Thread.currentThread().getName() + " get lock and do working .");
                while (true) {

                }
            }finally {
                lock.unlock();
            }

        }else {
            System.out.println(Thread.currentThread().getName() + " not get lock and close working .");
        }
    }

    public static void testUnInterruptibly() {

        try {
            lock.lockInterruptibly();
            System.out.println(Thread.currentThread().getName() + " : " + lock.getHoldCount());
            System.out.println(Thread.currentThread().getName() + " get lock an do working .");
            while (true) {

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void needLock() {

        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " get lock an do working .");
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public static void needLockBySync() {
        synchronized (ReentrantLockExample.class) {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
