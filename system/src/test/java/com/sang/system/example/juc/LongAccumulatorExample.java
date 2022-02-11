package com.sang.system.example.juc;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.function.LongBinaryOperator;

/**
 * @author hxy
 * @date 2022/2/11 18:28
 **/
class LongAccumulatorExample {

    @Test
    void longAccumulatorExample() throws InterruptedException {
        LongAccumulator longAccumulator = new LongAccumulator((a,b) -> a*b,1);


        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                longAccumulator.accumulate(2);
            });
        }

        long l = longAccumulator.get();
        System.out.println(l);

    }


}
