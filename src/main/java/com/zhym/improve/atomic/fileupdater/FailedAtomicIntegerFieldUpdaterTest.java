package com.zhym.improve.atomic.fileupdater;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @description:
 * @author: zhym
 * @time: 2020/11/6 0006 21:41
 */
public class FailedAtomicIntegerFieldUpdaterTest {

    /**
     * Can't access the private field of object
     */
    @Test(expected = RuntimeException.class)
    public void testPrivateFieldAccessError() {
        AtomicIntegerFieldUpdater<TestMe> updater = AtomicIntegerFieldUpdater.newUpdater(TestMe.class, "i");
        TestMe testMe = new TestMe();

        updater.compareAndSet(testMe, 0, 1);
    }

    @Test
    public void testTargetObjectIsNull() {
        AtomicIntegerFieldUpdater<TestMe> updater = AtomicIntegerFieldUpdater.newUpdater(TestMe.class, "i");
        updater.compareAndSet(null, 0, 1);
    }

    @Test
    public void testFiledNameInvalid() {
        AtomicIntegerFieldUpdater<TestMe> updater = AtomicIntegerFieldUpdater.newUpdater(TestMe.class, "i2");
        updater.compareAndSet(null, 0, 1);
    }

    @Test
    public void testFiledTypeInvalid() {
        AtomicReferenceFieldUpdater<TestMe2, Long> updater = AtomicReferenceFieldUpdater.newUpdater(TestMe2.class, Long.class, "i");
        TestMe2 me2 = new TestMe2();
        updater.compareAndSet(me2, null, 1L);
    }

    @Test
    public void testFiledIsNotInvalid() {
        //Must be volatile type
        AtomicReferenceFieldUpdater updater = AtomicReferenceFieldUpdater.newUpdater(TestMe2.class, Integer.class, "i");
        TestMe me2 = new TestMe();
        updater.compareAndSet(me2, null, 1);
    }

    static class TestMe2 {
        volatile Integer i;
    }
}
