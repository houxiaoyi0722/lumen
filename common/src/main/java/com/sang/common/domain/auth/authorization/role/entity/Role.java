package com.sang.common.domain.auth.authorization.role.entity;

import cn.hutool.core.collection.CollUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sang.common.domain.auth.authorization.permission.entity.Permission;
import com.sang.common.domain.auth.authentication.user.entity.User;
import com.sang.common.domain.base.entity.BaseModel;
import com.sang.common.domain.router.entity.Router;
import io.ebean.annotation.DbComment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色
 * @author hxy
 * @date 2021/12/30 17:14
 **/
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sys_role")
@DbComment("角色表")
public class Role extends BaseModel {

    /**
     * 角色名称
     */
    @DbComment("角色名称")
    @Column(length = 20,nullable = false)
    private String roleName;

    /**
     * 角色代码
     */
    @DbComment("角色代码")
    @Column(length = 20,nullable = false)
    private String roleCode;

    /**
     * 备注
     */
    @DbComment("备注")
    @Column(length = 200)
    private String comment;

    @DbComment("上级角色id")
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "parentId")
    private List<Role> children = new ArrayList<>();

    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PARENT_ID")
    private Role parentId;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY)
    private List<User> users;

    @JsonIgnore
    @ManyToMany(fetch=FetchType.LAZY,mappedBy = "roles")
    private List<Router> routers;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,mappedBy = "roles")
    private List<Permission> permissions;

    /**
     * 获取当前角色和上级角色的所有权限列表
     * @return
     */
    public List<Permission> getPermissions() {
        return parentPermission(this).stream().distinct().collect(Collectors.toList());
    }

    private List<Permission> parentPermission(Role parentId) {
        if (parentId != null) {
            return CollUtil.unionAll(parentId.permissions,parentPermission(parentId.getParentId()));
        }
        return Collections.emptyList();
    }

}
