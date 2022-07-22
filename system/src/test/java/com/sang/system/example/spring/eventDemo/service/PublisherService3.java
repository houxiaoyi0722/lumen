package com.sang.system.example.spring.eventDemo.service;

import com.sang.system.example.spring.eventDemo.event.UserRegisterEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class PublisherService3 {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void register(String username) {
        // ... 执行注册逻辑
        logger.info("[register][执行用户({}) 的注册逻辑]", username);

        // ... 发布
        applicationEventPublisher.publishEvent(new UserRegisterEvent(this,"test3"));
    }

}
