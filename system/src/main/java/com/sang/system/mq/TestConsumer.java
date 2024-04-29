package com.sang.system.mq;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@RocketMQMessageListener(
        topic = "bdmp-notice",
        consumerGroup = "",
        selectorExpression = "tag1 || tag2")
@Component
public class TestConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String msg) {
        System.out.println("receive message: " + msg);
    }

}