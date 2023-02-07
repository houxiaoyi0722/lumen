package com.sang.common.domain.auth.authorization.permission.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sang.common.domain.auth.authorization.permission.entity.finder.PermissionFinder;
import com.sang.common.domain.auth.authorization.role.entity.Role;
import com.sang.common.domain.base.entity.BaseModel;
import com.sang.common.domain.router.entity.Router;
import io.ebean.annotation.DbComment;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * 权限管理
 * 操作权限
 * 数据模型
 * hxy 2022-12-13 14:11:40
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@Entity
@Table(name = "sys_permission")
@DbComment("操作权限表")
public class Permission extends BaseModel {

    public static final PermissionFinder finder = PermissionFinder.builder().build();

    /**
     * 权限code
     */
    @Column(length = 100)
    @DbComment("权限code")
    private String code;

    /**
     * 权限名称
     */
    @Column(length = 100)
    @DbComment("权限名称")
    private String name;

    /**
     * 备注
     */
    @Column(length = 200)
    @DbComment("备注")
    private String comment;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Role> roles;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Router router;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return this.getId().equals(that.getId()) && code.equals(that.code) && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, comment);
    }
}
