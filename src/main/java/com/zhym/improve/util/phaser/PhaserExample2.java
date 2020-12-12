package com.zhym.improve.util.phaser;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: zhym
 * @time: 2020/12/7 0007 17:42
 */
public class PhaserExample2 {

    private final static Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        final Phaser phaser = new Phaser(5);
        for (int i = 1; i < 6; i ++ ){
            new Athlets(i, phaser).start();
        }
    }

    static class Athlets extends Thread {
        private final int no;
        private final Phaser phaser;

        public Athlets(int no, Phaser phaser) {
            this.no = no;
            this.phaser = phaser;
        }

        @Override
        public void run() {

            try {
                System.out.println(no + " : start running. ");
                TimeUnit.SECONDS.sleep(random.nextInt(5));
                System.out.println(no + " : finished running. ");
                System.out.println(" getPhase -> " + phaser.getPhase());
                phaser.arriveAndAwaitAdvance();


                System.out.println(no + " : start bicycle. ");
                TimeUnit.SECONDS.sleep(random.nextInt(5));
                System.out.println(no + " : finished bicycle. ");
                System.out.println(" getPhase -> " + phaser.getPhase());
                phaser.arriveAndAwaitAdvance();

                System.out.println(no + " : start jump. ");
                TimeUnit.SECONDS.sleep(random.nextInt(5));
                System.out.println(no + " : finished jump. ");
                System.out.println(" getPhase -> " + phaser.getPhase());
                phaser.arriveAndAwaitAdvance();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
