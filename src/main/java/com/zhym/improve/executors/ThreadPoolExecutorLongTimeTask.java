package com.zhym.improve.executors;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @description:
 * @author: zhym
 * @time: 2020/12/8 0008 0:37
 */
public class ThreadPoolExecutorLongTimeTask {

    public static void main(String[] args) throws InterruptedException {
        //设置执行的线程为守护线程，当主线程结束时，关闭他们
        ExecutorService executorService = new ThreadPoolExecutor(10, 20, 30,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(10),
                r -> {
                    Thread t = new Thread(r);
                    t.setDaemon(true);
                    return t;
                }, new ThreadPoolExecutor.AbortPolicy());

        IntStream.rangeClosed(1, 10).boxed().forEach(i ->
                executorService.submit(() -> {
                    //有危险，仅供参考，表面这里需要执行很久，但是无法interrupt。
                    while (true) {

                    }
                }));

        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("================= start the sequence work ==========================");
    }
}
