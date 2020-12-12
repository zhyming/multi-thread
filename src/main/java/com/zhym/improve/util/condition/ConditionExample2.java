package com.zhym.improve.util.condition;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * @description:
 * @author: zhym
 * @time: 2020/12/2 0002 0:47
 */
public class ConditionExample2 {

    /**
     * 1.不使用condition,仅使用lock
     *
     *
     */

    private static final ReentrantLock lock = new ReentrantLock();

    private static final Condition conditon = lock.newCondition();

    private static int data = 0;

    private static volatile boolean notUse = true;

    private static void buildData() {
        try {
            lock.lock();  // equals synchronized key word #monitor enter
            while (notUse) {
                conditon.await(); //monitor.wait()
            }
            data ++;
            System.out.println(" P: " + data);
            TimeUnit.SECONDS.sleep(1);
            notUse = true;
            conditon.signal();  //monitor.notify()
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();  //synchronized end  #monitor end
        }
    }

    private static void useData() {
        try {
            lock.lock();
            while (!notUse) {
                conditon.await();
            }
           TimeUnit.SECONDS.sleep(1);
            System.out.println(" C: " + Thread.currentThread().getName() + " : "  + data);
            notUse = false;
            conditon.signal();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    public static void main(String[] args) {

        new Thread(() -> {
            for (;;) {
                buildData();
            }
        }).start();

        new Thread(()-> {
            for (;;) {
                useData();
            }
        }).start();
    }


}
