package com.sang.common.domain.base.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CommonIdDto {
    /**
     * id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
}
