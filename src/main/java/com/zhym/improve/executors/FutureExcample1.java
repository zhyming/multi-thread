package com.zhym.improve.executors;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @description:
 * @author: zhym
 * @time: 2020/12/9 0009 19:10
 */
public class FutureExcample1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        //testFutureGet();
        //testFutureGetTimeOut();
        //testIsDown();
        //testIsCancel();
        //testIsCancelled();
        testIsCancelled2();
    }

    private static void testFutureGet() throws ExecutionException, InterruptedException {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor)Executors.newCachedThreadPool();

        Future<Integer> future = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;
        });
        //===========================
        System.out.println("==========print quickly===============");

        Thread thread = Thread.currentThread();
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            thread.interrupt();
        }).start();

        Integer result = future.get();
        System.out.println(" ********************** " + result);
    }

    /**
     * Future 设置了timeout了，时间到了如果没有结果线程还是会继续执行
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     */
    private static void testFutureGetTimeOut() throws InterruptedException, ExecutionException, TimeoutException {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor)Executors.newCachedThreadPool();

        Future<Integer> future = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
                System.out.println(" --------继续执行---------");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;
        });
        //===========================
        Integer result = future.get(5, TimeUnit.SECONDS);
        System.out.println(result);
    }

    private static void testIsDown() throws ExecutionException, InterruptedException {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor)Executors.newCachedThreadPool();

        Future<Integer> future = executorService.submit(() -> {
            /*try {
                TimeUnit.SECONDS.sleep(10);
                System.out.println(" --------继续执行---------");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;*/
            throw new RuntimeException();
        });

        try {
            Integer result = future.get();
            System.out.println(result);
        }catch (Exception e) {
            System.out.println(" is done-> " + future.isDone());
        }

        //System.out.println(result + " is done-> " + future.isDone());
        //System.out.println(future.isDone());
    }

    /**
     *
     * future 的cancel方法会触发线程中的中断异常，所以可以在线程中进行中断捕获，或者线程中有其他的的中断异常捕获则会影响
     * cancel预期的效果
     * @throws ExecutionException
     * @throws InterruptedException
     */

    private static void testIsCancel() throws ExecutionException, InterruptedException {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor)Executors.newCachedThreadPool();
        AtomicBoolean running = new AtomicBoolean(true);
        Future<Integer> future = executorService.submit(() -> {
            /*try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            while (running.get()) {
            }
            return 10;
        });
        TimeUnit.SECONDS.sleep(1);
        //System.out.println(future.get());
        System.out.println(future.cancel(true));
        //System.out.println(future.cancel(true));
        System.out.println(future.isDone());
        System.out.println(future.isCancelled());
    }

    /**
     * future 调用cancel方法线程还是会继续执行完；
     * 如果要结束线程可以在线程中进行while（！Thread.interrupted）判断
     *
     *
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void testIsCancelled() throws ExecutionException, InterruptedException {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor)Executors.newCachedThreadPool(new ThreadFactory() {
            /**
             * 在线程池中自定义执行线程，以达到使用cancel结束线程的的目的
             * @param r
             * @return
             */
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread();
                t.setDaemon(true);
                return t;
            }
        });
        AtomicBoolean running = new AtomicBoolean(true);
        Future<Integer> future = executorService.submit(() -> {
            /*try {
                TimeUnit.SECONDS.sleep(10);
                System.out.println("----------first print----------");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }/*
            while (running.get()) {
            }
            /*while (!Thread.interrupted()) {

            }*/
            while (running.get()) {
            }
            System.out.println("----------second print----------");
            return 10;
        });
        TimeUnit.SECONDS.sleep(1);
        //System.out.println(future.get());
        System.out.println(future.cancel(true));
        //System.out.println(future.cancel(true));
        System.out.println(future.isDone());
        System.out.println(future.isCancelled());
    }

    private static void testIsCancelled2() throws ExecutionException, InterruptedException {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor)Executors.newCachedThreadPool();
        AtomicBoolean running = new AtomicBoolean(true);
        Future<Integer> future = executorService.submit(() -> {
            while (!Thread.interrupted()) {

            }
            System.out.println("----------second print----------");
            return 10;
        });
        TimeUnit.SECONDS.sleep(1);
        System.out.println(future.cancel(true));
        System.out.println(future.isDone());
        System.out.println(future.isCancelled());
        TimeUnit.SECONDS.sleep(2);
        /**
         * 如果future已经被cancel，则不能获取值get(),会抛出CancellationException
         */
        System.out.println(future.get());
    }
}
