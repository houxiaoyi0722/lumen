package com.sang.system.example.spring.eventDemo.service;

import com.sang.system.example.spring.eventDemo.event.UserRegisterEvent;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class CouponListenerService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 监听器1
    @SneakyThrows
    @EventListener // 此处默认为同步（由同一线程执行）/可以添加异步注解，发布者需要等待监听者执行完成才能执行下一次调用
    public void addCoupon(UserRegisterEvent event) {
        Thread.sleep(2000);
        logger.info("[addCoupon][给用户({}) 发放优惠劵]", event.getUsername());
    }

}
