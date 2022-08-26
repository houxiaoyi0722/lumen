package com.sang.common.domain.auth.authorization.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sang.common.domain.base.entity.BaseModel;
import io.ebean.annotation.DbComment;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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
@Table(name = "user_group")
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
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PARENT_ID")
    private UserGroup parentId;

    @JsonIgnore
    @OneToMany(mappedBy = "userGroup",fetch = FetchType.LAZY)
    private List<User> userList;

}
