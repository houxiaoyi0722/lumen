package com.sang.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ResultCodeEnum {

    SUCCESS(200,"请求成功"),
    ERROR(500,"未处理异常"),
    CLASS_TYPE_NOT_EXTEND_JOB(50001,"该类未继承job接口，请检查");

    private Integer code;
    private String message;
}
