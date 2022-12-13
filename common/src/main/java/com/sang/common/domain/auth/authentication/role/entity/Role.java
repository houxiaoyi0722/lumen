package com.sang.common.domain.auth.authentication.role.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sang.common.domain.auth.authorization.user.entity.User;
import com.sang.common.domain.base.entity.BaseModel;
import com.sang.common.domain.router.entity.Router;
import io.ebean.annotation.DbComment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
@Table(name = "role")
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

}
