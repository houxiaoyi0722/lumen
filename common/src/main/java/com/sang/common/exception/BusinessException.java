package com.sang.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 业务异常
 */
@AllArgsConstructor
@Setter
@Getter
public class BusinessException extends BaseException{
    protected Integer code;
    protected String message;
}
