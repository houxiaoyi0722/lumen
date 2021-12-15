package com.sang.annotation.dictionary;

import java.lang.annotation.*;

/**
 * @author xiaoy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
public @interface Transform {

    /**
     * 有包装需要转换的目标字段
     * @return
     */
    String targetField() default "";

}
