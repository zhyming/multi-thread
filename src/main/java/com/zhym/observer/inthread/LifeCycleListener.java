package com.zhym.observer.inthread;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/14 0014 0:58
 */
public interface LifeCycleListener  {


    void onEvent(ObserverRunnable.RunnableEvent event);
}
