package com.sang.system.mq;

import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

//@RocketMQMessageListener(topic = "msp-test", consumerGroup = "consumer-group-test", selectorExpression = "tag1 || tag2")

// 顺序消费 全局队列锁防止其他消费者消费+消费者队列对象锁防止消费者内部其他线程消费+process队列锁防止队列重新分配
@RocketMQMessageListener(
        topic = "msp-test",
        consumerGroup = "consumer-group-test",
        selectorExpression = "tag1 || tag2",
        consumeMode = ConsumeMode.ORDERLY)
@Component
public class TestConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String msg) {
        System.out.println("receive message: " + msg);
    }

}