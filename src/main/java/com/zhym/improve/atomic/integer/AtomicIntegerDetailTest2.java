package com.zhym.improve.atomic.integer;

/**
 * @description:
 * @author: zhym
 * @time: 2020/11/4 0004 23:47
 */
public class AtomicIntegerDetailTest2 {

    private final static CompareAndSetLock lock = new CompareAndSetLock();
    public static void main(String[] args) {
        for (int i = 0; i < 5; i ++) {
            new Thread(() -> {
                try {
                    doSomeThing2();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (AtomicException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private static void doSomeThing() throws InterruptedException {
        synchronized (AtomicIntegerDetailTest2.class) {
            System.out.println(Thread.currentThread().getName() + " get the locked");
            Thread.sleep(1000_000);
        }
    }

    private static void doSomeThing2() throws InterruptedException, AtomicException {
        try {
            lock.tryLock();
            System.out.println(Thread.currentThread().getName() + " get the locked");
            Thread.sleep(1000_000);
        }finally {
            lock.unlock();
        }
    }
}
