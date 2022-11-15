package com.sang.system.example.mq.rocketmq;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@RocketMQMessageListener(
        topic = "msp-test",
        consumerGroup = "consumer-group-test",
        selectorExpression = "tag1 || tag2")
@Component
public class TestConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String msg) {
        System.out.println("receive message: " + msg);
    }

}