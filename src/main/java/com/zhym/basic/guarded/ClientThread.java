package com.zhym.basic.guarded;

import java.util.Random;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/16 0016 22:56
 */
public class ClientThread extends Thread {

    private final RequestQueue queue;

    private final Random random;

    private final String sendValue;

    public ClientThread(RequestQueue queue, String sendValue) {
        this.queue = queue;
        random = new Random(System.currentTimeMillis());
        this.sendValue = sendValue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i ++ ) {
            System.out.println("Client -> request" + sendValue);
            queue.putRequest(new Request(sendValue));
            try {
                Thread.sleep(random.nextInt(1_000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
