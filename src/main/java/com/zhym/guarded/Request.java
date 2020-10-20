package com.zhym.guarded;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/16 0016 22:49
 */
public class Request {

    final private String value;

    public Request(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
