package com.zhym.improve.executors;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @description:
 * @author: zhym
 * @time: 2020/12/8 0008 0:04
 */
public class ThreadPoolExecutorTask {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = new ThreadPoolExecutor(10, 20, 30,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(10), r -> new Thread(r), new ThreadPoolExecutor.AbortPolicy());

        IntStream.rangeClosed(1, 20).boxed().forEach(i ->
            executorService.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(20);
                    System.out.println(Thread.currentThread().getName() + " [ " + i + " ] finished done. ");
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                }
            })
        );
        List<Runnable> runnableList = null;
        //executorService.shutdown();
        try {
            runnableList = executorService.shutdownNow();
            System.out.println("=============over================");

        }catch (Exception e) {
            e.printStackTrace();
        }

        //executorService.awaitTermination(1, TimeUnit.HOURS);
        System.out.println("=============================");
        System.out.println(runnableList);
        System.out.println(runnableList.size());
    }
}
