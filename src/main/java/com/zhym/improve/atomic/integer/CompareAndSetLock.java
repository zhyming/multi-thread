package com.zhym.improve.atomic.integer;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description:
 * @author: zhym
 * @time: 2020/11/4 0004 23:58
 */
public class CompareAndSetLock {
    private final AtomicInteger value = new AtomicInteger();
    private Thread threadLocal;

    public void tryLock() throws AtomicException {
        boolean success = value.compareAndSet(0, 1);
        if (!success) {
            throw new AtomicException(" Get the Lock failed ");
        }else {
            threadLocal = Thread.currentThread();
        }
    }

    public void unlock() {
        if (0 == value.get()) {
            return;
        }

        if (threadLocal == Thread.currentThread()) {
            value.compareAndSet(1, 0);
        }
    }

}
