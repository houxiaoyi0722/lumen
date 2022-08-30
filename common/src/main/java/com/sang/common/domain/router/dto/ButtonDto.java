package com.sang.common.domain.router.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sang.common.validate.Delete;
import com.sang.common.validate.Update;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 页面按钮维护
 * 页面按钮维护
 * 数据传输对象
 * hxy 2022-08-30 17:45:30
*/
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ButtonDto {

    /**
     * id
    */
    @NotNull(message = "id不能为空",groups = {Delete.class,Update.class})
    private Long id;

    /**
     * 按钮code
     */
    private String buttonCode;

    /**
     * 按钮名称
     */
    private String buttonName;

    /**
     * 描述
     */
    private String description;


}
