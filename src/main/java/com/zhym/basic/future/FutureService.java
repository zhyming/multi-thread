package com.zhym.basic.future;

import java.util.function.Consumer;

/**
 * @description:
 * @author: zhym
 * @time: 2020/10/15 0015 23:32
 */
public class FutureService {

    public <T> Future<T> submit(final FutureTask<T> task) {
        AsynFuture<T> asynFuture = new AsynFuture<>();

        new Thread(()-> {
            T result = task.call();
            asynFuture.done(result);
        }).start();

        return asynFuture;
    }

    public <T> Future<T> submit(final FutureTask<T> task, final Consumer<T> consumer) {
        AsynFuture<T> asynFuture = new AsynFuture<>();

        new Thread(()-> {
            T result = task.call();
            asynFuture.done(result);
            consumer.accept(result);
        }).start();

        return asynFuture;
    }
}
