package com.zhym.improve.atomic.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description:
 * @author: zhym
 * @time: 2020/11/8 0008 21:32
 */
public class UnsafeTest {


    /**
     * 1.正常方式
     *  Counter result: 9376197
     *  Time passed : 189
     * 2.synchronized
     *  Counter result: 10000000
     *  Time passed : 875
     * 3.lock
     *  Counter result: 10000000
     *  Time passed : 252
     * 4.Atomic
     *  Counter result: 10000000
     *  Time passed : 258
     * 4.CAS
     *   Counter result: 10000000
     *  Time passed : 947
     *
     * @param args
     * @throws InterruptedException
     */

    public static void main(String[] args) throws InterruptedException, NoSuchFieldException {
        /*final Unsafe unsafe = Unsafe.getUnsafe();
        System.out.println(unsafe);*/
        //Unsafe unsafe = gerUnsafe();
        //System.out.println(unsafe);
        ExecutorService executor = Executors.newFixedThreadPool(1000);

        Counter counter = new CasCounterApply();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i ++) {
            executor.submit(new CounterRunnable(counter, 10_000));
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.HOURS);
        long end = System.currentTimeMillis();

        System.out.println(" Counter result: " + counter.getCounter());
        System.out.println(" Time passed : " + (end - start));


    }

    private static Unsafe gerUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            //get 传一个对象实例，但是如果方法或者字段为静态的则传一个null就可以了
            return  (Unsafe)field.get(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    interface Counter {
        void increment();

        long getCounter();
    }

    static class CounterApply implements Counter {

        private long counter = 0;
        @Override
        public void increment() {
            counter ++;
        }

        @Override
        public long getCounter() {
            return counter;
        }
    }

    static class SyncCounterApply implements Counter {

        private long counter = 0;
        @Override
        public synchronized void increment() {
            counter ++;
        }

        @Override
        public long getCounter() {
            return counter;
        }
    }

    static class LockCounterApply implements Counter {

        private long counter = 0;
        //定义一个lock
        private final Lock lock = new ReentrantLock();
        @Override
        public void increment() {
            try {
                lock.lock();
                counter ++;
            }finally {
                lock.unlock();
            }
        }

        @Override
        public long getCounter() {
            return counter;
        }
    }

    static class AtomicCounterApply implements Counter {

        private final AtomicInteger counter = new AtomicInteger();
        @Override
        public void increment() {
            counter.incrementAndGet();
        }

        @Override
        public long getCounter() {
            return counter.get();
        }
    }

    static class CasCounterApply implements Counter {

        private volatile long counter = 0;
        private Unsafe unsafe;
        private long offset;

        public CasCounterApply() throws NoSuchFieldException {
            unsafe = gerUnsafe();
            offset = unsafe.objectFieldOffset(CasCounterApply.class.getDeclaredField("counter"));
        }

        @Override
        public void increment() {
            long current = counter;
            while (!unsafe.compareAndSwapLong(this, offset, current, current + 1)) {
                current = counter;
            }
        }

        @Override
        public long getCounter() {
            return counter;
        }
    }

    static class CounterRunnable implements Runnable {
        private final Counter counter;
        private final int num;

        public CounterRunnable(Counter counter, int num) {
            this.counter = counter;
            this.num = num;
        }

        @Override
        public void run() {
            for (int i = 0; i < num; i ++) {
                counter.increment();
            }
        }
    }
}
