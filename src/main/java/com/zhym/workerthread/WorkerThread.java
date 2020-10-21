package com.zhym.workerthread;

import java.util.Random;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/21 0021 23:43
 */
public class WorkerThread extends Thread{

    private final Channel channel;

    private static final Random random = new Random(System.currentTimeMillis());


    public WorkerThread(String name, Channel channel) {

        super(name);
        this.channel = channel;

    }

    @Override
    public void run() {
        while (true) {
            channel.take().execute();
            try {
                Thread.sleep(random.nextInt(1_000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
