package com.zhym.improve.util.semaphore;

import java.util.Collection;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: zhym
 * @time: 2020/11/24 0024 0:02
 */
public class SemaphoreAPITest2 {

    public static void main(String[] args) throws InterruptedException {

        final MySemaphore semaphore = new MySemaphore(5);

        Thread t1 = new Thread(() -> {
            try {
                semaphore.drainPermits();
                System.out.println(semaphore.availablePermits());
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                semaphore.release();
            }
            System.out.println(" T1 Finished");
        });
        t1.start();

        TimeUnit.SECONDS.sleep(1);
        Thread t2 = new Thread(() -> {
            try {
                //acquireUninterruptibly -- 不中断异常
                semaphore.acquireUninterruptibly();
                // TimeUnit.SECONDS.sleep(5);
            } finally {
                semaphore.release();
            }
            System.out.println(" T2 Finished");
        });
        t2.start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println(semaphore.hasQueuedThreads());
        Collection<Thread> waitingThreads = semaphore.getWaitingThread();
        for (Thread t : waitingThreads) {
            System.out.println(t);
        }

    }

    static class MySemaphore extends Semaphore {

        public MySemaphore(int permits) {
            super(permits);
        }

        public MySemaphore(int permits, boolean fair) {
            super(permits, fair);
        }

        public Collection<Thread> getWaitingThread() {
            return super.getQueuedThreads();
        }
    }
}
