package com.zhym.improve.util.phaser;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @description:
 * @author: zhym
 * @time: 2020/12/7 0007 17:17
 */
public class PhaserExample {

    private static final Random random = new Random(System.currentTimeMillis());

    /**
     * Phaser -》 动态注册栅栏
     * @param args
     */

    public static void main(String[] args) {

        final Phaser phaser = new Phaser();
        IntStream.rangeClosed(1, 5).boxed().map(i -> phaser).forEach(Task::new);
        phaser.register();
        phaser.arriveAndAwaitAdvance();
        System.out.println(" all of worker finished the task. ");
    }

    private static class Task extends Thread {
        private Phaser phaser;

        public Task(Phaser phaser) {
            this.phaser = phaser;
            this.phaser.register();
            start();
        }

        @Override
        public void run() {
            System.out.println(" The Worker [ " + getName() + " ] is working... " );
            try {
                TimeUnit.SECONDS.sleep(random.nextInt(5));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            phaser.arriveAndAwaitAdvance();
        }
    }
}
