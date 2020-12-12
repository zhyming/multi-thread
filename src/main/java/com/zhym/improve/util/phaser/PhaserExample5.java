package com.zhym.improve.util.phaser;

import java.nio.channels.FileChannel;
import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: zhym
 * @time: 2020/12/7 0007 18:49
 */
public class PhaserExample5 {

    private static final Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) throws InterruptedException {
       /* final Phaser phaser = new Phaser();

        new Thread(phaser::arrive).start();

        TimeUnit.SECONDS.sleep(4);*/

       final Phaser phaser = new Phaser(5);

       for (int i = 1; i < 5; i ++) {
           new ArriveTask(phaser, i).start();
       }
       phaser.arriveAndAwaitAdvance();
        System.out.println(" The phase 1 work finished done. ");

    }

    static class ArriveTask extends Thread {
        private final Phaser phaser;

        public ArriveTask(Phaser phaser, int no) {
            super(String.valueOf(no));
            this.phaser = phaser;
        }

        @Override
        public void run() {
            System.out.println(getName() + " start working. ");
            PhaserExample5.sleep();
            System.out.println(getName() + " The phase one is running. ");
            phaser.arrive();

            PhaserExample5.sleep();
            System.out.println(getName() + " keep to do other. ");
        }
    }

    private static void sleep() {
        try {
            TimeUnit.SECONDS.sleep(random.nextInt(5));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
