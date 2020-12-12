package com.zhym.improve.atomic.integer;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description:
 * @author: zhym
 * @time: 2020/11/4 0004 22:38
 */
public class AtomicIntegerDetailTest {

    @Test
    public void testCreate() {
        /**
         * create
         *
         */
        //无参，初始值为0
       /* AtomicInteger i = new AtomicInteger();
        System.out.println(i.get());
        //有参
        i = new AtomicInteger(10);
        System.out.println(i.get());
        i.set(12);
        System.out.println(i.get());

        i.lazySet(122);
        System.out.println(i.get());

        System.out.println("-------------CAS---------------");
        AtomicInteger index  = new AtomicInteger(11);
        //先取值，然后再做加运算
        int result = index.getAndAdd(12);
        System.out.println(result);
        System.out.println(index.get());
        */
        /*AtomicInteger index2 = new AtomicInteger();
        new Thread(() -> {
            for (int a = 0; a < 10;a ++) {
                int v = index2.addAndGet(1);
                System.out.println(v);
            }
        }).start();
        new Thread(() -> {
            for (int a = 0; a < 10;a ++) {
                int v = index2.addAndGet(1);
                System.out.println(v);
            }
        }).start();*/
        AtomicInteger i = new AtomicInteger(12);
        boolean val = i.compareAndSet(12, 20);
        System.out.println(val);
        System.out.println(i.get());
    }
}
