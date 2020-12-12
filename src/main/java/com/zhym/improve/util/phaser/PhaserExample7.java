package com.zhym.improve.util.phaser;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @description:
 * @author: zhym
 * @time: 2020/12/7 0007 19:38
 */
public class PhaserExample7 {

    public static void main(String[] args) throws InterruptedException {
        final Phaser phaser = new Phaser(3);

        /*Thread thread = new Thread(phaser::arriveAndAwaitAdvance);
        thread.start();
        System.out.println("==============");
        TimeUnit.SECONDS.sleep(5);
        thread.interrupt();
        System.out.println("========thread.interrupt=========");*/

        /*Thread thread = new Thread(() -> {
            try {
                phaser.awaitAdvanceInterruptibly(phaser.getPhase());
                System.out.println("88888888888888888888888888888888");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread.start();
        System.out.println("==============");
        TimeUnit.SECONDS.sleep(5);
        thread.interrupt();
        System.out.println("========thread.interrupt=========");*/

        Thread thread = new Thread(() -> {
            try {
                phaser.awaitAdvanceInterruptibly(0, 10, TimeUnit.SECONDS);
                System.out.println("88888888888888888888888888888888");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        });
        thread.start();

    }
}
