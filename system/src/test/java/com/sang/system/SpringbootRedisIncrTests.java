package com.sang.system;


import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.annotation.DirtiesContext;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

@Slf4j
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class SpringbootRedisIncrTests {

    private static final String REDIS_ID_KEY = "testKey:id:%s";
    private static final String REDIS_LOCK_KEY = "testKey:lock";

    @Resource
    private RedisTemplate redisTemplate;

    @BeforeEach
    public void init(){
        log.info("init...");
        // 初始化为1000
        String idKey = String.format(REDIS_ID_KEY,  DateUtil.format(new Date() , DatePattern.PURE_DATE_PATTERN));
        redisTemplate.opsForValue().set(idKey, 1000);
    }

    @Test
    public void testIncr() throws InterruptedException {
        // 开启1024个线程去抢
        CountDownLatch countDownLatch = new CountDownLatch(1024);
        IntStream.range(0, 1024).forEach(e->{
            new Thread(new RunnableTest(countDownLatch)).start();
        });

        countDownLatch.await();
    }

    class RunnableTest implements Runnable {

        CountDownLatch countDownLatch;

        public RunnableTest(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @SneakyThrows
        @Override
        public void run() {
            invoke();
            countDownLatch.countDown();
        }
    }


    private void invoke() throws InterruptedException {
        String idKey = String.format(REDIS_ID_KEY, DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN));
        Long decr = redisTemplate.opsForValue().decrement(idKey);
        if (decr >= 0) {
            log.info("decr:{}", decr);
        } else {
            log.info("incr:{}", decr);
        }

    }




}
