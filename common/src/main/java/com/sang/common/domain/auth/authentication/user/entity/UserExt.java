package com.sang.common.domain.auth.authentication.user.entity;

import com.sang.common.constants.DictionaryEnum;
import com.sang.common.domain.auth.authentication.user.entity.finder.UserExtFinder;
import com.sang.common.domain.base.entity.BaseModel;
import com.sang.common.domain.storage.entity.Storage;
import io.ebean.annotation.DbComment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * 用户模块
 * 用户扩展信息
 * 数据模型
 * hxy 2022-10-24 17:02:14
 */
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@Entity
@Table(name = "sys_user_ext")
@DbComment("用户扩展信息表")
public class UserExt extends BaseModel {

    public static final UserExtFinder finder = UserExtFinder.builder().build();


    @DbComment("用户头像")
    @OneToOne(fetch= FetchType.LAZY)
    private Storage avatar;

    @DbComment("性别")
    @Column(length = 20)
    private DictionaryEnum gender;

    @DbComment("出生日期")
    private Date birthday;

    @DbComment("简介")
    @Column(length = 200)
    private String intro;

    @DbComment("电话")
    @Column(length = 20)
    private String phone;

    @DbComment("移动电话")
    @Column(length = 20)
    private String mobilePhone;

    @DbComment("地址")
    @Column(length = 200)
    private String address;

    @DbComment("邮箱地址")
    @Column(length = 50)
    private String email;

    @OneToOne(fetch= FetchType.LAZY)
    private User user;

}
