package com.sang.common.domain.router.entity;

import com.sang.common.domain.auth.authentication.role.entity.Role;
import com.sang.common.domain.base.entity.BaseModel;
import com.sang.common.domain.router.entity.finder.ButtonFinder;
import io.ebean.annotation.DbComment;
import lombok.*;

import javax.persistence.*;

/**
 * 页面按钮维护
 * 页面按钮维护
 * 数据模型
 * hxy 2022-08-30 17:45:30
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@Entity
@Table
@DbComment("页面按钮维护")
public class Button extends BaseModel {

    public static final ButtonFinder finder = ButtonFinder.builder().build();

    /**
     * 按钮code
     */
    private String buttonCode;

    /**
     * 按钮名称
     */
    private String buttonName;

    /**
     * 描述
     */
    private String description;

    @ManyToOne(fetch= FetchType.LAZY)
    private Role role;

    @ManyToOne(fetch= FetchType.LAZY)
    private Router router;
}
