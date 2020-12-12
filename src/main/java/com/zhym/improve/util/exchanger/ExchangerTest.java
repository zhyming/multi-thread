package com.zhym.improve.util.exchanger;

import java.sql.Time;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description:
 * @author: zhym
 * @time: 2020/11/15 0015 22:55
 */
public class ExchangerTest {

    public static void main(String[] args) {
        /**
         *
         * 使用Exchanger进行传送的对象，是可以一直进行数据传输
         *
         * @param args
         */
        Exchanger<Integer> exchanger = new Exchanger<>();
        new Thread(() -> {
            AtomicInteger value = new AtomicInteger(1);
            try {
                while (true) {
                    value.set(exchanger.exchange(value.get()));
                    System.out.println(" === A received === " + value);
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " end. ");

        }, "==A==").start();

        new Thread(() -> {
            AtomicInteger value = new AtomicInteger(2);
            try {
                while (true) {
                    value.set(exchanger.exchange(value.get()));
                    System.out.println(" === B received === " + value);
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " end. ");

        }, "==B==").start();
    }

    public static void main1(String[] args) {
        /**
         *
         * 使用Exchanger进行传送的对象，从A线程发送的到B线程接收的是指向同一个对象，所以这时在A线程中对此对象进行修改
         * 那么在B中也会受到影响
         *
         * @param args
         */
        Exchanger<Object> exchanger = new Exchanger<>();
        new Thread(() -> {
            final Object a = new Object();
            System.out.println(" === A send === " + a);
            try {
                Object result = exchanger.exchange(a);
                System.out.println(" === A received === " + result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " end. ");

        }, "==A==").start();

        new Thread(() -> {
            final Object b = new Object();
            System.out.println(" === B send === " + b);
            try {
                Object result = exchanger.exchange(b);
                System.out.println(" === B received === " + result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " end. ");

        }, "==B==").start();
    }

    public static void main2(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();

        /**
         * r = exchanger.exchange(V v)
         *   v 代表本线程传送出去的值
         *   r 代表本线程接收到的值
         *
         *
         *   使用Exchanger必须成对，不然多出来的线程就会waiting
         *
         */

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start. ");
            try {
                String result = exchanger.exchange("I am come from T-A", 5, TimeUnit.SECONDS);
                System.out.println(Thread.currentThread().getName() + " Get value [" + result + "]");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " end. ");

        }, "==A==").start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start. ");
            try {
                TimeUnit.SECONDS.sleep(10);
                String result = exchanger.exchange("I am come from T-B");
                System.out.println(Thread.currentThread().getName() + " Get value [" + result + "]");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " end. ");

        }, "==B==").start();
    }


}
