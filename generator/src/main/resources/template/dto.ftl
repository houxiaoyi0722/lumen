package com.sang.common.domain.${domain}.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.sang.common.validate.Delete;
import com.sang.common.validate.Update;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * ${domainComment}
 * ${modelComment}
 * ${fileComment}
 * @author ${author}
 * @since ${createDate?string("yyyy-MM-dd HH:mm:ss")}
*/
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ${model}Dto {

    /**
     * id
    */
    @JsonSerialize(using = ToStringSerializer.class)
    @NotNull(message = "id不能为空",groups = {Delete.class,Update.class})
    private Long id;

}
