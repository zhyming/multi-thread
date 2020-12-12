package com.zhym.basic.countdown;

import java.util.Random;
import java.util.stream.IntStream;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/20 0020 1:20
 */
public class SyncTest {

    private final int total;

    private int counter;


    public SyncTest(int total) {
        this.total = total;
    }

    public void down(){
        synchronized (this) {
            this.counter ++;
            System.out.println("我要暂停一下");
            this.notifyAll();
        }

    }

    public void await() {
        synchronized (this) {
            while (counter != total) {
                System.out.println("输入的值是 " + this.total);
            }
        }
    }
}

class Test {
    private static final Random random = new Random(System.currentTimeMillis());
    public static void main(String[] args) {
        SyncTest test = new SyncTest(5);
        IntStream.rangeClosed(1, 5).forEach(i ->
                new Thread(() -> {
                    System.out.println(Thread.currentThread().getName() + " is working. ");
                    try {
                        Thread.sleep(random.nextInt(1_000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    test.down();
                }, String.valueOf(i)).start()
        );
        new Thread(() -> test.await(), "输出-" ).start();
    }
}
