package com.zhym.basic.activeobject;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/23 0023 0:03
 */
public class ActiveObjectTest {

    public static void main(String[] args) {
        ActiveObject activeObject = ActiveObjectFactory.createActiveObject();
        new MakerClientThread(activeObject, "张三").start();
        new MakerClientThread(activeObject, "王五").start();

        new DisplayClientThread("关羽", activeObject).start();
    }
}
