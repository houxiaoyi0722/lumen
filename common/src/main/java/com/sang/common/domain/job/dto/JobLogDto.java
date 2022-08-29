package com.sang.common.domain.job.dto;

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
 * Job管理
 * Job执行日志
 * 数据传输对象
 * hxy 2022-08-29 14:25:42
*/
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobLogDto {

    /**
     * id
    */
    @NotNull(message = "id不能为空",groups = {Delete.class,Update.class})
    private Long id;

}
