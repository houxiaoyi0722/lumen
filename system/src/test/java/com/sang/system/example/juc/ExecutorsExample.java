package com.sang.system.example.juc;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorsExample {

    void ExecutorsExample() throws InterruptedException, ExecutionException {

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

/*        Future<String> submit = studentThreadPool.submit(() -> {
            System.out.printf(String.valueOf(atomicInteger.incrementAndGet()));
            countDownLatch.countDown();
            return "1";
        });
        System.out.println(submit.get());*/

        boolean await = countDownLatch.await(30, TimeUnit.SECONDS);

    }


}
