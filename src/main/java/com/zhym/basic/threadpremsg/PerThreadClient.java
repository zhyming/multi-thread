package com.zhym.basic.threadpremsg;

import java.util.stream.IntStream;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/20 0020 23:32
 */
public class PerThreadClient {

    public static void main(String[] args) {
        final MessageHandler handler = new MessageHandler();
        IntStream.rangeClosed(0, 10)
                .forEach(
                        i -> handler.request(new Message(String.valueOf(i)))
                );

        handler.shutDown();
    }
}
