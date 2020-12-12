package com.zhym.improve.util.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: zhym
 * @time: 2020/11/23 0023 23:09
 */
public class SemaphoreTest {

    public static void main(String[] args) {

        final SemaphoreLock lock = new SemaphoreLock();

        for (int i = 0; i < 2; i ++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " is running. ");
                    lock.lock();
                    System.out.println(Thread.currentThread().getName() + " get the #SemaphoreLock. ");
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
                System.out.println(Thread.currentThread().getName() + " release the #SemaphoreLock. ");
            }).start();
        }
    }

    static class SemaphoreLock {

        private final Semaphore semaphore = new Semaphore(1);

        public void lock() throws InterruptedException {
            semaphore.acquire();
        }

        public void unlock() {
            semaphore.release();
        }

    }
}
