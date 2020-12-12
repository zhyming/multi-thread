package com.zhym.improve.executors;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @description:
 * @author: zhym
 * @time: 2020/12/8 0008 3:07
 */
public class ExecutorServiceExample1 {

    public static void main(String[] args) throws InterruptedException {
        //isShutDown();
        //isTerminated();
        //executorRunnableError();
        executeRunnableTask();
    }

    /**
     * {@link ExecutorService#isShutdown()}
     */
    private static void isShutDown() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println("========OK==========");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(executorService.isShutdown());
        executorService.shutdown();
        System.out.println(executorService.isShutdown());
        executorService.execute(() -> System.out.println(" 我还能上车吗？"));
    }

    /**
     *
     * {@link ExecutorService#isTerminated()}
     * {@link ThreadPoolExecutor#isTerminating()}
     */
    private static void isTerminated() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
        System.out.println(executorService.isShutdown());
        System.out.println(executorService.isTerminated());
        System.out.println(((ThreadPoolExecutor)executorService).isTerminating());

    }

    private static void executorRunnableError() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10, new MyThreadFactory());
        IntStream.rangeClosed(1, 10).boxed().forEach(i -> executorService.execute(()-> System.out.println(1/0)));
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.MINUTES);
        System.out.println("========================");

    }

    private static class MyThreadFactory implements ThreadFactory {

        private final static AtomicInteger SEQ = new AtomicInteger();
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("My-Thread-" + SEQ.getAndIncrement());
            thread.setUncaughtExceptionHandler((t, cause) -> {
                System.out.println("The thread " + thread.getName() + " execute failed.");
                cause.printStackTrace();
                System.out.println("========================================");
            });
            return thread;
        }
    }

    /**
     *
     */
    private static void executeRunnableTask() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10, new MyThreadFactory());
        IntStream.rangeClosed(1, 10).boxed().forEach(i -> executorService.execute(
                new MyTask(i) {
                    @Override
                    protected void error(Throwable cause) {
                        System.out.println("The no:" + i + " failed, update status to Error.");
                    }

                    @Override
                    protected void done() {
                        System.out.println("The no:" + i + " successfully, update status to DONE.");
                    }

                    @Override
                    protected void doExecutor() {

                        if (i%3 == 0) {
                            int tmp = i/0;
                        }
                    }

                    @Override
                    protected void doInit() {

                        //doSomeThing
                    }
                }
        ));

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.MINUTES);
        System.out.println("========================");
    }

    private abstract static class MyTask implements Runnable {

        protected final int no;

        public MyTask(int no) {
            this.no = no;
        }

        @Override
        public void run() {
            try {
                this.doInit();
                this.doExecutor();
                this.done();
            }catch (Throwable cause) {
                this.error(cause);
            }
        }

        protected abstract void error(Throwable cause);

        protected abstract void done();

        protected abstract void doExecutor();

        protected abstract void doInit();
    }
}
