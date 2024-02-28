//package com.sang.system.example.mq.rocketmq;
//
//import org.apache.rocketmq.client.producer.SendCallback;
//import org.apache.rocketmq.client.producer.SendResult;
//import org.apache.rocketmq.spring.core.RocketMQTemplate;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import javax.annotation.Resource;
//
//@SpringBootTest
//public class TestProducer {
//
//
//    @Resource
//    private RocketMQTemplate rocketMQTemplate;
//
//
////    @Test
//    public void test() {
//        SendResult test = rocketMQTemplate.syncSend("msp-test:tag1", "test");
//        System.out.println(test.toString());
//    }
//
////    @Test
//    public void test2() {
//        rocketMQTemplate.asyncSend("msp-test:tag1", "test2", new SendCallback() {
//            @Override
//            public void onSuccess(SendResult sendResult) {
//                System.out.println(sendResult);
//            }
//
//            @Override
//            public void onException(Throwable throwable) {
//                System.out.println(throwable);
//            }
//        });
//    }
//
//    //    @Test
//    public void test3() {
//        //顺序生产发送，按照hash取模的方式使同一个key的数据发送到同一个队列
////        rocketMQTemplate.syncSendOrderly("msp-test:tag1","123","2222222");
//    }
//}
