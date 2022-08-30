package com.sang.common.domain.auth.authorization.user.entity;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sang.common.domain.auth.authentication.role.entity.Role;
import com.sang.common.domain.base.entity.BaseModel;
import io.ebean.annotation.DbComment;
import io.ebean.annotation.Index;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
@Table(name = "user")
@Index(name = "user_name", columnNames = "user_name")
@DbComment("角色表")
public class User extends BaseModel implements UserDetails {

    @DbComment("姓名")
    @Column(length = 100,nullable = false,unique = true)
    private String name;

    @DbComment("用户名")
    @Column(length = 100,nullable = false,unique = true)
    private String userName;

    @DbComment("密码")
    @Column(length = 100,nullable = false,unique = true,updatable = false)
    private String password;

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

    @DbComment("是否启用")
    @Column
    private Boolean enabled = true;

    @DbComment("账户未过期")
    @Column
    private Boolean accountNonExpired = true;

    @DbComment("账户锁定")
    @Column
    private Boolean accountNonLocked = false;

    @DbComment("凭证未过期")
    @Column
    private Boolean credentialsNonExpired = true;

    @JsonIgnore
    @ManyToMany(fetch=FetchType.LAZY)
    private List<Role> roles;

    @ManyToOne(fetch=FetchType.LAZY)
    private UserGroup userGroup;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> authorities = this.roles.stream().map(Role::getRoleCode).collect(Collectors.toList());
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        for (String role : authorities) {
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role));
        }
        return simpleGrantedAuthorities;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public void setPassword(String password) {
        if (StrUtil.isNotBlank(password)) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            this.password = bCryptPasswordEncoder.encode(password);
        }
    }
}
