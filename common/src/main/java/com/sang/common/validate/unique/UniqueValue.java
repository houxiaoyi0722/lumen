package com.sang.common.validate.unique;

import com.sang.common.domain.base.entity.BaseModel;
import org.springframework.core.annotation.AliasFor;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = UniqueValueValidator.class)
public @interface UniqueValue {

    /**
     * 目标类型
     * @return
     */
    @AliasFor("path")
    Class<? extends BaseModel> targetClass();

    /**
     * 目标字段
     */
    String column() default "id";

    /**
     * @return 提示内容
     */
    String message() default "该值已存在: {}";

    /**
     * @return 分组
     */
    Class<?>[] groups() default {};

    /**
     * @return Payload 数组
     */
    Class<? extends Payload>[] payload() default {};

}
