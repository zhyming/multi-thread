package com.zhym.improve.util.locks;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @description:
 * @author: zhym
 * @time: 2020/12/2 0002 0:27
 */
public class ReadWriteLockExample {

    private final static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);

    private final static Lock readLock = readWriteLock.readLock();

    private final static Lock writeLock = readWriteLock.writeLock();

    private final static List<Long> data = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(ReadWriteLockExample::read);
        t1.start();

        TimeUnit.SECONDS.sleep(1);

        Thread t2 = new Thread(ReadWriteLockExample::read);
        t2.start();

    }

    public static void write() {
        try {
            writeLock.lock();
            data.add(System.currentTimeMillis());
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
        }
    }

    public static void read() {
        try {
            readLock.lock();
            data.forEach(System.out::println);
            TimeUnit.SECONDS.sleep(3);
            System.out.println(Thread.currentThread().getName() + " =============== ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readLock.unlock();
        }

    }
}

