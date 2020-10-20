package com.zhym.concurrent.waitset;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/11 0011 22:31
 */
public class WaitSet {

    private static final Object LOCK = new Object();

    private static void work() {
        synchronized (LOCK) {
            System.out.println("begin 。。。。");
            try {
                System.out.println("Thread will coming");
                LOCK.wait();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread will out.");
        }
    }

    /**
     * 1.所有对象都有一个wait set ，用来存放调用了该对象wait方法之后进入block状态线程
     * 2.线程被notify之后，不一定立即得到执行
     * 3.线程从wait set中被唤醒顺序不是FIFO，随机的
     * 4.线程唤醒后必须重新获取锁，且得到锁后会继续上次wait处（线程有记录wait地址）继续往下执行
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {

        new Thread() {
            @Override
            public void run() {
                work();
            }
        }.start();
        Thread.sleep(1_000);
        synchronized (LOCK) {
            LOCK.notify();
        }

        /*IntStream.rangeClosed(1, 10).forEach(i ->
                new Thread(String.valueOf(i)) {
                    @Override
                    public void run() {
                        synchronized (LOCK) {
                            try {
                                Optional.of(Thread.currentThread().getName() + "will come to wait set").ifPresent(System.out::println);
                                LOCK.wait();
                                Optional.of(Thread.currentThread().getName() + "will leave to wait set").ifPresent(System.out::println);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start());

        Thread.sleep(3_000);

        IntStream.rangeClosed(1, 10).forEach(i -> {
            synchronized (LOCK) {
                LOCK.notify();
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
*/
    }
}
