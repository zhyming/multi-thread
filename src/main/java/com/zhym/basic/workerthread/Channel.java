package com.zhym.basic.workerthread;


import java.util.Arrays;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/21 0021 23:41
 */
public class Channel {

    private final static int MAX_REQUEST = 100;

    private final Request[] requestQueue;

    private int head;

    private int tail;

    private int count;

    private final WorkerThread[] workerPool;

    public Channel(int workers) {
        this.requestQueue = new Request[MAX_REQUEST];
        this.head = 0;
        this.tail = 0;
        this.count = 0;
        this.workerPool = new WorkerThread[workers];
        this.init();
    }

    private void init() {
        for(int i = 0; i < workerPool.length; i ++) {
            workerPool[i] = new WorkerThread("Worker-" + i, this);
        }
    }

    /**
     * push switch to start all of workers to worker
     */
    public void startWorker() {
        Arrays.asList(workerPool).forEach(WorkerThread::start);
    }

    public synchronized void put(Request request) {
        while (count > requestQueue.length) {
            try {
                this.wait();
            }catch (Exception e) {

            }
        }

        this.requestQueue[tail] = request;
        this.tail = (tail + 1) % requestQueue.length;
        this.count ++;
        this.notifyAll();
    }

    public synchronized Request take() {
        while (count <= 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
        }

        Request request = this.requestQueue[head];
        this.head = (this.head + 1) % this.requestQueue.length;
        this.count --;
        this.notifyAll();
        return request;
    }
}
