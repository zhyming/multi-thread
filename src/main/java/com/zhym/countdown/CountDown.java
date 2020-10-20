package com.zhym.countdown;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/20 0020 0:57
 */
public class CountDown {

    private final int total;

    private int counter;

    public CountDown(int total) {
        this.total = total;
    }

    public void down() {
        synchronized (this) {
            this.counter ++;
            this.notifyAll();
        }

    }

    public void await() throws InterruptedException {
        synchronized (this) {
            while (counter != total) {
                this.wait();
            }
        }
    }
}
