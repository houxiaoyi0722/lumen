package com.sang.system.example.juc;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author hxy
 * @date 2022/2/8 17:28
 **/
class TestThreadLocalRandomExample {

//    @Test
    void test() throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3,5,1000, TimeUnit.SECONDS,new ArrayBlockingQueue<>(10), Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());

        CountDownLatch countDownLatch = new CountDownLatch(5);

        LongAdder longAdder = new LongAdder();

        for (int i = 0; i < 5; i++) {
            threadPoolExecutor.execute(() -> {
                for (int j = 0; j < 10; j++) {
                    try {
                        longAdder.add(1);
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " : " +  ThreadLocalRandom.current().nextInt(10) + " J : " + j);
                }
                countDownLatch.countDown();
            });
        }
/*        try {
            boolean b = threadPoolExecutor.awaitTermination(1000, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        long sum = longAdder.sum();
        countDownLatch.await();
        threadPoolExecutor.shutdown();
    }

}
