package com.zhym.basic.theadlocal2;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/18 0018 20:10
 */
public class QueryFromAction {
    public  void execute() {

        try {
            Thread.sleep(1_000L);
            String name = "张三-" + Thread.currentThread().getName();
            ActionContext.getInstance().getContext().setName(name);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
