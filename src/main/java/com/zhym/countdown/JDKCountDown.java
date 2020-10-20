package com.zhym.countdown;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/20 0020 0:46
 */
public class JDKCountDown {

    private static final Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) throws InterruptedException {
        System.out.println("准备多线程处理任务.");
        final CountDownLatch latch = new CountDownLatch(5);

        IntStream.rangeClosed(1, 5).forEach(i ->
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " is working. ");
                try {
                    Thread.sleep(random.nextInt(1_000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();
            }, String.valueOf(i)).start()
        );
        latch.await();
        System.out.println("任务全部结束，准备第二阶段任务");
        System.out.println("----------------------");
        System.out.println("FINISHED");
    }
}
