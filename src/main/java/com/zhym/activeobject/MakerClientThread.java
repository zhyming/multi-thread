package com.zhym.activeobject;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/22 0022 23:56
 */
public class MakerClientThread extends Thread{

    private final ActiveObject activeObject;

    private final char fillChar;

    public MakerClientThread(ActiveObject activeObject, String name) {
        super(name);
        this.activeObject = activeObject;
        this.fillChar = name.charAt(0);
    }

    @Override
    public void run() {
        try {
            for (int i = 0; true; i ++) {
                 Result result = activeObject.makeString(i + 1, fillChar);
                 Thread.sleep(20);
                 String value = (String)result.getResultValue();
                System.out.println(Thread.currentThread().getName() + ": value" + value);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
