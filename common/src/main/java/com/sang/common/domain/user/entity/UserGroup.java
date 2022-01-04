package com.sang.common.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sang.common.domain.base.entity.BaseModel;
import com.sang.common.domain.role.entity.Role;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.PERSIST;

/**
 * 用户组
 * @author hxy
 * @date 2021/12/31 15:53
 **/
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "USER_GROUP")
public class UserGroup extends BaseModel {

    @Column(length = 10,nullable = false,unique = true)
    private String groupName;

    @Column(length = 10,nullable = false,unique = true)
    private String groupCode;

    @Column(length = 200)
    private String comment;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "parentId")
    private List<UserGroup> children;

    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY, cascade = PERSIST)
    @JoinColumn(name="PARENT_ID")
    private UserGroup parentId;

    @JsonIgnore
    @OneToMany(mappedBy = "userGroup", cascade = PERSIST,fetch = FetchType.LAZY)
    private List<User> userList;

    @JsonIgnore
    @JoinColumn(name = "ROLE_ID")
    @ManyToMany(mappedBy = "userGroup", cascade = PERSIST,fetch=FetchType.LAZY)
    private List<Role> role;

}
