package com.zhym.improve.util.phaser;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: zhym
 * @time: 2020/12/7 0007 18:15
 */
public class PhaserExample4 {

    public static void main(String[] args) throws InterruptedException {
        //final Phaser phaser = new Phaser(1);

        /*System.out.println(phaser.getPhase());

        phaser.arriveAndAwaitAdvance();
        System.out.println(phaser.getPhase());

        phaser.arriveAndAwaitAdvance();
        System.out.println(phaser.getPhase());

        phaser.arriveAndAwaitAdvance();
        System.out.println(phaser.getPhase());*/

        /*System.out.println(phaser.getRegisteredParties());

        phaser.register();

        System.out.println(phaser.getRegisteredParties());

        phaser.register();

        System.out.println(phaser.getRegisteredParties());

        phaser.register();

        System.out.println(phaser.getRegisteredParties());*/



        /*phaser.bulkRegister(10);
        System.out.println(phaser.getRegisteredParties());
        System.out.println(phaser.getArrivedParties());
        System.out.println(phaser.getUnarrivedParties());
        new Thread(phaser::arriveAndAwaitAdvance).start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("==========================");
        System.out.println(phaser.getRegisteredParties());
        System.out.println(phaser.getArrivedParties());
        System.out.println(phaser.getUnarrivedParties());*/


        final Phaser phaser = new Phaser(2) {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                return true;
            }
        };

        new OnAdvanceTask("A", phaser).start();
        new OnAdvanceTask("B", phaser).start();

        TimeUnit.SECONDS.sleep(2);
        System.out.println(phaser.getUnarrivedParties());
        System.out.println(phaser.getArrivedParties());


    }

    static class OnAdvanceTask extends Thread {
        private final Phaser phaser;

        public OnAdvanceTask(String name, Phaser phaser) {
            super(name);
            this.phaser = phaser;
        }

        @Override
        public void run() {
            System.out.println(getName() + " I am start and the phase " + phaser.getPhase());
            phaser.arriveAndAwaitAdvance();
            System.out.println(getName() + " I am end !");

            System.out.println(" isTerminated ->" +  phaser.isTerminated());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (getName().equals("A")) {
                System.out.println(getName() + " I am start and the phase " + phaser.getPhase());
                phaser.arriveAndAwaitAdvance();
                System.out.println(getName() + " I am end !");
            }
        }
    }
}
