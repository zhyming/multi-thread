package com.zhym.improve.executors.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: zhym
 * @time: 2020/12/11 0011 21:42
 */
public class CompletableFutureExample4 {

    public static void main(String[] args) throws InterruptedException {
        //thenAcceptBoth();
        //acceptEither();
        //runAfterBoth();
        //runAfterEither();
        //combine();
        compose();
        Thread.currentThread().join();
    }
    /**
     * compose -- 先执行一个任务，后面使用上一个任务的结果再处理，返回第二个任务处理的结果
     */

    private static void compose() {
        CompletableFuture.supplyAsync(() -> {
            System.out.println(" start the compose-1");
            sleep(5);
            System.out.println(" end the compose-1");
            return "compose - 1";
        }).thenCompose(s -> CompletableFuture.supplyAsync(() -> {
            System.out.println(" start the compose-2");
            sleep(3);
            System.out.println(" end the compose-2");
            return s.length();
        })).thenAccept(System.out::println);
    }

    /**
     * combine -- 执行两个任务，在对两个任务对两个任务几个处理（BiFunction），返回一个结果
     * 与thenAcceptBoth不同的是 -- thenAcceptBoth的是BiConsumer
     */
    private static void combine() {
        CompletableFuture.supplyAsync(() -> {
            System.out.println(" start the combine-1");
            sleep(5);
            System.out.println(" end the combine-1");
            return "combine - 1";
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            System.out.println(" start the combine-2");
            sleep(5);
            System.out.println(" end the combine-2");
            return 100;
        }), (s, i) -> s.length() >i)
                .whenComplete((v, t) -> System.out.println(v));
    }

    /**
     * runAfterEither -- 执行两个任务，其中有一个执行完后再执行另外任务
     */
    private static void runAfterEither() {
        CompletableFuture.supplyAsync(() -> {
            System.out.println(" start the runAfterEither-1");
            sleep(5);
            System.out.println(" end the runAfterEither-1");
            return "runAfterEither - 1";
        }).runAfterEither(CompletableFuture.supplyAsync(() -> {
            System.out.println(" start the runAfterEither-2");
            sleep(3);
            System.out.println(" end the runAfterEither-2");
            return "runAfterEither - 2";
        }), () -> System.out.printf("DONE"));
    }

    /**
     * runAfterBoth -- 执行两个任务，都执行完后再执行另外任务
     */
    private static void runAfterBoth() {
        CompletableFuture.supplyAsync(() -> {
            System.out.println(" start the runAfterBoth-1");
            sleep(5);
            System.out.println(" end the runAfterBoth-1");
            return "runAfterBoth - 1";
        }).runAfterBoth(CompletableFuture.supplyAsync(() -> {
            System.out.println(" start the runAfterBoth-2");
            sleep(3);
            System.out.println(" end the runAfterBoth-2");
            return "runAfterBoth - 2";
        }), () -> System.out.printf("DONE"));
    }

    /**
     * acceptEither -- 取任一future执行结果，两个任务都会执行
     */
    private static void acceptEither() {
        CompletableFuture.supplyAsync(() -> {
            System.out.println(" start the acceptEither-1");
            sleep(5);
            System.out.println(" end the acceptEither-1");
            return "acceptEither - 1";
        }).acceptEither(CompletableFuture.supplyAsync(() -> {
            System.out.println(" start the acceptEither-2");
            sleep(5);
            System.out.println(" end the acceptEither-2");
            return "acceptEither - 2";
        }), System.out::println);
    }

    /**
     * thenAcceptBoth -- 取两个future执行结果
     */
    private static void thenAcceptBoth() {
        CompletableFuture.supplyAsync(() -> {
            System.out.println(" start the supplyAsync");
            sleep(5);
            System.out.println(" end the supplyAsync");
            return "thenAcceptBoth";
        }).thenAcceptBoth(CompletableFuture.supplyAsync(() -> {
            System.out.println(" start the thenAcceptBath");
            sleep(5);
            System.out.println(" end the thenAcceptBath");
            return 100;
        }), (s, i) -> System.out.println(s + " --- " + i));
    }

    private static void sleep(long seconds) {

        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
