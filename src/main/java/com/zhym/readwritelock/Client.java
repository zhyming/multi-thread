package com.zhym.readwritelock;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/14 0014 23:50
 */
public class Client {

    public static void main(String[] args) {
        final SharedData data = new SharedData(10);
        new ReaderWorker(data).start();
        new ReaderWorker(data).start();
        new ReaderWorker(data).start();
        new ReaderWorker(data).start();
        new ReaderWorker(data).start();
        new WriteWorker(data, "QWERWEASDFSDSDDDTTT").start();
        new WriteWorker(data, "qwerweasdfsdsdddttt").start();
    }
}
