package com.sang.common.domain.auth.authentication.router.entity;

import cn.hutool.core.collection.CollectionUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sang.common.domain.base.entity.BaseModel;
import com.sang.common.domain.auth.authentication.role.entity.Role;
import com.sang.common.domain.auth.authentication.router.dto.RouterDto;
import io.ebean.annotation.DbComment;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
@Table(name = "router")
@DbComment("路由表")
public class Router extends BaseModel {

    /**
     * 路由名称
     */
    @Column(length = 100)
    @DbComment("路由名称")
    private String name;

    /**
     * 访问路径
     */
    @DbComment("访问路径")
    @Column(length = 200)
    private String path;

    /**
     * 相对路径 根目录开始
     */
    @DbComment("相对路径 根目录开始")
    @Column(length = 200)
    private String redirect;

    /**
     * component组件
     */
    @DbComment("component组件")
    @Column(length = 200)
    private String component;

    /**
     * 元数据 json格式
     */
    // todo ebean json
    @DbComment("元数据 json格式")
    @Column(length = 500)
    private String mate;

    /**
     * 描述
     */
    @DbComment("描述")
    @Column(length = 300)
    private String description;

    /**
     * 是否隐藏
     */
    @DbComment("是否隐藏")
    @Column
    private Boolean hidden;

    /**
     * alwaysShow
     */
    @DbComment("alwaysShow")
    @Column
    private Boolean alwaysShow;

    /**
     * 排序
     */
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


    public static List<RouterDto> getRootNodeRouterTree(List<RouterDto> routers) {

        //是否是根节点
        List<RouterDto> rootNode = routers.stream()
                .filter(r -> r.getParentId() ==null || r.getParentId() == 0)
                .collect(Collectors.toList());

        Map<Long, List<RouterDto>> branchs = routers.stream().filter(r -> r.getParentId() != null && r.getParentId() != 0).collect(Collectors.groupingBy(RouterDto::getParentId));

        // 构建路由树
        return getRouterTree(rootNode, branchs);
    }

    /**
     * 获取路由树
     * @return 路由
     */
    private static List<RouterDto> getRouterTree(List<RouterDto> parentRouters,Map<Long, List<RouterDto>> routers){

        for (RouterDto router : parentRouters) {
            //字路由
            List<RouterDto> childRouter = routers.get(router.getId());
            //递归边界: 子路由为空
            if (CollectionUtil.isEmpty(childRouter)) {
                continue;
            }
            router.setChildren(getRouterTree(childRouter, routers));
        }

        return parentRouters;
    }


}