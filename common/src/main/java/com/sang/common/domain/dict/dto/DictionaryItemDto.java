package com.sang.common.domain.dict.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.ebean.annotation.DbComment;
import lombok.*;

import javax.persistence.Column;

/**
 * dictItemDto
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DictionaryItemDto {
    /**
     * value
     */
    private String itemValue;

    /**
     * key
     */
    private String itemKey;

    /**
     * 备注
     */
    private String comment;
}
