package com.zhym.basic.observer.inthread;

import java.util.Arrays;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/14 0014 1:19
 */
public class Client {

    public static void main(String[] args) {

        new ThreadLifeCycleObserver().concurrentQuery(Arrays.asList("1", "33", "44", "55"));


    }
}
