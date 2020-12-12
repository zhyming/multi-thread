package com.zhym.improve.atomic.atomiclong;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @description:
 * @author: zhym
 * @time: 2020/11/5 0005 23:04
 */
public class AtomicLongTest {

    @Test
    public void create() {

        AtomicLong l = new AtomicLong(100L);

        /**
         * VM_SUPPORTS_LONG_CAS
         * 地址、数据、控制总线-与cpu交互
         * long 64
         * high 32
         * low  32
         */

    }
}
