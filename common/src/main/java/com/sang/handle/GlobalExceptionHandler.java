//package com.sang.handle;
//
//import cn.hutool.core.exceptions.ExceptionUtil;
//import cn.hutool.json.JSONUtil;
//import com.jins.common.entity.auth.JinsJwtInfo;
//import com.jins.common.exception.BaseException;
//import com.jins.common.exception.BizException;
//import com.jins.common.exception.NoPermissionsException;
//import com.jins.common.utils.LoginUserUtil;
//import lombok.Data;
//import lombok.extern.log4j.Log4j2;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Date;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
///**
// * 全局异常处理
// * @author hxy
// */
//@RestControllerAdvice(annotations = RestController.class)
//@Log4j2
//public class GlobalExceptionHandler {
//    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
//
//    /**
//     * NoPermissionsException异常处理
//     * @param e
//     * @param request
//     * @return
//     */
//    @ExceptionHandler(NoPermissionsException.class)
//    @ResponseBody
//    public ResponseEntity<Map<String,Object>> handleNoPermissionsException(NoPermissionsException e, HttpServletRequest request) {
//        JinsJwtInfo jinsJwtInfo = LoginUserUtil.getCurrentUser();
//        RequestErrorInfo requestErrorInfo = new RequestErrorInfo();
//        requestErrorInfo.setUserName(jinsJwtInfo.getUserName());
//        requestErrorInfo.setUrl(request.getRequestURL().toString());
//        requestErrorInfo.setHttpMethod(request.getMethod());
//        requestErrorInfo.setDeviceId(request.getHeader("deviceId"));
//        requestErrorInfo.setException(ExceptionUtil.stacktraceToOneLineString(e));
//        logger.error("Error Request Info      : {}", JSONUtil.toJsonStr(requestErrorInfo));
//
//        HttpStatus errorStatus = HttpStatus.UNAUTHORIZED;
//        Map<String, Object> errorAttributes = getErrorAttributes(request, errorStatus, e);
//        return new ResponseEntity<>(errorAttributes, errorStatus);
//    }
//
//    /**
//     * BaseException的异常处理
//     * @param e 异常
//     * @param request 请求信息
//     * @return 处理后的响应信息
//     */
//    @ExceptionHandler(BaseException.class)
//    @ResponseBody
//    public ResponseEntity<Map<String, Object>> handlerBaseException(BaseException e, HttpServletRequest request) {
//        JinsJwtInfo jinsJwtInfo = LoginUserUtil.getCurrentUser();
//        RequestErrorInfo requestErrorInfo = new RequestErrorInfo();
//        requestErrorInfo.setUserName(jinsJwtInfo.getUserName());
//        requestErrorInfo.setUrl(request.getRequestURL().toString());
//        requestErrorInfo.setHttpMethod(request.getMethod());
//        requestErrorInfo.setDeviceId(request.getHeader("deviceId"));
//        requestErrorInfo.setException(ExceptionUtil.stacktraceToOneLineString(e));
//        logger.error("Error Request Info      : {}", JSONUtil.toJsonStr(requestErrorInfo));
//
//        HttpStatus errorStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//        Map<String, Object> errorAttributes = getErrorAttributes(request, errorStatus, e);
//        return new ResponseEntity<>(errorAttributes, errorStatus);
//    }
//
//    /**
//     * Exception的异常时，打印日志信息
//     * @param e 异常
//     * @param request 请求信息
//     */
//    @ExceptionHandler(Exception.class)
//    public void handlerAllException(Exception e, HttpServletRequest request) throws Exception {
//        // 异常发生时，打印ERROR级别的日志信息
//        JinsJwtInfo jinsJwtInfo = LoginUserUtil.getCurrentUser();
//        RequestErrorInfo requestErrorInfo = new RequestErrorInfo();
//        requestErrorInfo.setUserName(jinsJwtInfo.getUserName());
//        requestErrorInfo.setUrl(request.getRequestURL().toString());
//        requestErrorInfo.setHttpMethod(request.getMethod());
//        requestErrorInfo.setDeviceId(request.getHeader("deviceId"));
//        requestErrorInfo.setException(ExceptionUtil.stacktraceToOneLineString(e));
//        logger.error("Error Request Info      : {}", JSONUtil.toJsonStr(requestErrorInfo));
//        throw e;
//    }
//
//    /**
//     * base异常
//     * @param request 请求
//     * @param errorStatus 状态码
//     * @param e 异常
//     * @return 异常信息
//     */
//    private Map<String, Object> getErrorAttributes(HttpServletRequest request, HttpStatus errorStatus, BaseException e) {
//        Map<String, Object> errorAttributes = new LinkedHashMap();
//        errorAttributes.put("timestamp", new Date());
//        errorAttributes.put("status", errorStatus.value());
//        errorAttributes.put("error", errorStatus.getReasonPhrase());
//        // 如果定义了Code，则将Code加入到响应对象中
//        if(e.getCode() != null) {
//            errorAttributes.put("code", e.getCode());
//        }
//
//        errorAttributes.put("message", e.getMessage());
//        errorAttributes.put("path", request.getServletPath());
//        return errorAttributes;
//    }
//
//    /**
//     * 业务异常
//     * @param request 请求
//     * @param errorStatus 状态码
//     * @param e 异常
//     * @return 异常信息
//     */
//    private Map<String, Object> getErrorAttributes(HttpServletRequest request, HttpStatus errorStatus, BizException e) {
//        Map<String, Object> errorAttributes = this.getErrorAttributes(request, errorStatus, (BaseException) e);
//        if(e.getErrorList() != null) {
//            errorAttributes.put("errorList", e.getErrorList());
//        }
//
//        return errorAttributes;
//    }
//
//    @Data
//    public class RequestErrorInfo {
//        //        private String ip;
//        private String userName;
//        private String url;
//        private String httpMethod;
//        private String deviceId;
//        private String exception;
//    }
//
//}
