package com.sang.common.domain.router.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sang.common.domain.base.entity.BaseModel;
import com.sang.common.domain.role.entity.Role;
import io.ebean.annotation.DbComment;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.PERSIST;

/**
 * 路由管理
 * @author hxy
 * @date 2022/1/25 14:44
 **/
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "ROUTER")
@DbComment("路由表")
public class Router extends BaseModel {

    @Column(length = 100)
    @DbComment("路由名称")
    private String name;

    @DbComment("访问路径")
    @Column(length = 200)
    private String path;

    @DbComment("相对路径 根目录开始")
    @Column(length = 200)
    private String redirect;

    @DbComment("component组件")
    @Column(length = 200)
    private String component;

    // todo ebean json
    @DbComment("元数据 json格式")
    @Column(length = 500)
    private String mate;

    @DbComment("描述")
    @Column(length = 300)
    private String description;

    @DbComment("是否隐藏")
    @Column
    private Boolean hidden;

    @DbComment("alwaysShow")
    @Column
    private Boolean alwaysShow;

    @DbComment("排序")
    @Column
    private Integer orderBy;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "parentId",cascade = PERSIST)
    private List<Router> children = new ArrayList<>();

    /**
     * 上级路由id
     */
    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PARENT_ID")
    private Router parentId;

    @JsonIgnore
    @ManyToMany(fetch=FetchType.LAZY)
    private List<Role> roles;


}
