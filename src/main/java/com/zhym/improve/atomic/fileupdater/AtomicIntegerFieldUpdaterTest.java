package com.zhym.improve.atomic.fileupdater;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @description:
 * @author: zhym
 * @time: 2020/11/6 0006 21:41
 */
public class AtomicIntegerFieldUpdaterTest {

    /**
     * 1.想让类的属性操作具备原子性
     *      1.1.volatile
     *      1.2.非private，protected（如果是当前类也可以是protected或private）
     *      1.3.类型必须一致
     *      1.4. 其它
     * 2.不想使用锁（包括显示锁，重量级锁）
     *
     *
     * @param args
     */


    public static void main(String[] args) {
        AtomicIntegerFieldUpdater<TestMe> updater = AtomicIntegerFieldUpdater.newUpdater(TestMe.class, "i");
        TestMe testMe = new TestMe();
        for(int i = 0; i < 2; i ++) {
            new Thread(() -> {
                final int MAX = 20;
                for (int a = 0; a < MAX; a ++) {
                    int increment = updater.getAndIncrement(testMe);
                    System.out.println(Thread.currentThread().getName() + " : " + increment);
                }
            }).start();
        }
    }

}
