package com.zhym.theadlocal2;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/18 0018 20:17
 */
public class QueryFromHttpAction {

    public void execute() {
        Context context = ActionContext.getInstance().getContext();
        String name = context.getName();
        String cardId = getCardId(name);
        context.setCardId(cardId);
    }

    private String getCardId(String name) {
        try {
            Thread.sleep(1_000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "121451253-" + Thread.currentThread().getId();
    }
}
