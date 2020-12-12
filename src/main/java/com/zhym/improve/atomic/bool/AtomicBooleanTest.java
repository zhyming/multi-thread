package com.zhym.improve.atomic.bool;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @description:
 * @author: zhym
 * @time: 2020/11/5 0005 0:21
 */
public class AtomicBooleanTest {

    @Test
    public void crateAtomicBooleanTest() {
        AtomicBoolean bool = new AtomicBoolean();
        System.out.println(bool.get());

        AtomicBoolean bool2 = new AtomicBoolean(true);
        System.out.println(bool2.get());

        boolean bool3 = bool2.getAndSet(false);
        System.out.println(bool3);
        System.out.println(bool2.get());
    }

    @Test
    public void testCompareAndSet() {
        AtomicBoolean bool = new AtomicBoolean(true);
        boolean result = bool.compareAndSet(true, false);
        System.out.println("修改状态：" + result);
        System.out.println(bool.get());
    }

    @Test
    public void testCompareAndSet2() {
        AtomicBoolean bool = new AtomicBoolean(true);
        boolean result = bool.compareAndSet(false, false);
        System.out.println("修改状态：" + result);
        System.out.println(bool.get());
    }

}
