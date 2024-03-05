package com.sang.system.controller.mq;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/mq")
public class MqController {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("/job")
    public void test() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        //顺序生产发送，按照hash取模的方式使同一个key的数据发送到同一个队列
        executorService.execute(() -> {
            for (int i = 0; i < 100; i++) {
            int finalI = i;
                rocketMQTemplate.syncSendOrderly("msp-test:tag1", finalI + "a", finalI + "aaaaaaaa");
            }
        });
        executorService.execute(() -> {
            for (int i = 0; i < 100; i++) {
                int finalI = i;
                rocketMQTemplate.syncSendOrderly("msp-test:tag1", finalI + "b", finalI + "bbbbbbbb");
            }
        });
    }



}
