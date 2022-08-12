package com.sang.system.example.spring.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;



/**
 * springsecurity 元注释定义
 */
//@PreAuthorize("@sc.checkSgin()") 调用自定义方法验证
// todo 接口访问权限定义 https://docs.spring.io/spring-security/reference/6.0.0-M3/servlet/authorization/expression-based.html
@Retention(RetentionPolicy.RUNTIME)
// el表达式取参数contact下的name字段判断是否和当前登录人相同
@PreAuthorize("#contact.name == authentication.name")
public @interface ContactPermission {}