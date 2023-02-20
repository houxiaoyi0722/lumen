package com.sang.common.domain.dict.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;

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

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

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

    private DictionaryDto dictionary;
}
