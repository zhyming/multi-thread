package com.zhym.basic.immutable;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/15 0015 22:51
 */
public class PerformanceTest {

    public static void main(String[] args) throws InterruptedException {
        long startTimestamp = System.currentTimeMillis();
        /**32807 -- sync
        SyncObj syncObj = new SyncObj();
        syncObj.setName("张三");*/

        //ImmutableObj obj = new ImmutableObj("李四");
        SyncObj syncObj = new SyncObj();
        syncObj.setName("张三");
        /*for(long l = 0L; l < 10_000_000L; l++) {
            System.out.println(obj.toString());
        }*/
        //多线程下 immutable--89817
        Thread t1 = new Thread(() -> {
                for(long l = 0L; l < 10_000_000L; l++) {
                    System.out.println(Thread.currentThread().getName() + "=" +syncObj.toString());
                }
            });

        Thread t2 = new Thread(() -> {
            for(long l = 0L; l < 10_000_000L; l++) {
                System.out.println(Thread.currentThread().getName() + "=" +syncObj.toString());
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        long endTimestamp = System.currentTimeMillis();
        System.out.println("spend time " + (endTimestamp - startTimestamp));
    }

}

final class ImmutableObj {
    private final String name;

    ImmutableObj(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "{" + name + '}';
    }
}

class SyncObj {
    private String name;

    public synchronized void setName(String name) {
        this.name = name;
    }

    @Override
    public synchronized String toString() {
        return "{" + name + '}';
    }
}
