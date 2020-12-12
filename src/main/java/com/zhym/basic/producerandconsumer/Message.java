package com.zhym.basic.producerandconsumer;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/19 0019 23:53
 */
public class Message {

    private String data;

    public Message(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
