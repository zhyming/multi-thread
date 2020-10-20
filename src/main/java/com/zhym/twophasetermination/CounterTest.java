package com.zhym.twophasetermination;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/20 0020 23:54
 */
public class CounterTest {

    public static void main(String[] args) throws InterruptedException {
        CounterIncrement counterIncrement = new CounterIncrement();
        counterIncrement.start();

        Thread.sleep(10_000L);
        counterIncrement.close();
    }
}
