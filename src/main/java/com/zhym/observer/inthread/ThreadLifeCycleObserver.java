package com.zhym.observer.inthread;

import java.util.List;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/14 0014 1:00
 */
public class ThreadLifeCycleObserver implements LifeCycleListener {

    private final Object LOCK = new Object();

    public void concurrentQuery(List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }
        ids.stream().forEach(id -> new Thread(new ObserverRunnable(this) {
            @Override
            public void run() {
                try {
                    notifyChange(new RunnableEvent(RunnableState.RUNNING, Thread.currentThread(), null));
                    System.out.println("query for the id " + id);
                    Thread.sleep(1_000L);
                    int x = 1 / 0;
                    notifyChange(new RunnableEvent(RunnableState.DONE, Thread.currentThread(), null));
                }catch (Exception e) {
                    notifyChange(new RunnableEvent(RunnableState.ERROR, Thread.currentThread(), e));
                }
            }
        }, id).start());
    }

    @Override
    public void onEvent(ObserverRunnable.RunnableEvent event) {
        synchronized (LOCK) {
            System.out.println("The runnable {" + event.getThread().getName() + "} data change and state is {"+ event.getState()+"}");
            if (event.getCause() != null) {
                System.out.println("The runnable {" + event.getThread().getName()+"} process failed");
                System.out.println(event.getCause());
            }
        }
    }
}
