package com.zhym.basic.guarded;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/16 0016 23:10
 */
public class SuspensionClient {

    public static void main(String[] args) throws InterruptedException {
        final RequestQueue queue = new RequestQueue();
        new ClientThread(queue, "张三").start();
        ServiceThread serviceThread = new ServiceThread(queue);
        serviceThread.start();

        Thread.sleep(10_000);

        serviceThread.close();
    }
}
