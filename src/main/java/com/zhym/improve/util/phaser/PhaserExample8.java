package com.zhym.improve.util.phaser;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: zhym
 * @time: 2020/12/7 0007 19:53
 */
public class PhaserExample8 {

    public static void main(String[] args) throws InterruptedException {
        Phaser phaser = new Phaser(3);

        new Thread(phaser::arriveAndAwaitAdvance).start();

        TimeUnit.SECONDS.sleep(3);

        System.out.println(phaser.isTerminated());

        phaser.forceTermination();
        System.out.println(phaser.isTerminated());
    }
}
