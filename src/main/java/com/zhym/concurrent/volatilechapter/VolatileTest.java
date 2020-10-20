package com.zhym.concurrent.volatilechapter;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/12 0012 23:51
 */
public class VolatileTest {

    /**
     *
     * JVM会对多线程进行优化，针对只有读没有写的线程，会优化这个线程读取数据时只去此线程缓存区拿数据，
     * 而如果线程有写操作，则每次写完后会去主存区获取数据 TEST1的问题就多线程间线程缓存区数据不一致问题
     *
     * 线程里面发生修改操作，会把线程缓存区数据刷新会主内存
     *
     * 1.给数据总线加锁
     *      总线（数据总线，地址总线， 控制总线） 软件与硬件交互是要通过总线的，内存是硬件 --
     *      LOCK#
     * 2.CUP高速缓存一致性协议
     *      Intel --> MESI 协议
     * 核心思想
     * 1.当cpu写入数据的时候，如果发现该变量被共享（也就是说，在其它cpu中也存在该变量副本），会发出一个信号，通知其它cpu该变量的缓存无效
     * 2.当其它cpu访问该变量的时候，重新到主内存进行获取新数据
     *
     * 并发编程
     * 1.原子性 A
     *      一个操作或多个操作要么都成功要么都失败，中间不能有中断
     * 2.可见性
     *      共享变量线程间透明
     * 3.有序性（顺序性）
     *      --涉及到jvm的重排序 -- 要求最终一致性 -- 即变量初始化时a,b,c;最终也是a,b,c；中间不关心（变量之间是没联系的）
     * 1）原子性
     *  对基本数据类型的读取和赋值保证原子性
     *  a = 10; 满足
     *  b = a  不满足; 1.read a; 2, assign b;
     *  c ++   不满足; 1.read c; 2.add 3. assign to c;
     *  c = c + 1; 不满足; 1.read c; 2.add 3. assign to c;
     *  2）可见性
     *      使用volatile关键字保证可见性
     *  3）有序性
     *      happen-before relationship
     *      1.代码的执行顺序，编写在前面的发生在编写在后面的 -- 仅适用于单线程
     *      2.unlock必须发生在lock之后
     *      3.volatile修饰的变量，对该变量的写操作，先于对改变量的读操作
     *      4.传递规则，操作A先于B,B先于C，那么A肯定先于C
     *      5.线程启动规则，start方法肯定先于run方法
     *      6.线程中断规则，interrupt这个动作必须发生在捕获该动作之前
     *      7.对象销毁规则，对象的初始化必须发生在finalize之前
     *      8.线程终结规则，所有的操作都发生在线程死亡之前
     *
     *
     *  volatile 关键字
     *  一旦一个共享变量被volatile修饰，具备两层语义
     *  1.保证不同线程间的可见性
     *  2.禁止对其重排序，也就是保证的程序的有序性
     *  3.不能保证原子性
     *  volatile保证重排序不会包后面的指令放到屏障前面，也不会把前面的放到后面
     *  强制对缓存的修改操作立刻写入主存
     *  如果是写操作，它会导致其它cpu中的缓存失效
     *
     *  使用场景
     *  1.状态量标记
     */
    private volatile static int INIT_VALUES = 0;

    private final static int MAX_LIMIT = 5;

    public static void main(String[] args) {
        new Thread(() -> {
            int localValue = INIT_VALUES;
            while (localValue < MAX_LIMIT) {
                if (localValue != INIT_VALUES) {
                    System.out.printf("The value updated to {%d}\n", INIT_VALUES);
                    localValue = INIT_VALUES;
                }
            }
        }, "READER").start();

        new Thread(() -> {
            int localValue = INIT_VALUES;
            while (localValue < MAX_LIMIT) {
                System.out.printf("Update the value to {%d}\n", ++ localValue);
                INIT_VALUES = localValue;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }, "UPDATE").start();
    }
}
