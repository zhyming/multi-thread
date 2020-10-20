package com.zhym.future;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/15 0015 23:27
 */
public class SyncInvoker {

    /**
     *
     * Future 代表未来的凭据
     * FutureTask 将逻辑进行隔离
     * FutureService 桥接Future和FutureTask
     *
     *
     * @param args
     * @throws InterruptedException
     */

    public static void main(String[] args) throws InterruptedException {
        /*String result = get();
        System.out.println(result);
        System.out.println("------------");*/
        FutureService service = new FutureService();
        Future<String> future = service.submit(() -> {
                try {
                    Thread.sleep(10_000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "FINISH";
        }, System.out::println);

        System.out.println("================");
        System.out.println(" do other thing. ");
        Thread.sleep(1_000);
        System.out.println("=================");

        //System.out.println(future.get());
        System.out.println("-----------------------------");
    }

    private static String get() throws InterruptedException {
        Thread.sleep(10_000);
        return "FINISH";
    }
}
