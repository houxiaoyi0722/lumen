package com.sang.common.domain.router.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sang.common.domain.role.entity.Role;
import com.sang.common.domain.router.entity.Router;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.PERSIST;

/**
 * @author hxy
 * @date 2022/1/25 16:20
 **/
public class RouterDto {


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
     * 排序
     */
    private Integer orderBy;

    private List<Router> children = null;


}
