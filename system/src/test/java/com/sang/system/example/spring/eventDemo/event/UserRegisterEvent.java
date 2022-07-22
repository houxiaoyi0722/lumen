package com.sang.system.example.spring.eventDemo.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * 用户注册事件
 */
@AllArgsConstructor
@Getter
@Setter
public class UserRegisterEvent extends ApplicationEvent {

    /**
     * 用户名
     */
    private String username;

}
