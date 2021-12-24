//package com.sang.system.aspect;
//
//import cn.hutool.core.exceptions.ExceptionUtil;
//import cn.hutool.json.JSONUtil;
//import com.jins.common.entity.auth.JinsJwtInfo;
//import com.jins.common.utils.LoginUserUtil;
//import lombok.Data;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.AfterThrowing;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.HashMap;
//import java.util.Map;
//
//@Component
//@Aspect
//public class RequestLogAspect {
//    private final static Logger LOGGER = LoggerFactory.getLogger(RequestLogAspect.class);
//    // 非public的方法不记录日志
//    @Pointcut("execution(public * com.jins.*.controller.*.*(..))")
//    public void requestServer() {
//    }
//
//    @Around("requestServer()")
//    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//        long start = System.currentTimeMillis();
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//        String deviceId = request.getHeader("deviceId");
//        //从Header中获取用户信息
//        JinsJwtInfo jinsJwtInfo = LoginUserUtil.getCurrentUser();
//
//        Object result = proceedingJoinPoint.proceed();
//        RequestInfo requestInfo = new RequestInfo();
////        requestInfo.setIp(request.getRemoteAddr());
//        requestInfo.setUserName(jinsJwtInfo.getUserName());
//        requestInfo.setUrl(request.getRequestURL().toString());
//        requestInfo.setHttpMethod(request.getMethod());
//        requestInfo.setDeviceId(deviceId);
//        requestInfo.setClassMethod(String.format("%s.%s", proceedingJoinPoint.getSignature().getDeclaringTypeName(),
//                proceedingJoinPoint.getSignature().getName()));
//        requestInfo.setRequestParams(getRequestParamsByProceedingJoinPoint(proceedingJoinPoint));
////        requestInfo.setResult(result);
//        requestInfo.setTimeCost(System.currentTimeMillis() - start);
//        LOGGER.info("Request Info      : {}", JSONUtil.toJsonStr(requestInfo));
//        return result;
//    }
//
//
//    @AfterThrowing(pointcut = "requestServer()", throwing = "e")
//    public void doAfterThrow(JoinPoint joinPoint, RuntimeException e) {
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//        String deviceId = request.getHeader("deviceId");
//        //从Header中获取用户信息
//        JinsJwtInfo jinsJwtInfo = LoginUserUtil.getCurrentUser();
//
//        RequestErrorInfo requestErrorInfo = new RequestErrorInfo();
//        // requestErrorInfo.setIp(request.getRemoteAddr());
//        requestErrorInfo.setUserName(jinsJwtInfo.getUserName());
//        requestErrorInfo.setUrl(request.getRequestURL().toString());
//        requestErrorInfo.setHttpMethod(request.getMethod());
//        requestErrorInfo.setDeviceId(deviceId);
//        requestErrorInfo.setClassMethod(String.format("%s.%s", joinPoint.getSignature().getDeclaringTypeName(),
//                joinPoint.getSignature().getName()));
//        requestErrorInfo.setRequestParams(getRequestParamsByJoinPoint(joinPoint));
//        requestErrorInfo.setException(ExceptionUtil.stacktraceToOneLineString(e));
//        LOGGER.error("Error Request Info      : {}", JSONUtil.toJsonStr(requestErrorInfo));
//    }
//
//    /**
//     * 获取入参
//     * @param proceedingJoinPoint
//     *
//     * @return
//     * */
//    private Map<String, Object> getRequestParamsByProceedingJoinPoint(ProceedingJoinPoint proceedingJoinPoint) {
//        //参数名
//        String[] paramNames = ((MethodSignature)proceedingJoinPoint.getSignature()).getParameterNames();
//        //参数值
//        Object[] paramValues = proceedingJoinPoint.getArgs();
//
//        return buildRequestParam(paramNames, paramValues);
//    }
//
//    private Map<String, Object> getRequestParamsByJoinPoint(JoinPoint joinPoint) {
//        //参数名
//        String[] paramNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();
//        //参数值
//        Object[] paramValues = joinPoint.getArgs();
//
//        return buildRequestParam(paramNames, paramValues);
//    }
//
//    private Map<String, Object> buildRequestParam(String[] paramNames, Object[] paramValues) {
//        Map<String, Object> requestParams = new HashMap<>();
//        for (int i = 0; i < paramNames.length; i++) {
//            Object value = paramValues[i];
//
//            //如果是文件对象
//            if (value instanceof MultipartFile) {
//                MultipartFile file = (MultipartFile) value;
//                value = file.getOriginalFilename();  //获取文件名
//            }
//
//            requestParams.put(paramNames[i], value);
//        }
//
//        return requestParams;
//    }
//
//    @Data
//    public class RequestInfo {
////        private String ip;
//        private String userName;
//        private String url;
//        private String httpMethod;
//        private String classMethod;
//        private Object requestParams;
//        private Object result;
//        private Long timeCost;
//        private String deviceId;
//    }
//
//    @Data
//    public class RequestErrorInfo {
////        private String ip;
//        private String userName;
//        private String url;
//        private String httpMethod;
//        private String classMethod;
//        private String deviceId;
//        private Object requestParams;
//        private String exception;
//    }
//}
