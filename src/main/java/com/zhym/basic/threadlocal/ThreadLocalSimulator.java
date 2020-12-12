package com.zhym.basic.threadlocal;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/16 0016 23:49
 */
public class ThreadLocalSimulator<T> {

    private final Map<Thread, T> storge = new HashMap<>();

    public void set(T t) {
        synchronized (this) {
            Thread key = Thread.currentThread();
            storge.put(key, t);
        }
    }

    public T get() {
        synchronized (this) {
            Thread key = Thread.currentThread();
            T value = storge.get(key);
            if (value == null) {
                return initialValue();
            }
            return value;
        }
    }

    public T initialValue() {
        return null;
    }


}
