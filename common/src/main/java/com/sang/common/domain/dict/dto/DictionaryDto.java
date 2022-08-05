package com.sang.common.domain.dict.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sang.common.domain.dict.entity.Dictionary;
import lombok.*;

/**
 * dictDto
 */
@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DictionaryDto extends Dictionary {
    
}
