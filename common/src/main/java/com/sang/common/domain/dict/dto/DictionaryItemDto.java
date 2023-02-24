package com.sang.common.domain.dict.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.sang.common.domain.dict.entity.DictionaryItem;
import com.sang.common.validate.Create;
import com.sang.common.validate.Update;
import com.sang.common.validate.unique.UniqueValue;
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
    @UniqueValue(targetClass = DictionaryItem.class, column = "itemKey",groups = {Update.class, Create.class})
    private String itemKey;

    /**
     * 备注
     */
    private String comment;

    private DictionaryDto dictionary;
}
