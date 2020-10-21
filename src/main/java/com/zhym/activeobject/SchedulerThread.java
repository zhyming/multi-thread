package com.zhym.activeobject;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/22 0022 0:59
 */
public class SchedulerThread extends Thread {

    private final ActivationQueue activationQueue;

    public SchedulerThread(ActivationQueue activationQueue) {
        this.activationQueue = activationQueue;
    }

    public void invoke(MethodRequest request) {
        this.activationQueue.put(request);
    }

    @Override
    public void run() {
        while (true) {
            activationQueue.take().execute();
        }
    }
}
