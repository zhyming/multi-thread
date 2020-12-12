package com.zhym.improve.util.condition;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * @description:
 * @author: zhym
 * @time: 2020/12/2 0002 21:47
 */
public class ConditionExample3 {

    private static final ReentrantLock lock = new ReentrantLock();

    private static final Condition PRODUCE_COND = lock.newCondition();

    private static final Condition CONSUME_COND = lock.newCondition();

    private static final LinkedList<Long> TIMESTAMP_POOL = new LinkedList<>();

    private static final int MAX_CAPACITY = 100;

    private static void produce() {

        try {
            lock.lock();
            while (TIMESTAMP_POOL.size() >= MAX_CAPACITY) {
                PRODUCE_COND.await();
            }
            System.out.println(" PRODUCE_COND->waitQueueLength :  " + lock.getWaitQueueLength(PRODUCE_COND));
            long value = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + " P: " + value );
            TIMESTAMP_POOL.addFirst(value);

            CONSUME_COND.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    private static void consume() {

        try {
            lock.lock();
            while (TIMESTAMP_POOL.isEmpty()) {
                CONSUME_COND.await();
            }
            System.out.println(" CONSUME_COND->waitQueueLength :  " + lock.getWaitQueueLength(CONSUME_COND));
            long value = TIMESTAMP_POOL.removeFirst();
            System.out.println(Thread.currentThread().getName() + " C: " + value);

            PRODUCE_COND.signalAll();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    private static void sleep(long second) {

        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static void beginProduce(int i) {
        new Thread(() -> {
            for (;;) {
                produce();
                sleep(2);
            }
        }, "P-" + i).start();
    }

    private static void beginConsume(int i) {
        new Thread(() -> {
            for (;;) {
                consume();
                sleep(3);
            }
        }, "C-" + i).start();
    }

    public static void main(String[] args) throws InterruptedException {

        IntStream.rangeClosed(0, 5).boxed().forEach(ConditionExample3::beginProduce);
        IntStream.rangeClosed(0, 12).boxed().forEach(ConditionExample3::beginConsume);

        for (;;) {
            TimeUnit.SECONDS.sleep(5) ;
            System.out.println("===================");
            //System.out.println(" PRODUCE_COND->waitQueueLength :  " + lock.getWaitQueueLength(PRODUCE_COND));
            //System.out.println(" CONSUME_COND->waitQueueLength :  " + lock.getWaitQueueLength(CONSUME_COND));
            //System.out.println(" PRODUCE_COND->hasWaiter :  " + lock.hasWaiters(PRODUCE_COND));
            //System.out.println(" CONSUME_COND->hasWaiter :  " + lock.hasWaiters(CONSUME_COND));
        }

    }
}
