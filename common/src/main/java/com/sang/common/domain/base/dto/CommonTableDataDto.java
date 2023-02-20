package com.sang.common.domain.base.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

/**
 * vxe-table 数据保存对象
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonTableDataDto<T> {
    /**
     * 新增列表
     */
    private List<T> insertList;
    /**
     * 更新列表
     */
    private List<T> updateList;
    /**
     * 删除列表
     */
    private List<T> removeList;

}
