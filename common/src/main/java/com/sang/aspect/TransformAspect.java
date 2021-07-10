package com.sang.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xiaoy
 * 数据字典转换切换
 */
@Component
@Aspect
@Log4j2
public class TransformAspect {

    @Pointcut("@annotation(com.sang.annotation.dictionary.Transform)")
    public void transformServer() {
    }

    @After("transformServer()")
    public Object doAround(JoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        Object result = joinPoint.getArgs();

        return result;
    }

}
