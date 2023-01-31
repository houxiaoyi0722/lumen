package com.sang.common.domain.router.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 组合数据处理dto
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TableDataDto <T> {
    private List<T> insertList;
    private List<T> updateList;
    private List<T> removeList;
    private Long id;
}
