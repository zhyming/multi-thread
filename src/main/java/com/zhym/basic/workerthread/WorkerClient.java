package com.zhym.basic.workerthread;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/22 0022 0:09
 */
public class WorkerClient {

    public static void main(String[] args) {
        final Channel channel = new Channel(5);
        channel.startWorker();

        new TransportThread("张三", channel).start();
        new TransportThread("李四", channel).start();
        new TransportThread("王五", channel).start();
    }
}
