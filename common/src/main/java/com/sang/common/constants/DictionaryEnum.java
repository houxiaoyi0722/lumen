package com.sang.common.constants;

import cn.hutool.core.util.StrUtil;
import io.ebean.annotation.DbEnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 该枚举类为动态字典表类型枚举,固定枚举(代码强耦合,更改后会导致错误)请按照类型自行创建
 */
@Getter
@AllArgsConstructor
public enum DictionaryEnum implements IntArrayValuable{
    /**
     * 命名应当于code相同，用于valueof反向映射
     */
    GENDER("GENDER",null,"性别",""),
    MAN("MAN","GENDER","男",""),
    WOMEN("WOMEN","GENDER","女","");


    private String code;
    private String parentCode;
    private String name;
    private String comment;

    @Override
    public List<String> array(String parentCode) {
        if (StrUtil.isNotBlank(parentCode))
            return Arrays.stream(values())
                    .filter(item -> parentCode.equals(item.getParentCode()))
                    .map(DictionaryEnum::getCode)
                    .collect(Collectors.toList());
        return Collections.emptyList();
    }

    @DbEnumValue
    public String getValue() {
        return code;
    }
}
