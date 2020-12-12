package com.zhym.improve.executors;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * @description:
 * @author: zhym
 * @time: 2020/12/8 0008 0:53
 */
public class ExecutorsExample2 {

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newWorkStealingPool();
        //Optional.of(Runtime.getRuntime().availableProcessors()).ifPresent(System.out::println);

        List<Callable<String>> callableList = IntStream.rangeClosed(1, 20).boxed().map(i ->
                (Callable<String>) () -> {
                    System.out.println("Thread " + Thread.currentThread().getName());
                    sleep(2);
                    return "Task-" + i;
                }
        ).collect(toList());
        executorService.invokeAll(callableList).stream().map(future -> {
            try {
                return future.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).forEach(System.out::println);
    }

    /**
     *
     * @param seconds
     */
    private static void sleep(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
