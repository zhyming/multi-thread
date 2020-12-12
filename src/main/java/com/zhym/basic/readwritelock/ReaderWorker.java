package com.zhym.basic.readwritelock;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/14 0014 23:47
 */
public class ReaderWorker extends Thread {

    private final SharedData data;

    public ReaderWorker(SharedData data) {
        this.data = data;
    }

    @Override
    public void run() {
        try {
            while (true) {
                char[] readBuffer = data.read();
                System.out.println(Thread.currentThread().getName() + " reads " + String.valueOf(readBuffer));
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
