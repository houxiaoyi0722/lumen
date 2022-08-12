package com.sang.common.constants;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum DictionaryEnum implements IntArrayValuable{
    EMPTY(1,null,"","");

    private Integer code;
    private Integer parentCode;
    private String name;
    private String comment;

    @Override
    public List<Integer> array(String parentCode) {
        if (StrUtil.isNotBlank(parentCode))
            return Arrays.stream(values())
                    .filter(item -> Integer.valueOf(parentCode).equals(item.getParentCode()))
                    .map(DictionaryEnum::getCode)
                    .collect(Collectors.toList());
        return Collections.emptyList();
    }
}
