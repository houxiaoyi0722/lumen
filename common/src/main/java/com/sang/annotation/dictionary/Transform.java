package com.sang.annotation.dictionary;

import java.lang.annotation.*;

/**
 * @author xiaoy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Inherited
public @interface Transform {

}
