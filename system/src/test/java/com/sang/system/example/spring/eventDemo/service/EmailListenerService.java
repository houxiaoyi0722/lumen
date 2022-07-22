package com.sang.system.example.spring.eventDemo.service;

import com.sang.system.example.spring.eventDemo.event.UserRegisterEvent;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailListenerService implements ApplicationListener<UserRegisterEvent> {    // 监听器2

    private Logger logger = LoggerFactory.getLogger(getClass());

    @SneakyThrows
    @Override
    @Async // 异步处理
    public void onApplicationEvent(UserRegisterEvent event) {
        Thread.sleep(2000);
        logger.info("[onApplicationEvent][给用户({}) 发送邮件]", event.getUsername());
    }

}
