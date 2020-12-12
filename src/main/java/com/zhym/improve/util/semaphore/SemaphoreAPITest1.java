package com.zhym.improve.util.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: zhym
 * @time: 2020/11/23 0023 23:28
 */
public class SemaphoreAPITest1 {

    /**
     * connection pool
     *
     * 多个线程获取连接，连接可当做信号量处理
     *
     * registry callback
     *
     *
     *
     * @param args
     */

    /**
     *
     * acquire
     * release
     * availablePermit
     * queueLength
     * acquireUninterruptibly
     *
     *
     * @param args
     * @throws InterruptedException
     */

    public static void main(String[] args) throws InterruptedException {
        final Semaphore semaphore = new Semaphore(1);

        Thread t1 = new Thread(() -> {
            try {
                semaphore.acquire();
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                semaphore.release();
            }
            System.out.println(" T1 Finished");
        });
        t1.start();

        TimeUnit.MICROSECONDS.sleep(50);
        Thread t2 = new Thread(() -> {
            try {
                //acquireUninterruptibly -- 不中断异常
                final boolean success = semaphore.tryAcquire();
                System.out.println(success ? "Successful" : "Failure");
            }  finally {
                semaphore.release();
            }
            System.out.println(" T2 Finished");
        });
        t2.start();
        TimeUnit.MICROSECONDS.sleep(50);
        t2.interrupt();

    }

    public static void main1(String[] args) throws InterruptedException {
        final Semaphore semaphore = new Semaphore(2);

        for (int i = 0; i < 3; i ++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " in. ");
                try {
                    semaphore.acquire(2);
                    System.out.println(Thread.currentThread().getName() + " get the semaphore. ");
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release(1);
                }
                System.out.println(Thread.currentThread().getName() + " out. ");
            }).start();
        }

        while (true) {
            System.out.println("AP -> " + semaphore.availablePermits());
            System.out.println("QL -> " + semaphore.getQueueLength());
            System.out.println(" ======================================== ");
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
