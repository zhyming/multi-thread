package com.zhym.improve.executors.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: zhym
 * @time: 2020/12/10 0010 3:46
 */
public class CompletableFutureExample2 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //supplyAsync();
       /* Future<?> future = runAsync();
        future.get();*/

        /*Future<Void> future = completed("HELLO");
        System.out.println(future.isDone());
        System.out.println("=========");*/


        //System.out.println(">>>>>>>>>>>" + anyOf().get());
        allOf();

        Thread.currentThread().join();
    }

    private static void create() {
        CompletableFuture<Object> completableFuture = new CompletableFuture();

        Object s = null;

        CompletableFuture.supplyAsync(() -> s);
    }

    private static void allOf() {
        CompletableFuture.allOf(CompletableFuture.runAsync(()-> {
                    try {
                        System.out.println(" 1 =========== start");
                        TimeUnit.SECONDS.sleep(5);
                        System.out.println(" 1 =========== end");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).whenComplete((v, t) -> System.out.println(" ======over======")),
                CompletableFuture.supplyAsync(()-> {
                    try {
                        System.out.println(" 2 =========== start");
                        TimeUnit.SECONDS.sleep(5);
                        System.out.println(" 2 =========== end");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "HELLO";
                }).whenComplete((v, t) -> System.out.println(v + " ======over======"))
        );
    }

    private static Future<?> anyOf() {
        return CompletableFuture.anyOf(CompletableFuture.runAsync(()-> {
            try {
                System.out.println(" 1 =========== start");
                TimeUnit.SECONDS.sleep(5);
                System.out.println(" 1 =========== end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).whenComplete((v, t) -> System.out.println(" ======over======")),
                CompletableFuture.supplyAsync(()-> {
                    try {
                        System.out.println(" 2 =========== start");
                        TimeUnit.SECONDS.sleep(5);
                        System.out.println(" 2 =========== end");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "HELLO";
                }).whenComplete((v, t) -> System.out.println(v + " ======over======"))
        );
    }

    private static Future<Void> completed(String data) {
        return CompletableFuture.completedFuture(data).thenAccept(System.out::println);
    }

    private static Future<?> runAsync() {
        return CompletableFuture.runAsync(()-> {
            try {
                System.out.println(" Obj =========== start");
                TimeUnit.SECONDS.sleep(5);
                System.out.println(" Obj =========== end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).whenComplete((v, t) -> System.out.println(" ========== over ==========="));
    }

    private static void supplyAsync() {

        CompletableFuture.supplyAsync(Object::new)
                .thenAcceptAsync(o -> {
                    try {
                        System.out.println(" Obj =========== start");
                        TimeUnit.SECONDS.sleep(5);
                        System.out.println(" Obj =========== " + o);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
        }).runAfterBoth(CompletableFuture.supplyAsync(() -> "HELLO")
                .thenAcceptAsync(s ->{
                    try {
                        System.out.println(" String =========== start");
                        TimeUnit.SECONDS.sleep(3);
                        System.out.println(" String =========== " + s);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }), () -> System.out.println(" ============= Finished.")
        );
    }
}
