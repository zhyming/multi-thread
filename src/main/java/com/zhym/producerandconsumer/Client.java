package com.zhym.producerandconsumer;

import java.util.stream.IntStream;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/20 0020 0:15
 */
public class Client {

    public static void main(String[] args) {
        final MessageQueue queue = new MessageQueue();

        IntStream.rangeClosed(1, 5).forEach(i -> {
            new ProduceThread(queue, i).start();
        } );
        IntStream.rangeClosed(1, 5).forEach(i -> {
            new ConsumerThread(queue, i).start();
        } );
        ;
    }
}
