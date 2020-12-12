package com.zhym.basic.workerthread;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/21 0021 23:41
 */
public class Request {

    private final String name;

    private final int number;

    public Request(final String name, final int number) {
        this.name = name;
        this.number = number;
    }

    public void execute(){
        System.out.println(Thread.currentThread().getName() + " executed " + this);
    }

    @Override
    public String toString() {
        return "Request -> No. " + number + " Name. "  + name;
    }
}
