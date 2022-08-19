package com.sang.common.handle;

import com.sang.common.constants.ResultCodeEnum;
import com.sang.common.constants.StringConst;
import com.sang.common.exception.BaseException;
import com.sang.common.exception.BusinessException;
import com.sang.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

/**
 * 全局异常处理
 * @author hxy
 */
@RestControllerAdvice(annotations = RestController.class)
@Slf4j
public class GlobalExceptionHandler {

    private ObjectError item;

    /**
     * BusinessException 异常发生时，打印日志信息
     * @param e 异常
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResponseEntity<Result> handlerBusinessException(BusinessException e) {
        return new ResponseEntity<>(Result.error(e.getMessage(), e.getCode()), HttpStatus.OK);
    }

    /**
     * BusinessException 异常发生时，打印日志信息
     * @param e 异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<Result> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream().map(item -> item.getField() + " : " + item.getDefaultMessage()).collect(Collectors.joining(StringConst.COMMA));
        return new ResponseEntity<>(Result.error(message, ResultCodeEnum.METHOD_ARGUMENT_NOT_VALID.getCode()), HttpStatus.OK);
    }

    /**
     * BaseException 异常发生时，打印日志信息
     * @param e 异常
     */
    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public ResponseEntity<Result> handlerBusinessException(BaseException e) {
        return new ResponseEntity<>(Result.error(e.getMessage(), e.getCode()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Exception的异常时，打印日志信息
     * @param e 异常
     * @param request 请求信息
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<Result> handlerAllException(Exception e, HttpServletRequest request) {
        HttpStatus errorStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(Result.error(e.getMessage(), errorStatus.value()), errorStatus);
    }

}
