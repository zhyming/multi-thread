package com.zhym.improve.atomic.atomicarray;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @description:
 * @author: zhym
 * @time: 2020/11/6 0006 21:18
 */
public class AtomicIntegerArrayTest {

    @Test
    public void create() {

        AtomicIntegerArray array = new AtomicIntegerArray(10);

        System.out.println(array.length());

        System.out.println(array.get(5));

        array.set(8, 3333);
        System.out.println(array.get(8));

        int v = array.getAndSet(5, 111);
        System.out.println(v);
        System.out.println(array.get(5));
    }


}
