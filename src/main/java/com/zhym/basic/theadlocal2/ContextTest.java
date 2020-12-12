package com.zhym.basic.theadlocal2;

import java.util.stream.IntStream;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/18 0018 20:26
 */
public class ContextTest {


    public static void main(String[] args) {

        IntStream.rangeClosed(1, 5).forEach(i -> {
            new Thread(new ExecutionTask()).start();
        });
    }
}
