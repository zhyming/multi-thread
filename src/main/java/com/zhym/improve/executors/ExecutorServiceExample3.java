package com.zhym.improve.executors;

import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * @description:
 * @author: zhym
 * @time: 2020/12/8 0008 19:18
 */
public class ExecutorServiceExample3 {

    public static void main(String[] args) throws InterruptedException {
        //test();
        //testAllowCoreTimeOut();
        //testRemove();
        //testPreStartCoreThread();
        //testPreStartAllCoreThread();
        testThreadPoolAdvice();
    }

    private static void test() throws InterruptedException {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor)Executors.newFixedThreadPool(5);
        System.out.println(executorService.getActiveCount());
        executorService.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        TimeUnit.MILLISECONDS.sleep(20);
        System.out.println(executorService.getActiveCount());
    }

    private static void testAllowCoreTimeOut() {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor)Executors.newFixedThreadPool(5);
        executorService.setKeepAliveTime(10, TimeUnit.SECONDS);
        executorService.allowCoreThreadTimeOut(true);
        IntStream.rangeClosed(1, 5).boxed().forEach(i -> executorService.execute(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        ));
        //executorService.
    }

    private static void testRemove() throws InterruptedException {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor)Executors.newFixedThreadPool(2);
        executorService.setKeepAliveTime(10, TimeUnit.SECONDS);
        executorService.allowCoreThreadTimeOut(true);
        IntStream.rangeClosed(1, 2).boxed().forEach(i -> executorService.execute(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(5);
                        System.out.println("==================== is finished!");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        ));
        TimeUnit.MILLISECONDS.sleep(20);
        Runnable r = () -> {
            System.out.println(" i will never be executed!");
        };
        executorService.execute(r);
        TimeUnit.MILLISECONDS.sleep(20);
        executorService.remove(r);
    }


    private static void testPreStartCoreThread(){
        ThreadPoolExecutor executorService = (ThreadPoolExecutor)Executors.newFixedThreadPool(2);
        System.out.println(executorService.getActiveCount());
        executorService.prestartCoreThread();
        System.out.println(executorService.getActiveCount());
        executorService.prestartCoreThread();
        System.out.println(executorService.getActiveCount());
        executorService.prestartCoreThread();
        System.out.println(executorService.getActiveCount());
    }

    private static void testPreStartAllCoreThread(){
        ThreadPoolExecutor executorService = (ThreadPoolExecutor)Executors.newFixedThreadPool(2);
        executorService.setMaximumPoolSize(3);
        System.out.println(executorService.getActiveCount());

        int number = executorService.prestartAllCoreThreads();
        System.out.println(number);
        System.out.println(executorService.getActiveCount());


    }

    private static void testThreadPoolAdvice() {
        ExecutorService executorService = new MyThreadPoolExecutor(1, 2, 30,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(1), r -> {
            Thread t = new Thread(r);
            return t;
        }, new ThreadPoolExecutor.AbortPolicy());
        executorService.execute(new MyRunnable(1) {
            @Override
            public void run() {
                //System.out.println("==========================================");
                System.out.println(1/0);
            }
        });

    }

    private abstract static class MyRunnable implements Runnable {
        private final int no;

        protected MyRunnable(int no) {
            this.no = no;
        }

        protected int getData() {
            return this.no;

        }


    }

    private static class MyThreadPoolExecutor extends ThreadPoolExecutor {

        public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        }

        @Override
        protected void beforeExecute(Thread t, Runnable r) {
            System.out.println(" init the " + ((MyRunnable)r).getData());
        }

        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            if (null == t) {
                System.out.println(" successful " + ((MyRunnable)r).getData());
            }else {
                t.printStackTrace();
            }
        }
    }
}
