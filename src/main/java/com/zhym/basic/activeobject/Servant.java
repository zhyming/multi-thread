package com.zhym.basic.activeobject;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/22 0022 0:34
 */
 class Servant implements ActiveObject {
    @Override
    public Result makeString(int count, char fillChar) {

        char[] buf = new char[count];
        for (int i = 0; i < count; i ++) {
            buf[i] = fillChar;
            try {
                Thread.sleep(10);
            }catch (Exception e) {

            }
        }
        return new RealResult(new String(buf));
    }

    @Override
    public void displayString(String text) {

        try {
            System.out.println(" Display: " + text);
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
