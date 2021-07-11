package com.sang.annotation.dictionary;

import java.lang.annotation.*;

/**
 * @author xiaoy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
public @interface Transform {

}
