package com.zhym.guarded;

import java.util.Random;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/16 0016 23:00
 */
public class ServiceThread extends Thread{

    private final RequestQueue queue;

    private final Random random;

    private volatile boolean flag = true;

    public ServiceThread(RequestQueue queue) {
        this.queue = queue;
        random = new Random(System.currentTimeMillis());
    }

    @Override
    public void run() {
        while (flag) {
            Request request = queue.getRequest();
            if (null == request) {
                System.out.println("Service the empty request.");
                continue;
            }
            System.out.println("Server ->" + request.getValue());
            try {
                Thread.sleep(random.nextInt(1_000));
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    public void close() {
        this.flag = false;
        this.interrupt();
    }
}
