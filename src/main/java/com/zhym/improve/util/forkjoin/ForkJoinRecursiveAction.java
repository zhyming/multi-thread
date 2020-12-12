package com.zhym.improve.util.forkjoin;

import java.util.Optional;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @description:
 * @author: zhym
 * @time: 2020/12/7 0007 16:52
 */
public class ForkJoinRecursiveAction {

    private final static int MAX_THRESHOLD = 3;

    private final static AtomicInteger SUM = new AtomicInteger();

    private static class CalculateRecursiveAction extends RecursiveAction {

        private final int start;

        private final int end;

        public CalculateRecursiveAction(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if ((start - end) <= MAX_THRESHOLD) {
                SUM.addAndGet(IntStream.rangeClosed(start, end).sum());
            }else {
                int middle = (end - start) / 2;
                CalculateRecursiveAction leftAction = new CalculateRecursiveAction(start, middle);
                CalculateRecursiveAction rightAction = new CalculateRecursiveAction(middle + 1, end);
                leftAction.fork();
                rightAction.fork();

            }
        }

        public static void main(String[] args) throws InterruptedException {
            final ForkJoinPool pool = new ForkJoinPool();
            pool.submit(() -> {
                System.out.println(" Finished! ");
            },new CalculateRecursiveAction(0, 10));
            pool.awaitTermination(10, TimeUnit.SECONDS);
            Optional.of(SUM).ifPresent(System.out::println);
        }
    }
}
