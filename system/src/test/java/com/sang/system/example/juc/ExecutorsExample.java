package com.sang.system.example.juc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorsExample {

    void ExecutorsExample() throws InterruptedException {

        ExecutorService studentThreadPool = Executors.newFixedThreadPool(10);

        AtomicInteger atomicInteger = new AtomicInteger();

        CountDownLatch countDownLatch = new CountDownLatch(10);

        studentThreadPool.execute(() -> {
            System.out.printf(String.valueOf(atomicInteger.incrementAndGet()));
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            countDownLatch.countDown();
        });

        boolean await = countDownLatch.await(30, TimeUnit.SECONDS);

    }


}
