package com.sang.common.domain.user.entity;

import com.sang.common.domain.base.entity.BaseModel;
import lombok.*;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

/**
 * 用户
 * @author hxy
 * @date 2021/12/30 17:00
 **/
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@Entity
@Table(name = "USER")
public class User extends BaseModel {

    /**
     * 姓名
     */
    private String name;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 电话
     */
    private String phone;

    /**
     * 移动电话
     */
    private String mobilePhone;

    /**
     * 邮箱地址
     */
    private String email;



}
