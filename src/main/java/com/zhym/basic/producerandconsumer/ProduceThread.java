package com.zhym.basic.producerandconsumer;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/20 0020 0:06
 */
public class ProduceThread  extends Thread{

    private final MessageQueue messageQueue;

    private final static Random random = new Random(System.currentTimeMillis());

    private final static AtomicInteger counter = new AtomicInteger(0);

    public ProduceThread(MessageQueue messageQueue, int seq) {
        super("PRODUCER-" + seq);
        this.messageQueue = messageQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message message = new Message("Message-" + counter.getAndIncrement());
                messageQueue.put(message);
                System.out.println(Thread.currentThread().getName() + "put message " + message.getData());
                Thread.sleep(random.nextInt(1_000));
            } catch (InterruptedException e) {
                break;
            }

        }
    }
}
