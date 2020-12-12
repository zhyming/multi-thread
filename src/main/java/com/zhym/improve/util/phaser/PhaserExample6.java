package com.zhym.improve.util.phaser;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @description:
 * @author: zhym
 * @time: 2020/12/7 0007 19:19
 */
public class PhaserExample6 {

    public static void main(String[] args) {

        Phaser phaser = new Phaser(7);

        /*phaser.awaitAdvance(10);
        System.out.println("=================");*/

        IntStream.rangeClosed(0, 5).boxed().map(i -> phaser).forEach(AwaitAdvanceTask::new);
        phaser.awaitAdvance(phaser.getPhase());
        System.out.println("==============================");

    }

    private static class AwaitAdvanceTask extends Thread {
        private final Phaser phaser;

        public AwaitAdvanceTask(Phaser phaser) {
            this.phaser = phaser;
            start();
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            phaser.arriveAndAwaitAdvance();
            System.out.println(getName() + " finished work.");
        }
    }
}
