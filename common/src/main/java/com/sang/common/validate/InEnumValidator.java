package com.sang.common.validate;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.sang.common.constants.IntArrayValuable;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class InEnumValidator implements ConstraintValidator<InEnum, String> {

    /**
     * 值数组
     */
    private Set<String> values;

    @Override
    public void initialize(InEnum annotation) {
        IntArrayValuable[] values = annotation.value().getEnumConstants();
        String parentCode = annotation.parentCode();
        if (values.length == 0) {
            this.values = Collections.emptySet();
        } else {
            this.values = new HashSet<>(values[0].array(parentCode));
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 校验通过
        if (values.contains(value)) {
            return true;
        }
        // 校验不通过，自定义提示语句（因为，注解上的 value 是枚举类，无法获得枚举类的实际值）
        context.disableDefaultConstraintViolation(); // 禁用默认的 message 的值
        String replace = StrUtil.format(context.getDefaultConstraintMessageTemplate(),JSON.toJSONString(values));
        context.buildConstraintViolationWithTemplate(replace).addConstraintViolation(); // 重新添加错误提示语句
        return false;
    }

}
