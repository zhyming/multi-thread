package com.zhym.improve.executors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @description:
 * @author: zhym
 * @time: 2020/12/10 0010 1:53
 */
public class ComplexExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //func1();
        //func2();
        func3();
    }

    private static void func3() throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        List<Callable<Integer>> tasks = IntStream.rangeClosed(1, 5).boxed()
                .map(MyTask::new)
                .collect(Collectors.toList());
        CompletionService<Integer> completionService = new ExecutorCompletionService<>(executorService);
        tasks.forEach(completionService::submit);
        TimeUnit.SECONDS.sleep(18);
        executorService.shutdownNow();
        tasks.stream().filter(callable -> !((MyTask)callable).isSuccess()).forEach(System.out::println);
    }

    private static void func2() throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        List<Runnable> tasks = IntStream.rangeClosed(1, 5).boxed()
                .map(ComplexExample::toTask)
                .collect(Collectors.toList());
        CompletionService<Object> completionService = new ExecutorCompletionService<>(executorService);
        tasks.forEach(r -> completionService.submit(Executors.callable(r)));

        TimeUnit.SECONDS.sleep(13);
        List<Runnable> runnableList = executorService.shutdownNow();
        System.out.println(runnableList);

    }

    private static void func1() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        List<Runnable> tasks = IntStream.rangeClosed(1, 5).boxed()
                .map(ComplexExample::toTask)
                .collect(Collectors.toList());
        //List<Future<?>> futureList = new ArrayList<>();
        CompletionService<Object> completionService = new ExecutorCompletionService<>(executorService);

        //tasks.forEach(r -> futureList.add(executorService.submit(r)));

        tasks.forEach(r -> completionService.submit(Executors.callable(r)));

        Future<?> future;

        while ((future = completionService.take()) != null) {
            System.out.println(future.get());
        }

        //无法正确拿到先执行完的任务，如果刚好先拿的是执行时间最久的任务，
        // 那么获取结果的所有都会阻塞住，所有可以使用ExecutorCompletionService解决
        /*futureList.get(4).get();
        System.out.println("========4=======");
        futureList.get(3).get();
        System.out.println("========3=======");
        futureList.get(2).get();
        System.out.println("========2=======");
        futureList.get(1).get();
        System.out.println("========1=======");
        futureList.get(0).get();
        System.out.println("========0=======");*/
    }

    private static class MyTask implements Callable<Integer> {

        private final Integer value;

        private boolean success = false;

        public MyTask(Integer value) {
            this.value = value;
        }

        @Override
        public Integer call() throws Exception {

            System.out.printf(" The Task [%d] will be executed.\n", value);
            TimeUnit.SECONDS.sleep(value * 5 + 10);
            System.out.printf(" The Task [%d] execute be done.\n", value);
            success = true;
            return value;
        }

        public boolean isSuccess() {
            return success;
        }
    }

    private static Runnable toTask(int i) {

        return () -> {
            try {
                System.out.printf(" The Task [%d] will be executed.\n", i);
                TimeUnit.SECONDS.sleep(i * 5 + 10);
                System.out.printf(" The Task [%d] execute be done.\n", i);
            } catch (InterruptedException e) {
                System.out.printf(" The Task [%d] be exception.\n", i);
            }
        };

    }
}
