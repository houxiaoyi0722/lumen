package com.sang.common.domain.base.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CommonKeyValueDto {

    private String value;

    private String label;

    private List<CommonKeyValueDto> children;

}
