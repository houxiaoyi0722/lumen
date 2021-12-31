package com.sang.common.domain.role.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sang.common.domain.base.entity.BaseModel;
import com.sang.common.domain.user.entity.User;
import com.sang.common.domain.user.entity.UserGroup;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
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
@MappedSuperclass
@Entity
@Builder
@Table(name = "ROLE")
public class Role extends BaseModel {

    /**
     * 角色名称
     */
    @Column(length = 20,nullable = false,unique = true)
    private String roleName;

    /**
     * 角色代码
     */
    @Column(length = 20,nullable = false,unique = true)
    private String roleCode;

    /**
     * 备注
     */
    @Column(length = 200)
    private String comment;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "parentId")
    private List<Role> children;

    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PARENT_ID")
    private Role parentId;

    @JsonIgnore
    @JoinColumn(name = "USER_ID")
    @ManyToMany(mappedBy = "role", cascade = PERSIST,fetch = FetchType.LAZY)
    private List<User> userList;

    @JsonIgnore
    @JoinColumn(name = "USER_GROUP_ID")
    @ManyToMany(mappedBy = "role", cascade = PERSIST,fetch = FetchType.LAZY)
    private List<UserGroup> userGroup;
}
