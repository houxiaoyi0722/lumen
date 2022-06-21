package com.sang.common.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sang.common.domain.base.entity.BaseModel;
import com.sang.common.domain.role.entity.Role;
import io.ebean.annotation.DbComment;
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
@DbComment("用户组")
public class UserGroup extends BaseModel {

    @DbComment("用户组名称")
    @Column(length = 10,nullable = false,unique = true)
    private String groupName;

    @DbComment("用户组代码")
    @Column(length = 10,nullable = false,unique = true)
    private String groupCode;

    @Column(length = 200)
    @DbComment("备注")
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
    @ManyToMany(cascade = PERSIST,fetch=FetchType.LAZY)
    private List<Role> roles;

}
