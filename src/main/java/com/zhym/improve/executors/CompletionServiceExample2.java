package com.zhym.improve.executors;

import com.zhym.basic.activeobject.FutureResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @description:
 * @author: zhym
 * @time: 2020/12/9 0009 20:40
 */
public class CompletionServiceExample2 {

    private static final Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        tesExecutorCompletionServiceRunnable();
    }

    public static void main2(String[] args) throws InterruptedException, ExecutionException {
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

        ExecutorCompletionService<Integer> completionService = new ExecutorCompletionService<>(executorservice);
        List<Future<Integer>> futures = new ArrayList<>();
        callableList.stream().forEach(callable -> futures.add(completionService.submit(callable)));
        //Future<Integer> future;
        /**
         * 这样可以哪个任务完成就获取哪个任务的结果
         */
        /*while ((future = completionService.take()) != null) {
            System.out.println(future.get());
        }*/

        /**
         * poll()不是一个阻塞方法，有可能拿到null，要注意；
         * 可以使用超时的poll
         */
        //Future<Integer> future = completionService.poll();
        //System.out.println(future);
        //可以使用超时的poll
        System.out.println(completionService.poll(12, TimeUnit.SECONDS).get());
    }

    private static void tesExecutorCompletionServiceRunnable() throws InterruptedException, ExecutionException {
        ExecutorService executorservice = Executors.newFixedThreadPool(2);
        ExecutorCompletionService<Event> completionService = new ExecutorCompletionService<>(executorservice);
        final Event event = new Event(1);
        completionService.submit(new MyTask(event), event);
        System.out.println(completionService.take().get().result);
    }
    private static class MyTask implements Runnable {

        private final Event event;

        public MyTask(Event event) {
            this.event = event;
        }

        @Override
        public void run() {
            sleep(random.nextInt(10));
            event.setResult("过");
        }
    }
    private static class Event {
        final private int eventId;

        private String result;

        public Event(int eventId) {
            this.eventId = eventId;
        }

        public int getEventId() {
            return eventId;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }

    private static void sleep(long second) {

        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
