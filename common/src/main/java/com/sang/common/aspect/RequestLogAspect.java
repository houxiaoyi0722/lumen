package com.sang.common.aspect;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Component
@Aspect
public class RequestLogAspect {

    @Resource
    private ObjectMapper objectMapper;

    // 非public的方法不记录日志 com.sang.system.controller
    @Pointcut("execution(public * com.sang.*.controller.*.*.*(..))")
    public void requestServer() {
    }

    @Around("requestServer()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //从Header中获取用户信息
        Object result = proceedingJoinPoint.proceed();
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setUsername(SecurityContextHolder.getContext().getAuthentication().getPrincipal() + "");
        requestInfo.setUrl(request.getRequestURL().toString());
        requestInfo.setHttpMethod(request.getMethod());
        requestInfo.setClassMethod(String.format("%s.%s", proceedingJoinPoint.getSignature().getDeclaringTypeName(),
                proceedingJoinPoint.getSignature().getName()));
        requestInfo.setRequestParams(getRequestParamsByProceedingJoinPoint(proceedingJoinPoint));
//        requestInfo.setResult(result);
        requestInfo.setTimeCost(System.currentTimeMillis() - start);
        log.info("Request Info      : {}", objectMapper.writeValueAsString(requestInfo));
        return result;
    }


    @AfterThrowing(pointcut = "requestServer()", throwing = "e")
    public void doAfterThrow(JoinPoint joinPoint, RuntimeException e) throws JsonProcessingException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        RequestErrorInfo requestErrorInfo = new RequestErrorInfo();
        requestErrorInfo.setIp(request.getRemoteAddr());
        requestErrorInfo.setUsername(SecurityContextHolder.getContext().getAuthentication().getPrincipal() + "");
        requestErrorInfo.setUrl(request.getRequestURL().toString());
        requestErrorInfo.setHttpMethod(request.getMethod());
        requestErrorInfo.setClassMethod(String.format("%s.%s", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName()));
        requestErrorInfo.setRequestParams(getRequestParamsByJoinPoint(joinPoint));
        requestErrorInfo.setException(ExceptionUtil.stacktraceToOneLineString(e));
        log.error("Error Request Info      : {}", objectMapper.writeValueAsString(requestErrorInfo));
    }

    /**
     * 获取入参
     * @param proceedingJoinPoint
     *
     * @return
     * */
    private Map<String, Object> getRequestParamsByProceedingJoinPoint(ProceedingJoinPoint proceedingJoinPoint) {
        //参数名
        String[] paramNames = ((MethodSignature)proceedingJoinPoint.getSignature()).getParameterNames();
        //参数值
        Object[] paramValues = proceedingJoinPoint.getArgs();

        if (paramNames == null || paramNames.length == 0)
            return null;

        return buildRequestParam(paramNames, paramValues);
    }

    private Map<String, Object> getRequestParamsByJoinPoint(JoinPoint joinPoint) {
        //参数名
        String[] paramNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();
        //参数值
        Object[] paramValues = joinPoint.getArgs();

        return buildRequestParam(paramNames, paramValues);
    }

    private Map<String, Object> buildRequestParam(String[] paramNames, Object[] paramValues) {
        Map<String, Object> requestParams = new HashMap<>();
        for (int i = 0; i < paramNames.length; i++) {
            Object value = paramValues[i];

            //如果是文件对象
            if (value instanceof MultipartFile) {
                MultipartFile file = (MultipartFile) value;
                value = file.getOriginalFilename();  //获取文件名
            }

            requestParams.put(paramNames[i], value);
        }

        return requestParams;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public class RequestInfo {
        private String ip;
        private String username;
        private String url;
        private String httpMethod;
        private String classMethod;
        private Object requestParams;
        private Object result;
        private Long timeCost;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public class RequestErrorInfo {
        private String ip;
        private String username;
        private String url;
        private String httpMethod;
        private String classMethod;
        private Object requestParams;
        private String exception;
    }
}
