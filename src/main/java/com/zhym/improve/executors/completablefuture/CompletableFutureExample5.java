package com.zhym.improve.executors.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: zhym
 * @time: 2020/12/11 0011 22:34
 */
public class CompletableFutureExample5 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //getNow();
        //complete();
        //join();
        //completeExceptionally();
        //obtrudeException();
        final CompletableFuture<String> future = errorHandle();
        future.whenComplete((v, t) -> System.out.println(v));
        Thread.currentThread().join();
    }

    private static CompletableFuture<String> errorHandle() {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            sleep(5);
            System.out.println(" =========== i will be still process .. ");
            return "HELLO";
        });

        future1.thenApply(s -> {
            //sleep(10);
            Integer.parseInt(s);
            System.out.println(" ==============keep execute=============");
            return s + "张三";
        }).exceptionally(Throwable::getMessage).thenAccept(System.out::println);

        return future1;
    }

    /**
     * obtrudeException -- 不管任务有没有执行完，调用get直接抛出一个异常
     * @throws ExecutionException
     * @throws InterruptedException
     */

    private static void obtrudeException() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            //sleep(5);
            System.out.println(" =========== i will be still process .. ");
            return "HELLO";
        });
        sleep(1);
        future.obtrudeException(new Exception("i am error"));
        System.out.println(future.get());
    }

    /**
     * completeExceptionally -- 设置一个异常，当调用get方法的时候，任务还没执行完，则抛出；如果已经执行完了，get正常返回结果。
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void completeExceptionally() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            //sleep(5);
            System.out.println(" =========== i will be still process .. ");
            return "HELLO";
        });
        sleep(1);
        future.completeExceptionally(new RuntimeException());
        System.out.println(future.get());
    }

    /**
     * join 与get方法类似，区别在于不用去处理异常，它内部已经帮你封装好了
     */
    private static void join() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            sleep(5);
            System.out.println(" =========== i will be still process .. ");
            return "HELLO";
        });

        final String result = future.join();
        System.out.println(result);
    }

    /**
     * complete(T value) -- 完成任务，并把任务结果设为value；如果任务没有结束，则设置成功，返回true
     *                      如果任务已经完成，则设置失败，返回false。
     *                      如果任务还没执行，则会取消执行；如果任务正在执行中，则不会取消执行。
     * @throws ExecutionException
     * @throws InterruptedException
     */

    private static void complete() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            sleep(5);
            System.out.println(" =========== i will be still process .. ");
            return "HELLO";
        });
        sleep(1);
        boolean finished = future.complete("张三");
        System.out.println(finished);
        System.out.println(future.get());

    }

    /**
     * getNow(T valueIfAbsent) -- 返回valueIfAbsent，如果任务没有完成；如果已经完成了则返回任务结果。
     * @throws ExecutionException
     * @throws InterruptedException
     */

    private static void getNow() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            //sleep(5);
            return "HELLO";
        });
        sleep(1);
        String result = future.getNow("张三");

        System.out.println(result);
        System.out.println(future.get());
    }

    private static void sleep(long seconds) {

        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
