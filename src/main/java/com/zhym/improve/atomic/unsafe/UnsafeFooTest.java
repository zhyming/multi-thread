package com.zhym.improve.atomic.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @description:
 * @author: zhym
 * @time: 2020/11/8 0008 23:09
 */
public class UnsafeFooTest {



    private static Unsafe gerUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            //get 传一个对象实例，但是如果方法或者字段为静态的则传一个null就可以了
            return  (Unsafe)field.get(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
