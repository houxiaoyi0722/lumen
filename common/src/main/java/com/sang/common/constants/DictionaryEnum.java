package com.sang.common.constants;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum DictionaryEnum implements IntArrayValuable{

    GENDER("1",null,"性别",""),
    MAN("0","1","男",""),
    WOMAN("1","1","女","");


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
}
