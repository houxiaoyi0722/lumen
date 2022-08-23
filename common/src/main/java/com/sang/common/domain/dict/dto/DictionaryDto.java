package com.sang.common.domain.dict.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sang.common.validate.Create;
import com.sang.common.validate.Delete;
import com.sang.common.validate.Update;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * dictDto
 */
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DictionaryDto {

    /**
     * id
     */
    @NotNull(message = "id不能为空",groups = {Update.class, Delete.class})
    private Long id;
    /**
     * 组id
     */
    @NotBlank(message = "组编号不能为空",groups = {Create.class})
    @Length(max = 10,message = "组编号长度不能大于10",groups = {Update.class,Create.class})
    private String groupId;

    /**
     * 组名称
     */
//    @InEnum(value = DictionaryEnum.class, parentCode = "1", groups = {Create.class,Update.class})
    @NotBlank(message = "组名称不能为空",groups = {Create.class})
    @Length(max = 10,message = "组名称长度不能大于10",groups = {Update.class,Create.class})
    private String groupName;

    /**
     * 备注
     */
    private String comment;

    /*@Valid 嵌套校验记录一下
    private List<DictionaryItem> dictionaryItems;*/
}
