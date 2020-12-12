package com.zhym.basic.threadpremsg;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/20 0020 23:29
 */
public class MessageHandler {

    private final Random random = new Random(System.currentTimeMillis());

    private final Executor executor = Executors.newFixedThreadPool(5);

    public void request(Message message) {
        executor.execute(() -> {
            String val = message.getValue();
            try {
                Thread.sleep(random.nextInt(1_000));
                System.out.println(" The message will be handle by " + Thread.currentThread().getName()  + " " + val );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        new Thread().start();
    }

    public void shutDown() {
        ((ExecutorService)executor).shutdown();
    }
}
