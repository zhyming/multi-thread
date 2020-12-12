package com.zhym.improve.atomic.fileupdater;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @description:
 * @author: zhym
 * @time: 2020/11/6 0006 22:12
 */
public class AtomicIntegerFieldUpdaterLockTest {

    private volatile int i;

    private AtomicIntegerFieldUpdater<AtomicIntegerFieldUpdaterLockTest> updater = AtomicIntegerFieldUpdater.newUpdater(AtomicIntegerFieldUpdaterLockTest.class, "i");

    public void update(int newValue) {
        updater.compareAndSet(this, i, newValue);
    }

    public int get() {
        return i;
    }

    public static void main(String[] args) {
        AtomicIntegerFieldUpdaterLockTest test = new AtomicIntegerFieldUpdaterLockTest();
        test.update(10);
        System.out.println(test.get());
    }
}
