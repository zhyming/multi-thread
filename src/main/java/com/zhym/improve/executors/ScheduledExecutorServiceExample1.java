package com.zhym.improve.executors;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @description:
 * @author: zhym
 * @time: 2020/12/10 0010 0:21
 */
public class ScheduledExecutorServiceExample1 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);

        /*ScheduledFuture<?> future = executor.schedule(() -> System.out.println(" ============= i will be invoked."), 2, TimeUnit.SECONDS);

        System.out.println(future.cancel(true));*/

        /*ScheduledFuture<Integer> future = executor.schedule(() -> 2, 2, TimeUnit.SECONDS);
        System.out.println(future.get());*/


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
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            interval.set(currentTimeMillis);
            System.out.println(Thread.currentThread().getName());
        }, 2, 2, TimeUnit.SECONDS);

    }
}
