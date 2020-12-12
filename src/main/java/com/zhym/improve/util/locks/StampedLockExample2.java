package com.zhym.improve.util.locks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: zhym
 * @time: 2020/12/2 0002 22:22
 */
public class StampedLockExample2 {

    /**
     *
     * ReentrantLock vs Synchronized
     * <p></p>
     * ReentrantReadWriteLock
     *
     * @param args
     */

    private static final StampedLock lock = new StampedLock();

    private static final List<Long> DATA = new ArrayList<>();

    private static void read() {

        long stamped = lock.tryOptimisticRead();
        if (lock.validate(stamped)) {
            try {
                stamped = lock.readLock();
                Optional.of(
                        DATA.stream()
                                .map(String::valueOf)
                                .collect(Collectors.joining("#", "R-", "")))
                        .ifPresent(System.out::println);
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlockRead(stamped);
            }

        }
    }

    private static void write() {
        long stamped = -1;
        try {
            stamped = lock.writeLock();
            DATA.add(System.currentTimeMillis());
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlockWrite(stamped);
        }
    }

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(10);
        Runnable readTask = () -> {
            for (;;) {
                read();
            }
        };
        Runnable writeTask = () -> {
            for (;;) {
                write();
            }
        };

        /**
         * 存在多个读，有少有的几个写
         */
        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(writeTask);
    }
}
