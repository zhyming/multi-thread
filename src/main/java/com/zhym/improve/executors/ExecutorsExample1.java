package com.zhym.improve.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @description:
 * @author: zhym
 * @time: 2020/12/8 0008 0:53
 */
public class ExecutorsExample1 {

    public static void main(String[] args) throws InterruptedException {
        //useCachedThreadPool();
        //useFixedSizePool();
        useSinglePool();
    }

    /**
     * 与普通直接创建一个线程Thread区别：
     * 1.Thread工作完后生命周期结束，singleThread可以复用
     * 2.Thread 不能当前任务submit到queue里，但是singleThread可以
     *
     *
     */
    private static void useSinglePool() throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        IntStream.rangeClosed(1, 10).boxed().forEach(i -> executorService
                .execute(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " [ "+ i + " ] ");
                }));
    }

    private static void useFixedSizePool() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        execute(executorService);
        executorService.shutdown();
    }

    private static void useCachedThreadPool() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        System.out.println(((ThreadPoolExecutor) executorService).getActiveCount());
        executorService.execute(() -> System.out.println("====================="));
        System.out.println(((ThreadPoolExecutor) executorService).getActiveCount());

        execute(executorService);

    }

    private static void execute(ExecutorService executorService) throws InterruptedException {
        System.out.println(((ThreadPoolExecutor) executorService).getActiveCount());
        IntStream.rangeClosed(1, 100).boxed().forEach(i -> executorService
                .execute(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " [ "+ i + " ] ");
                }));

        TimeUnit.SECONDS.sleep(1);
        System.out.println(((ThreadPoolExecutor) executorService).getActiveCount());
    }

}
