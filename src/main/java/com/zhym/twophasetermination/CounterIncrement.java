package com.zhym.twophasetermination;

import java.util.Random;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/20 0020 23:47
 */
public class CounterIncrement extends Thread{

    private volatile boolean terminated = false;

    private int counter = 0;

    private final Random random = new Random(System.currentTimeMillis());

    @Override
    public void run() {
        try {
            while (!terminated) {
                System.out.println(Thread.currentThread().getName() + " " + counter ++);
                Thread.sleep(random.nextInt(1_000));
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        } finally{
            this.clean();
        }

    }

    private void clean() {

        System.out.println(" do some clean work for the second phase. current counter -> " + this.counter);
    }

    public void close() {
        this.terminated = true;
        this.interrupt();
    }
}
