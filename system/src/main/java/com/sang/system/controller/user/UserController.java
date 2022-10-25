package com.sang.system.controller.user;

import com.sang.common.domain.auth.authorization.user.dto.UserDto;
import com.sang.common.domain.auth.authorization.user.dto.UserInfoDto;
import com.sang.common.domain.auth.authorization.user.entity.User;
import com.sang.common.domain.auth.authorization.user.mapper.UserMapper;
import com.sang.common.domain.auth.authorization.user.param.UserQry;
import com.sang.common.response.PageResult;
import com.sang.common.response.Result;
import com.sang.common.validate.Create;
import com.sang.common.validate.Delete;
import com.sang.common.validate.Update;
import com.sang.common.validate.user.ResetPassword;
import com.sang.system.service.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户管理
 * @author hxy
 * @date 2022/1/7 15:10
 **/
@Validated
@RestController
@RequestMapping("/lumen/user")
public class UserController {

    @Resource
    private UserService userService;

    private final UserMapper userMapper = UserMapper.mapper;

    /**
     * 用户分页查询
     * @param userQry
     * @return
     */
    @PostMapping("/users")
    public PageResult<User> list(@RequestBody UserQry userQry) {
        return PageResult.ok(userService.userList(userQry));
    }

    /**
     * 保存用户
     * @param user
     * @return
     */
    @PostMapping("/user")
    public Result<Boolean> save(@RequestBody @Validated(Create.class) UserDto user) {
        userService.save(userMapper.dtoToUser(user));
        return Result.ok();
    }

    /**
     * 按jwt获取用户详情
     * @return
     */
    @GetMapping("/userinfo")
    public Result<UserInfoDto> userinfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userinfo = userService.userinfo(authentication.getName());
        return Result.ok(userMapper.userToInfoDto(userinfo));
    }

    /**
     * 按id更新
     * @param user
     * @return
     */
    @PutMapping("/user")
    public Result<Boolean> update(@RequestBody @Validated(Update.class) UserDto user) {
        userService.update(userMapper.dtoToUser(user));
        return Result.ok();
    }

    /**
     * 更新密码
     * @param user
     * @return
     */
    @PutMapping("/password")
    public Result<Boolean> resetPassWord(@RequestBody @Validated(ResetPassword.class) UserDto user) {
        userService.resetPassWord(userMapper.dtoToUser(user));
        return Result.ok();
    }


    /**
     * 按id删除
     * @param user
     * @return
     */
    @DeleteMapping("/user")
    public Result<Boolean> delete(@RequestBody @Validated(Delete.class) UserDto user) {
        userService.delete(userMapper.dtoToUser(user));
        return Result.ok();
    }

    /**
     * 批量删除
     * @param users
     * @return
     */
    @DeleteMapping("/users")
    public Result<Boolean> delete(@RequestBody @Validated(Delete.class) List<UserDto> users) {
        userService.deleteAll(userMapper.dtoToUserList(users));
        return Result.ok();
    }



}
