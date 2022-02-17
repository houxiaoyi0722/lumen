package com.sang.system.example.juc;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hxy
 * @date 2022/2/17 18:11
 **/
class ForkJoinExample {

    @Test
    void forkJoinExample() throws ExecutionException, InterruptedException {
            ForkJoinPool forkJoinPool = new ForkJoinPool();
            ForkJoinTask<Integer> task = forkJoinPool.submit(new BatchSaveTask(1,1000000));
            Integer integer = task.get();

            forkJoinPool.awaitTermination(2, TimeUnit.SECONDS);
            forkJoinPool.shutdown();
            System.out.println(integer);
    }
}


class BatchSaveTask extends RecursiveTask<Integer> {

    public final Integer start;
    public final Integer end;

    public BatchSaveTask(Integer start, Integer end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if ((end - start) <= 5000) {
            int a = 0;
            for (int i = start; i < end; i++) {
                a += i;
            }
            return a;
        } else {
            // 对半拆分
            int middle = (end + start) / 2;
            BatchSaveTask tBatchSaveTaskLeft = new BatchSaveTask(start, middle);
            BatchSaveTask tBatchSaveTaskRight = new BatchSaveTask(middle, end);
            invokeAll(tBatchSaveTaskLeft, tBatchSaveTaskRight);
            return tBatchSaveTaskLeft.join() + tBatchSaveTaskRight.join();
        }
    }
}



