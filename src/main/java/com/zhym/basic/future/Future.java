package com.zhym.basic.future;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/15 0015 23:30
 */
public interface Future<T> {

    T get() throws InterruptedException;
}
