package com.zhym.improve.executors;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @description:
 * @author: zhym
 * @time: 2020/12/9 0009 17:22
 */
public class ExecutorServiceExample4 {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        //testInvokeAny();
        //testInvokeAnyTimeOut();
        //testInvokeAll();
        //testInvokeAllTimeOut();
        //testSubmitRunnable();
        //testSubmitRunnableWithResult();
        testGetQueue();
    }

    /**
     * {@link ExecutorService#invokeAny(Collection)}
     * Note: The invokeAny will be block call
     * 其它线程的执行过程会被取消（如果其它线程没有结束的话）
     *
     */
    private static void testInvokeAny() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Callable<Integer>> callableList = IntStream.rangeClosed(1, 5).boxed().map(i -> (Callable<Integer>) () -> {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
            System.out.println(Thread.currentThread().getName() + " : " + i);
            return i;
        }).collect(Collectors.toList());
        Integer value = executorService.invokeAny(callableList);
        System.out.println(" =======finished=========== ");
        System.out.println(value);
    }

    private static void testInvokeAnyTimeOut() throws InterruptedException, ExecutionException, TimeoutException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Integer value = executorService.invokeAny(
                IntStream.rangeClosed(1, 5).boxed().map(i -> (Callable<Integer>) () -> {
                    TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(100));
                    System.out.println(Thread.currentThread().getName() + " : " + i);
                    return i;
                }).collect(Collectors.toList()), 3, TimeUnit.SECONDS
        );
        System.out.println(" =======finished=========== ");
        System.out.println(value);
    }

    /**
     * {@link ExecutorService#invokeAll(Collection)}
     * @throws InterruptedException
     */
    private static void testInvokeAll() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        executorService.invokeAll(
                IntStream.rangeClosed(1, 5).boxed().map(i -> (Callable<Integer>) () -> {
                    TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
                    System.out.println(Thread.currentThread().getName() + " : " + i);
                    return i;
                }).collect(Collectors.toList())
        ).stream().map(future -> {
            try {
                return future.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).forEach(System.out::println);
        System.out.println(" =======finished=========== ");
    }

    /**
     * {@link ExecutorService#invokeAll(Collection, long, TimeUnit)}
     * @throws InterruptedException
     */
    private static void testInvokeAllTimeOut() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        executorService.invokeAll(
                IntStream.rangeClosed(1, 5).boxed().map(i -> (Callable<Integer>) () -> {
                    TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
                    System.out.println(Thread.currentThread().getName() + " : " + i);
                    return i;
                }).collect(Collectors.toList()), 1, TimeUnit.SECONDS
        ).stream().map(future -> {
            try {
                return future.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).forEach(System.out::println);
        System.out.println(" =======finished=========== ");
    }

    /**
     * {@link ExecutorService#submit(Runnable)}
     */
    private static void testSubmitRunnable() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Future<?> future = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Object obj = future.get();
        System.out.println("R: " + obj);
    }

    /**
     * {@link ExecutorService#submit(Runnable, Object)}
     */
    private static void testSubmitRunnableWithResult() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        String result = "DONE";
        Future<String> future = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, result);
        System.out.println(future.get());
    }

    private static void testGetQueue() throws InterruptedException {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor)Executors.newFixedThreadPool(5);
        executorService.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println(" I need a thread.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        TimeUnit.SECONDS.sleep(5);
        executorService.getQueue().add(() -> System.out.println(" I am added directly into the queue."));

    }
}
