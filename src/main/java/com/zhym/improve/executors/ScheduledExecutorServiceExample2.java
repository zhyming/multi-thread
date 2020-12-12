package com.zhym.improve.executors;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @description:
 * @author: zhym
 * @time: 2020/12/10 0010 0:54
 */
public class ScheduledExecutorServiceExample2 {



    public static void main(String[] args) throws InterruptedException {
        //testScheduleWithFixedDelay();
        //testContinueExistingPeriodicTasksAfterShutdownPolicy();
        testExecuteExistingDelayedTasksAfterShutdownPolicy();

    }

    private static void testExecuteExistingDelayedTasksAfterShutdownPolicy() throws InterruptedException {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
        System.out.println(executor.getExecuteExistingDelayedTasksAfterShutdownPolicy());
        executor.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
        final AtomicLong interval = new AtomicLong(0L);

        /**
         *
         * 固定时间的延迟，如定义延迟2s，一个任务执行5s；那么任务完成后不需要等待直接触发下一个任务
         */

        ScheduledFuture<?> future = executor.scheduleAtFixedRate(() -> {
            long currentTimeMillis = System.currentTimeMillis();
            if (interval.get() == 0) {
                System.out.printf(" The first time trigger task at %d\n ", currentTimeMillis);
            } else {
                System.out.printf("The actually spend {%d} \n", currentTimeMillis - interval.get());
            }
            interval.set(currentTimeMillis);
            System.out.println(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }, 1, 2, TimeUnit.SECONDS);

        TimeUnit.MILLISECONDS.sleep(1200);
        executor.shutdown();
        System.out.println(" ===over=== ");
    }

    private static void testContinueExistingPeriodicTasksAfterShutdownPolicy() throws InterruptedException {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
        System.out.println(executor.getContinueExistingPeriodicTasksAfterShutdownPolicy());
        executor.setContinueExistingPeriodicTasksAfterShutdownPolicy(true);
        final AtomicLong interval = new AtomicLong(0L);

        /**
         *
         * 固定时间的延迟，如定义延迟2s，一个任务执行5s；那么任务完成后不需要等待直接触发下一个任务
         */

        ScheduledFuture<?> future = executor.scheduleAtFixedRate(() -> {
            long currentTimeMillis = System.currentTimeMillis();
            if (interval.get() == 0) {
                System.out.printf(" The first time trigger task at %d\n ", currentTimeMillis);
            } else {
                System.out.printf("The actually spend {%d} \n", currentTimeMillis - interval.get());
            }
            interval.set(currentTimeMillis);
            System.out.println(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }, 1, 2, TimeUnit.SECONDS);

        TimeUnit.MILLISECONDS.sleep(1200);
        executor.shutdown();
        System.out.println(" ===over=== ");

    }



    private static void testScheduleWithFixedDelay() throws InterruptedException {
        ScheduledThreadPoolExecutor  executor = new ScheduledThreadPoolExecutor(2);
        final AtomicLong interval = new AtomicLong(0L);
        /**
         *
         * 随着执行时间的延迟不变，如定义延迟2s，一个任务执行5s；那么任务完成后同样要等待2s后才能触发下一个任务
         */
        ScheduledFuture<?> future = executor.scheduleWithFixedDelay(() -> {
                    long currentTimeMillis = System.currentTimeMillis();
                    if (interval.get() == 0) {
                        System.out.printf(" The first time trigger task at %d\n ", currentTimeMillis);
                    } else {
                        System.out.printf("The actually spend {%d} \n", currentTimeMillis - interval.get());
                    }
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    interval.set(currentTimeMillis);
                    System.out.println(Thread.currentThread().getName());
                },
                1, 2, TimeUnit.SECONDS);

    }
}
