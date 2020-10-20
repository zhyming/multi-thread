package com.zhym.immutable;

import java.util.stream.IntStream;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/15 0015 1:01
 */
public class Client {

    public static void main(String[] args) {
        Person person = new Person("ZZZ", "GGG");

        IntStream.rangeClosed(0, 5).forEach(i -> {
            new UserPersonThread(person).start();
        });
    }
}
