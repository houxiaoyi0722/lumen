package com.sang.common.domain.storage.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StorageDto {

    /**
     * id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 原文件名
     */
    private String originalFileName;

    /**
     * 文件类型/后缀名
     */
    private String suffix;

    /**
     * 业务代码
     */
    private String businessCode;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 下载地址
     */
    private String downLoadUrl;
}
