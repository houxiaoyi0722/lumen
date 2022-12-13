package com.sang.common.domain.auth.authentication.permissions.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sang.common.domain.auth.authentication.role.entity.Role;
import com.sang.common.domain.base.entity.BaseModel;
import com.sang.common.domain.auth.authentication.permissions.entity.finder.PermissionsFinder;
import com.sang.common.domain.router.entity.Router;
import io.ebean.annotation.DbComment;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
@Table
@DbComment("操作权限表")
public class Permissions extends BaseModel {

    public static final PermissionsFinder finder = PermissionsFinder.builder().build();

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
    @ManyToOne(fetch = FetchType.LAZY)
    private Role role;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Router router;

}
