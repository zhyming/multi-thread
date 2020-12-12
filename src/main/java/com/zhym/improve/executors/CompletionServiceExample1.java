package com.zhym.improve.executors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @description:
 * @author: zhym
 * @time: 2020/12/9 0009 20:18
 */
public class CompletionServiceExample1 {

    private static final Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //futureDefect1();
        futureDefect2();
    }

    /**
     * future：缺点1 没有回调callback
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void futureDefect1() throws ExecutionException, InterruptedException {
        ExecutorService executorservice = Executors.newFixedThreadPool(2);

        Future<Integer> future = executorservice.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 100;
        });
        System.out.println("========================");
        future.get();
    }

    /**
     * 缺陷2，futures里头不知道哪个对应哪个任务执行结果
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private static void futureDefect2() throws InterruptedException, ExecutionException {
        ExecutorService executorservice = Executors.newFixedThreadPool(2);
        List<Callable<Integer>> callableList = Arrays.asList(() -> {
            int bound = random.nextInt(10);
            sleep(random.nextInt(bound));
            System.out.println(" finished time -> " + bound);
            return bound;
        }, () -> {
            int bound = random.nextInt(10);
            System.out.println(" finished time -> " + bound);
            sleep(random.nextInt(bound));
            return bound;
        });
        //List<Future<Integer>> futures = executorservice.invokeAll(callableList);
        List<Future<Integer>> futures = new ArrayList<>();
        /**
         * 解决方法：可以按顺序执行，结果放入future的list里
         */
        futures.add(executorservice.submit(callableList.get(0)));
        futures.add(executorservice.submit(callableList.get(1)));
        Integer v1 = futures.get(1).get();
        System.out.println(v1);

        Integer v0 = futures.get(0).get();
        System.out.println(v0);

    }

    private static void sleep(long second) {

        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
