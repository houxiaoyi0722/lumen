package com.sang.common.domain.${domain?lower_case}.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * ${domainComment}
 * ${modelComment}
 * ${fileComment}
 * ${author} ${createDate?string("yyyy-MM-dd HH:mm:ss")}
*/
@Getter @Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ${model}Dto {

}
