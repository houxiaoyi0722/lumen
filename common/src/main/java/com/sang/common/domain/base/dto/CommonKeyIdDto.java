package com.sang.common.domain.base.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CommonKeyIdDto {

    private String id;

    private String label;

    private List<CommonKeyIdDto> children;

}
