package com.sang.common.handle;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.json.JSONUtil;
import com.sang.common.response.Result;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理
 * @author hxy
 */
@RestControllerAdvice(annotations = RestController.class)
@Log4j2
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Exception的异常时，打印日志信息
     * @param e 异常
     * @param request 请求信息
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<Result> handlerAllException(Exception e, HttpServletRequest request) throws Exception {
        // 异常发生时，打印ERROR级别的日志信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestErrorInfo requestErrorInfo = new RequestErrorInfo();
        requestErrorInfo.setUserName(authentication.getPrincipal().toString());
        requestErrorInfo.setUrl(request.getRequestURL().toString());
        requestErrorInfo.setException(ExceptionUtil.stacktraceToString(e));
        logger.error("Error Request Info      : {}", JSONUtil.toJsonStr(requestErrorInfo));

        HttpStatus errorStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(Result.error(e.getMessage(), errorStatus.value()), errorStatus);
    }



    @Data
    public class RequestErrorInfo {
        //        private String ip;
        private String userName;
        private String url;
        private String httpMethod;
        private String deviceId;
        private String exception;
    }

}
