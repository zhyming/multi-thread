package com.zhym.theadlocal2;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/18 0018 20:32
 */
public final class ActionContext {

    private static final ThreadLocal<Context> threadLocal = new ThreadLocal<Context>() {
        @Override
        protected Context initialValue() {
            return new Context();
        }
    };

    private static class ContextHolder {
        private static ActionContext actionContext = new ActionContext();

    }

    public static ActionContext getInstance() {
        return ContextHolder.actionContext;
    }

    public Context getContext() {
        return threadLocal.get();
    }
}
