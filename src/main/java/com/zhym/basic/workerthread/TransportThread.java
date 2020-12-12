package com.zhym.basic.workerthread;

import java.util.Random;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/21 0021 23:59
 */
public class TransportThread extends Thread{

    private final Channel channel;

    private static final Random random = new Random(System.currentTimeMillis());

    public TransportThread(String name, Channel channel) {
        super(name);
        this.channel = channel;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; true; i++) {
                Request request = new Request(getName(), i);
                this.channel.put(request);
                Thread.sleep(random.nextInt(1_000));
            }
        }catch (Exception e) {

        }
    }
}
