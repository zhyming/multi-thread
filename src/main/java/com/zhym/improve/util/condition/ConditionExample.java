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
public class ConditionExample {

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
            //conditon.signal();  //monitor.notify()
            conditon.signalAll();
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
           // TimeUnit.SECONDS.sleep(1);
            System.out.println(" C: " + Thread.currentThread().getName() + " : "  + data);
            notUse = false;
            conditon.signalAll();

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

        IntStream.rangeClosed(0, 2).forEach(i -> {
            new Thread(()-> {
                for (;;) {
                    useData();
                }
            }).start();
        });

    }


}
