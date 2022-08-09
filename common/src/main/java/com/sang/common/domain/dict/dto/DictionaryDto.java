package com.sang.common.domain.dict.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * dictDto
 */
@Getter @Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DictionaryDto {

    /**
     * 组id
     */
    private String groupId;

    /**
     * 组名称
     */
    private String groupName;

    /**
     * 备注
     */
    private String comment;


}
