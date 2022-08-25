package com.sang.common.domain.auth.authentication.role.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sang.common.domain.base.entity.BaseModel;
import com.sang.common.domain.router.entity.Router;
import com.sang.common.domain.auth.authorization.user.entity.User;
import com.sang.common.domain.auth.authorization.user.entity.UserGroup;
import io.ebean.annotation.DbComment;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.PERSIST;

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
    @Column(length = 20,nullable = false,unique = true)
    private String roleName;

    /**
     * 角色代码
     */
    @DbComment("角色代码")
    @Column(length = 20,nullable = false,unique = true)
    private String roleCode;

    /**
     * 备注
     */
    @DbComment("备注")
    @Column(length = 200)
    private String comment;

    @DbComment("上级角色id")
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "parentId",cascade = PERSIST)
    private List<Role> children = new ArrayList<>();

    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PARENT_ID")
    private Role parentId;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles", cascade = PERSIST,fetch = FetchType.LAZY)
    private List<User> users;

    @JsonIgnore
    @ManyToMany(fetch=FetchType.LAZY,cascade = PERSIST,mappedBy = "roles")
    private List<Router> routers;

}
