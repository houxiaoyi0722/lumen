package com.sang.common.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sang.common.domain.base.entity.BaseModel;
import com.sang.common.domain.role.entity.Role;
import io.ebean.annotation.DbComment;
import lombok.*;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.PERSIST;

/**
 * 用户
 * @author hxy
 * @date 2021/12/30 17:00
 **/
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "USER")
@DbComment("角色表")
public class User extends BaseModel {

    /**
     * 姓名
     */
    @Column(length = 100,nullable = false,unique = true)
    private String name;

    /**
     * 用户名
     */
    @Column(length = 100,nullable = false,unique = true)
    private String userName;

    /**
     * 密码
     */
    @Column(length = 100,nullable = false,unique = true)
    private String password;

    /**
     * 电话
     */
    @Column(length = 20)
    private String phone;

    /**
     * 移动电话
     */
    @Column(length = 20)
    private String mobilePhone;

    /**
     * 邮箱地址
     */
    @Column(length = 50)
    private String email;

    @JsonIgnore
    @ManyToMany(cascade = PERSIST,fetch=FetchType.LAZY)
    private List<Role> roles;

    @ManyToOne(fetch=FetchType.LAZY)
    private UserGroup userGroup;

}
