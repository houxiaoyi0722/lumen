package com.sang.common.domain.auth.authentication.router.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hxy
 * @date 2022/1/25 16:20
 **/
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class RouterDto {

    /**
     * id
     */
    private Long id;
    /**
     * 路由名称
     */
    private String name;
    /**
     * 访问路径
     */
    private String path;
    /**
     * 相对路径 根目录开始
     */
    private String redirect;
    /**
     * component组件
     */
    private String component;
    /**
     * 元数据 json格式
     */
    private String mate;
    /**
     * 描述
     */
    private String description;
    /**
     * 是否隐藏
     */
    private Boolean hidden;
    /**
     * alwaysShow
     */
    private Boolean alwaysShow;
    /**
     * 上级路由id
     */
    private Long parentId;
    /**
     * 排序
     */
    private Integer orderBy;

    private List<RouterDto> children = new ArrayList<>();


}