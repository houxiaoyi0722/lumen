package com.sang.common.annotation.dictionary;

import java.lang.annotation.*;

/**
 * 字典转换标记注解
 * 转换只支持List或者entity
 * @author xiaoy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
public @interface Transform {

    /**
     * 包装内需要转换的目标字段,优先级大于{@link TargetField}
     * @return
     */
    String targetField() default "";

}
