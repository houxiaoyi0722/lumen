package com.sang.response;

import com.sang.constants.ResultCodeEnum;
import lombok.*;

/**
 * 统一返回对象
 * @author xiaoy
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Result<T> {

    private String message;
    private Integer code;
    private T data;

    public static <T> Result<T> build(String message,Integer code,T data) {
        return new Result<>(message, code, data);
    }

    public static <T> Result<T> ok() {
        return new Result<>(ResultCodeEnum.SUCCESS.getMessage(), ResultCodeEnum.SUCCESS.getCode());
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>(ResultCodeEnum.SUCCESS.getMessage(), ResultCodeEnum.SUCCESS.getCode(), data);
    }

    public static Result error(String message,Integer code) {
        return new Result<>(ResultCodeEnum.SUCCESS.getMessage(), ResultCodeEnum.SUCCESS.getCode());
    }

    public static Result unhandledException(String message,Integer code) {
        return new Result<>(ResultCodeEnum.ERROR.getMessage(), ResultCodeEnum.ERROR.getCode());
    }

    public Result(String message, Integer code) {
        this.message = message;
        this.code = code;
    }
}
