package com.sang.common.validate.unique;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.sang.common.domain.base.entity.BaseModel;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueValueValidator implements ConstraintValidator<UniqueValue, String> {

    /**
     * 错误消息
     */
    private String field;
    /**
     * 目标类型
     */
    private Class<? extends BaseModel> targetClass;

    @Override
    public void initialize(UniqueValue annotation) {
        this.targetClass = annotation.targetClass();
        this.field = annotation.column();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            BaseModel baseModel = targetClass.getDeclaredConstructor().newInstance();
            // 校验通过
            if (!baseModel.db().find(targetClass).where().eq(field,value).eq("deleted",0).exists()) {
                return true;
            }

            // 校验不通过，自定义提示语句（因为，注解上的 value 是枚举类，无法获得枚举类的实际值）
            context.disableDefaultConstraintViolation(); // 禁用默认的 message 的值
            String replace = StrUtil.format(context.getDefaultConstraintMessageTemplate(),JSON.toJSONString(value));
            context.buildConstraintViolationWithTemplate(replace).addConstraintViolation(); // 重新添加错误提示语句
            return false;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
