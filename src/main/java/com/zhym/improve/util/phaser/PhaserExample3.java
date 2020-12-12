package com.zhym.improve.util.phaser;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: zhym
 * @time: 2020/12/7 0007 17:42
 */
public class PhaserExample3 {

    private final static Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        final Phaser phaser = new Phaser(5);
        for (int i = 1; i < 5; i ++ ){
            new Athletes(i, phaser).start();
        }
        new InjuredAthletes(5, phaser).start();
    }

    static class InjuredAthletes extends Thread {
        private final int no;
        private final Phaser phaser;

        public InjuredAthletes(int no, Phaser phaser) {
            this.no = no;
            this.phaser = phaser;
        }

        @Override
        public void run() {

            try {
                sport(no, phaser, " : start running. ", " : finished running. ");

                sport(no, phaser, " : start bicycle. ", " : finished bicycle. ");

                System.out.println(" I am injured. i will be exited! ");
                //动态销户
                phaser.arriveAndDeregister();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Athletes extends Thread {
        private final int no;
        private final Phaser phaser;

        public Athletes(int no, Phaser phaser) {
            this.no = no;
            this.phaser = phaser;
        }

        @Override
        public void run() {

            try {
                sport(no, phaser, " : start running. ", " : finished running. ");


                sport(no, phaser, " : start bicycle. ", " : finished bicycle. ");

                sport(no, phaser, " : start jump. ", " : finished jump. ");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private static void sport(int no, Phaser phaser, String s, String s2) throws InterruptedException {
        System.out.println(no + s);
        TimeUnit.SECONDS.sleep(random.nextInt(5));
        System.out.println(no + s2);
        phaser.arriveAndAwaitAdvance();
    }
}
