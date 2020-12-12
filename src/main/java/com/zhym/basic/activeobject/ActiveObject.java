package com.zhym.basic.activeobject;

/**
 * @description: 接受异步消息的主动方法
 * @author: zhym
 * @time: 2020/10/22 0022 0:24
 */
public interface ActiveObject {

    Result makeString(int count, char fillChar);

    void displayString(String text);
}
