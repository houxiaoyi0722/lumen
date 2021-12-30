package com.sang.common.annotation.dictionary;

import java.lang.annotation.*;

/**
 * 包装类拆包目标字段标注注解
 * 用于配置通用返回对象,或标记多个目标字段
 * @author xiaoy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Inherited
public @interface TargetField {
}
