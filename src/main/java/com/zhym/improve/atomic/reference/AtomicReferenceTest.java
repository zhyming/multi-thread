package com.zhym.improve.atomic.reference;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @description:
 * @author: zhym
 * @time: 2020/11/5 0005 23:19
 */


/**
 * CAS 特点
 * 1.可见性
 * 2.有序性
 * 3.原子性
 * 1.volatile修饰的变量，能包装前两者
 * 2.CAS算法，也就是CPU级别的同步指令，相当于乐观锁，他可以探测到其它线程对共享数据的变化情况
 *
 * atomicInteger ++
 * incrementAndGet()
 *
 * for(;;)
 *       int current = get();
 *       int next = current + 1;
 *       if(compareAndSet(current, next))
 *              return next;
 * 取到当前值，对当前值进行加操作，然后this对比当前值是否相等，如果符合，则返回加好的next
 *
 * CAS轻量级锁，带来的一个严重问题，ABA问题
 *
 * T1  T2
 *
 * A   A->B->A
 * 线程拿出A，对A进行CAS操作准备变成C；但是另外一个线程拿出A，优先于前一个线程先把A
 * 变成了B，然后再变成A；之后A对比发现与期望值相等，那么就把A变成了C
 *
 */
public class AtomicReferenceTest {

    @Test
    public void create() {
        AtomicReference<Simple> atomic = new AtomicReference<>(new Simple("张三", 18));
        System.out.println(atomic.get());
        boolean result = atomic.compareAndSet(new Simple("张三", 12), new Simple("李四", 23));
        System.out.println(result);
        System.out.println(atomic.get());

        final AtomicReference<Simple> s = new AtomicReference<>(new Simple("T", 22));

        new Thread(() -> {
            //匿名内部类里变量必须使用final，所以这里引用类型可以使用AtomicReference
            s.set(new Simple("GOD", 1000000));
        });

    }

    /**
     * 定义一个包装类，包装类型T的对象
     * @param <T>
     */
    static class ObjectWrap<T> {
        private T t;

        public ObjectWrap(T t) {
            this.t = t;
        }

        public T get() {
            return t;
        }

        public void set(T t) {
            this.t = t;
        }
    }

    static class Simple {
        private String name;

        private int age;

        public Simple(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }
}
