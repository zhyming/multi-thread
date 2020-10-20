package com.zhym.readwritelock;

/**
 * @description: ReadWriteLock design pattern
 * @author: zhym
 * @time: 2020/10/14 0014 23:08
 */
public class ReadWriteLock {

    private int readingReaders = 0;

    private int waitingReaders = 0;

    private int writingWriters = 0;

    private int waitingWriters = 0;

    private boolean perferWriter = true;

    public ReadWriteLock() {
        this(true);
    }

    public ReadWriteLock(boolean perferWriter) {

        this.perferWriter = perferWriter;

    }

    public synchronized void readLock() throws InterruptedException {
        this.waitingReaders ++;
        try {
            while (writingWriters > 0 || (perferWriter && waitingWriters > 0)) {
                this.wait();
            }
            this.readingReaders ++;
        }finally {
            this.waitingReaders --;
        }

    }

    public synchronized void readUnlock() {
        this.readingReaders --;
        this.notifyAll();
    }

    public synchronized void writeLock() throws InterruptedException {
        this.waitingWriters ++;

        try {
            while (readingReaders > 0 || writingWriters > 0) {
                this.wait();
            }
            this.writingWriters ++;
         }finally {
            this.waitingWriters --;
        }
    }

    public synchronized void writeUnlock() {
        this.writingWriters --;
        this.notifyAll();
    }
}
