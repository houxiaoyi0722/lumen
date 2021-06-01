package com.sang.annotation;

import java.lang.annotation.*;

/**
 * @author xiaoy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Inherited
public @interface Dictionary {
    /**
     * 组id
     */
    String groupId();

    /**
     * 目标字段名称,为空则默认该字段
     */
    String targetField() default "";
}
