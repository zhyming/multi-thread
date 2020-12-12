package com.zhym.basic.activeobject;

/**
 * @description: 对应ActiveObject的每一个方法
 * @author: zhym
 * @time: 2020/10/22 0022 0:31
 */
public abstract class MethodRequest {

    protected final Servant servant;

    protected final FutureResult futureResult;

    public MethodRequest(Servant servant, FutureResult futureResult) {
        this.servant = servant;
        this.futureResult = futureResult;
    }

    public abstract void execute();
}
