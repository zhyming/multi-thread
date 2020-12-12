package com.zhym.basic.activeobject;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/22 0022 0:37
 */
public class RealResult implements Result {
    private final Object resultValue;

    public RealResult(Object resultValue) {
        this.resultValue = resultValue;
    }

    @Override
    public Object getResultValue() {
        return this.resultValue;
    }
}
