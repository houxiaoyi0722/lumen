package com.sang.common.domain.dict.param;

import cn.hutool.db.Page;
import com.sang.common.validate.Create;
import com.sang.common.validate.Update;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 分页查询对象
 */
@Getter
@Setter
@AllArgsConstructor
public class DictionaryQry extends Page {

    /**
     * 组id
     */
    private String groupId;

    /**
     * 组名称
     */
    private String groupName;

}
