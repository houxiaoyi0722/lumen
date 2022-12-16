package com.sang.common.domain.router.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sang.common.domain.auth.authentication.role.entity.Role;
import com.sang.common.validate.Create;
import com.sang.common.validate.Delete;
import com.sang.common.validate.Update;
import io.ebean.annotation.DbComment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * 路由管理
 * @author hxy
 **/
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RouterDto {

    /**
     * id
     */
    @NotNull(message = "id不能为空",groups = {Delete.class,Update.class})
    private Long id;

    /**
     * 路由名称
     */
    @NotBlank(message = "名称不能为空",groups = {Create.class,Update.class})
    @Length(max = 100,message = "名称长度在1-100之间",groups = {Create.class, Update.class})
    private String name;

    /**
     * 访问路径
     */
    @NotBlank(message = "访问路径不能为空",groups = {Create.class,Update.class})
    @Length(max = 200,message = "访问路径长度在1-200之间",groups = {Create.class, Update.class})
    private String path;

    /**
     * 相对路径 根目录开始
     */
//    @NotBlank(message = "相对路径不能为空",groups = {Create.class,Update.class})
    @Length(max = 200,message = "相对路径长度在1-200之间",groups = {Create.class, Update.class})
    private String redirect;

    /**
     * component组件
     */
    @NotBlank(message = "相对路径不能为空",groups = {Create.class,Update.class})
    @Length(max = 200,message = "相对路径长度在1-200之间",groups = {Create.class, Update.class})
    private String component;

    /**
     * 元数据 json格式
     */
    private Map<String,Object> mate;

    /**
     * 描述
     */
    @Length(max = 300,message = "元数据长度不能大于300",groups = {Create.class, Update.class})
    private String description;

    /**
     * 是否隐藏
     */
    @NotNull(message = "是否隐藏不能为空",groups = {Create.class,Update.class})
    private Boolean hidden;

    /**
     * alwaysShow
     */
    @NotNull(message = "alwaysShow不能为空",groups = {Create.class,Update.class})
    private Boolean alwaysShow;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空",groups = {Create.class,Update.class})
    private Integer orderBy;

    /**
     * @JsonIgnore
     * 上级路由id
     */
    private RouterDto parentId;

    /**
     * 角色权限
     */
    private List<Role> roles;

}
