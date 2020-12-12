package com.zhym.improve.executors;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: zhym
 * @time: 2020/12/7 0007 20:13
 */
public class ThreadPoolExecutorBuild {

    public static void main(String[] args) {

        ThreadPoolExecutor executorService = (ThreadPoolExecutor)buildThreadPoolExecutor();

        int activeCount = -1;
        int queueSize = -1;

        while (true) {
            if (activeCount != executorService.getActiveCount() || queueSize != executorService.getQueue().size()) {
                System.out.println(executorService.getActiveCount());
                System.out.println(executorService.getCorePoolSize());
                System.out.println(executorService.getQueue().size());
                System.out.println(executorService.getMaximumPoolSize());

                activeCount = executorService.getActiveCount();
                queueSize = executorService.getQueue().size();
                System.out.println("==========================================");
            }
        }
    }

    private static ExecutorService buildThreadPoolExecutor() {

        ExecutorService executorService = new ThreadPoolExecutor(1, 2, 30,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(1), r -> {
            Thread t = new Thread(r);
            return t;
        }, new ThreadPoolExecutor.AbortPolicy());

        System.out.println("The threadPoolExecutor create done. ");
        executorService.execute(() -> sleepSeconds(100));
        executorService.execute(() -> sleepSeconds(10));
        executorService.execute(() -> sleepSeconds(10));

        return executorService;
    }

    private static void sleepSeconds(long seconds) {
        try {
            System.out.println("* " + Thread.currentThread().getName() + " *");
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
