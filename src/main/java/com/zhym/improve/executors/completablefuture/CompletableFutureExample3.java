package com.zhym.improve.executors.completablefuture;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @description:
 * @author: zhym
 * @time: 2020/12/11 0011 20:41
 */
public class CompletableFutureExample3 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello").toCompletableFuture();
                future.thenRunAsync(() -> System.out.println("Done"));
        /*final CompletableFuture<Integer> future = CompletableFuture.supplyAsync((Supplier<String>) () -> {
            throw new RuntimeException(" not get the value. ");
        }).handleAsync((s, t) -> {
            Optional.of(t).ifPresent(e -> System.out.println(e.getCause()));
            return s == null ? 0 : s.length();
            //return s.length();
        });*/
        //.whenComplete((v, t) -> System.out.println(v));
        //这里的Async同步/异步指的是方法同步或异步，即按照代码同步的时候按照方法先后执行，异步则与多个线程执行多个方法类似
        //future.whenComplete((v, t) -> {
        //.thenApply(String::length);
       /* .thenApplyAsync(s -> {
            System.out.println("---------先-------------");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("---------后-------------");
            return s.length();
        });*/
        /*future.whenCompleteAsync((v, t) -> {
                    System.out.println("---------先-------------");
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("---------后-------------");
                });

        //Thread.currentThread().join();*/
        System.out.println(future.get());
        Thread.currentThread().join();

    }
}
