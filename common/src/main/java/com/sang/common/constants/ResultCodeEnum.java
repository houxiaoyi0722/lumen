package com.sang.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ResultCodeEnum {

    SUCCESS(200,"请求成功"),
    ERROR(500,"未处理异常");

    private Integer code;
    private String message;
}
