package com.zhym.basic.readwritelock;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/14 0014 23:18
 */
public class SharedData {

    private final char[] buffer;

    private final ReadWriteLock lock = new ReadWriteLock();

    public SharedData(int size) {
        this.buffer = new char[size];

        for (int i =0; i < buffer.length; i ++) {
            buffer[i] = '*';
        }
    }

    public char[] read() throws InterruptedException {
        try {
            lock.readLock();
            return doRead();
        }finally {
            lock.readUnlock();
        }
    }

    public void write(char c) throws InterruptedException {
        try {
            lock.writeLock();
            doWrite(c);
        }finally {
            lock.writeUnlock();
        }
    }

    private void doWrite(char c) throws InterruptedException {
        for (int i =0; i < buffer.length; i ++ ) {
            buffer[i] = c;
            slowly(10);
        }
    }

    private char[] doRead() throws InterruptedException {
        char[] newBuffer = new char[buffer.length];
        for (int i =0; i< buffer.length; i ++) {
            newBuffer[i] = buffer[i];
        }
        slowly(50);
        return newBuffer;
    }

    private void slowly(int ms) throws InterruptedException {
        try {
            Thread.sleep(ms);
        }catch (InterruptedException e){

        }
    }
}
