package com.zhym.improve.atomic.reference;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @description:
 * @author: zhym
 * @time: 2020/11/6 0006 0:33
 */
public class AtomicStampedReferenceTest {

    private static AtomicStampedReference<Integer> atomic = new AtomicStampedReference<>(100, 0);

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                boolean success = atomic.compareAndSet(100, 101, atomic.getStamp(), atomic.getStamp() + 1);
                System.out.println(Thread.currentThread().getName() + " : " + success);
                atomic.compareAndSet(101, 100, atomic.getStamp(), atomic.getStamp() + 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                int stamp = atomic.getStamp();
                System.out.println(" Before -- stamp; " + stamp);
                TimeUnit.SECONDS.sleep(2);
                boolean success = atomic.compareAndSet(100, 101, stamp, stamp + 1);
                System.out.println(Thread.currentThread().getName() + " : " + success);
                System.out.println(atomic.getReference());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
