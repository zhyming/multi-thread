package com.zhym.threadlocal;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/16 0016 23:36
 */
public class ThreadSimpleTest {

    //private static ThreadLocal threadLocal = new ThreadLocal();
    private static ThreadLocal<String> threadLocal = new ThreadLocal(){
        @Override
        protected String initialValue() {
            return "老王";
        }
    };

    public static void main(String[] args) throws InterruptedException {
        threadLocal.set("张三");
        Thread.sleep(1_000);
        //String value = (String) threadLocal.get();
        String value = threadLocal.get();
        System.out.println(threadLocal.get());
    }
}
