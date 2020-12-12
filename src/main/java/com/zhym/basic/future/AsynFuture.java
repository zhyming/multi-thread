package com.zhym.basic.future;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/15 0015 23:34
 */
public class AsynFuture<T> implements Future<T> {
    private volatile boolean done = false;

    private T result;

    public void done(T result) {
        synchronized (this) {
            this.result = result;
            this.done = true;
            this.notifyAll();
        }
    }

    @Override
    public T get() throws InterruptedException {
        synchronized (this) {
            while (!done) {
                this.wait();
            }
        }

        return result;
    }
}
