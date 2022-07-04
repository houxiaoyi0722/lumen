package com.sang.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum StorageEnum {

    FILE_NOT_EXIST(5001,"文件不存在或已删除");


    private Integer code;
    private String message;
}
