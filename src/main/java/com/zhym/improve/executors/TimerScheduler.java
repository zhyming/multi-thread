package com.zhym.improve.executors;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @description:
 * @author: zhym
 * @time: 2020/12/8 0008 2:17
 */
public class TimerScheduler {

    /**
     * scheduler solution
     *   Timer/TimerTask
     *   SchedulerExecutorService
     * @param args
     */

    public static void main(String[] args) {

        Timer timer = new Timer();

        final TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("===============" + System.currentTimeMillis());

            }
        };
        timer.schedule(task, 1000, 1000);
    }
}
