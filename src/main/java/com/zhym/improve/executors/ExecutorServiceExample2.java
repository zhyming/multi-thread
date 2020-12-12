package com.zhym.improve.executors;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @description:
 * @author: zhym
 * @time: 2020/12/8 0008 18:20
 */
public class ExecutorServiceExample2 {

    public static void main(String[] args) throws InterruptedException {
        //testAbortPolicy();
        //testDiscardPolicy();
        //testCallerRunsPolicy();
        testDiscardOldestPolicy();
    }

    private static void testAbortPolicy() throws InterruptedException {
        ExecutorService executorService = new ThreadPoolExecutor(1, 2, 30,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(1), r -> {
            Thread t = new Thread(r);
            return t;
        }, new ThreadPoolExecutor.AbortPolicy());
        IntStream.rangeClosed(1, 3).boxed().forEach(i ->
            executorService.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            })
        );
        TimeUnit.SECONDS.sleep(1);
        //最大可以有两个线程，队列一个线程；所以当提交第四个线程的时候会受到拒绝策略，
        // 发生RejectedExecutionException异常，但是原先的线程还是会正常得到执行
        executorService.execute(() -> System.out.println("x"));
    }

    private static void testDiscardPolicy() throws InterruptedException {
        ExecutorService executorService = new ThreadPoolExecutor(1, 2, 30,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(1), r -> {
            Thread t = new Thread(r);
            return t;
        }, new ThreadPoolExecutor.DiscardPolicy());
        IntStream.rangeClosed(1, 3).boxed().forEach(i ->
                executorService.execute(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                })
        );
        TimeUnit.SECONDS.sleep(1);
        executorService.execute(() -> System.out.println("x"));
        System.out.println("==================================");
    }

    private static void testCallerRunsPolicy() throws InterruptedException {
        ExecutorService executorService = new ThreadPoolExecutor(1, 2, 30,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(1), r -> {
            Thread t = new Thread(r);
            return t;
        }, new ThreadPoolExecutor.CallerRunsPolicy());
        IntStream.rangeClosed(1, 3).boxed().forEach(i ->
                executorService.execute(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                })
        );
        TimeUnit.SECONDS.sleep(1);
        executorService.execute(() -> {
            System.out.println("x");
            System.out.println(Thread.currentThread().getName());
        });
        System.out.println("==================================");
    }

    private static void testDiscardOldestPolicy() throws InterruptedException {
        ExecutorService executorService = new ThreadPoolExecutor(1, 2, 30,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(1), r -> {
            Thread t = new Thread(r);
            return t;
        }, new ThreadPoolExecutor.DiscardOldestPolicy());
        IntStream.rangeClosed(1, 3).boxed().forEach(i ->
                executorService.execute(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(5);
                        System.out.println(" 我在里面 ！");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                })
        );
        TimeUnit.SECONDS.sleep(1);
        executorService.execute(() -> {
            System.out.println("x");
            System.out.println(Thread.currentThread().getName());
        });
        System.out.println("==================================");
    }
}
