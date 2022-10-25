package com.sang.common.domain.auth.authorization.user.entity;

import com.sang.common.domain.auth.authorization.user.entity.finder.UserExtFinder;
import com.sang.common.domain.base.entity.BaseModel;
import io.ebean.annotation.DbComment;
import lombok.*;

import javax.persistence.*;

/**
 * 用户模块
 * 用户扩展信息
 * 数据模型
 * hxy 2022-10-24 17:02:14
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@Entity
@Table
@DbComment("用户扩展信息表")
public class UserExt extends BaseModel {

    public static final UserExtFinder finder = UserExtFinder.builder().build();


    @DbComment("用户头像")
    @Column(length = 500)
    private String avatar;

    @DbComment("简介")
    @Column(length = 200)
    private String intro;


    @OneToOne(fetch= FetchType.LAZY)
    private User user;

}
