package com.zhym.improve.atomic.integer;

/**
 * @description:
 * @author: zhym
 * @time: 2020/11/4 0004 0:54
 */
public class JITTest {

    //不使用volatile，第一个线程在jdk1.8版本不会退出，但是如果线程一循环里头加了东西，就会退出（很诡异）
    //private static boolean init = false;
    //使用volatile，第一个线程是个空循环满足条件了线程会退出结束
    private volatile static boolean init = false;

    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {
            /**
             * 非volatile修饰的变量作为循环条件，并且为空循环；JIT把它编译等价于while（true）{}，认为此状态不会受到后面代码影响；
             * 如果不是空循环，那么它不会这样处理，会按照代码本身编译，不会做手脚；所以在多线程中循环条件一定要用volatile修饰
             */
            while (!init) {

                System.out.println(init);
            }
        }).start();

        Thread.sleep(1000);

        new Thread(() -> {
            init = true;
            System.out.println(" set init to true. ");
        }).start();

    }
}
