package com.sang.common.constants;

import cn.hutool.core.util.StrUtil;
import io.ebean.annotation.DbEnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum DictionaryEnum implements IntArrayValuable{

    GENDER("GD",null,"性别",""),
    MAN("GD0","GD","男",""),
    WOMAN("GD1","GD","女","");


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
