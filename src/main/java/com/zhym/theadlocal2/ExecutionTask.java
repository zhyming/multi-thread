package com.zhym.theadlocal2;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/18 0018 20:09
 */
public class ExecutionTask implements Runnable {

    private QueryFromAction queryAction = new QueryFromAction();

    private QueryFromHttpAction httpAction = new QueryFromHttpAction();

    @Override
    public void run() {


        queryAction.execute();
        System.out.println("The Name query successful");

        httpAction.execute();
        System.out.println("The CardId query successful");

        Context context = ActionContext.getInstance().getContext();
        System.out.println("The Name is " + context.getName() + " and CardId " + context.getCardId());
    }
}
