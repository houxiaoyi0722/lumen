package com.sang.system.example.validator;

import com.sang.common.domain.dict.dto.DictionaryDto;
import com.sang.common.validate.Create;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@SpringBootTest
public class TestValidator {

    @Qualifier("validator")
    @Autowired
    private Validator validator;


    @Test
    public void testValidator() {
        // 打印，查看 validator 的类型
        System.out.println(validator);

        // 创建 UserAddDTO 对象
        DictionaryDto addDTO = new DictionaryDto();
        addDTO.setGroupName("123");
        addDTO.setGroupId("123");
        // 校验
        Set<ConstraintViolation<DictionaryDto>> result = validator.validate(addDTO, Create.class);
        // 打印校验结果
        for (ConstraintViolation<DictionaryDto> constraintViolation : result) {
            // 属性:消息
            System.out.println(constraintViolation.getPropertyPath() + ":" + constraintViolation.getMessage());
        }
    }


}
