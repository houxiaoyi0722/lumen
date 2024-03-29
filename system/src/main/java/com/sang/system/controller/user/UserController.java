package com.sang.system.controller.user;

import com.sang.common.constants.AuthConst;
import com.sang.common.domain.auth.authentication.user.dto.UserDto;
import com.sang.common.domain.auth.authentication.user.dto.UserExtDto;
import com.sang.common.domain.auth.authentication.user.dto.UserInfoDto;
import com.sang.common.domain.auth.authentication.user.entity.User;
import com.sang.common.domain.auth.authentication.user.entity.UserExt;
import com.sang.common.domain.auth.authentication.user.mapper.UserExtMapper;
import com.sang.common.domain.auth.authentication.user.mapper.UserMapper;
import com.sang.common.domain.auth.authentication.user.param.UserQry;
import com.sang.common.domain.auth.authentication.user.vo.UserVo;
import com.sang.common.response.PageResult;
import com.sang.common.response.Result;
import com.sang.common.validate.Create;
import com.sang.common.validate.Delete;
import com.sang.common.validate.Update;
import com.sang.common.validate.user.ResetPassword;
import com.sang.system.service.user.UserExtService;
import com.sang.system.service.user.UserService;
import io.ebean.PagedList;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户管理
 * @author hxy
 * @date 2022/1/7 15:10
 **/
@Validated
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private UserExtService userExtService;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    private final UserMapper userMapper = UserMapper.mapper;
    private final UserExtMapper userExtMapper = UserExtMapper.mapper;

    /**
     * 用户分页查询
     * @param userQry
     * @return
     */
    @PostMapping("/users")
    public PageResult<UserVo> list(@RequestBody UserQry userQry) {
        PagedList<User> userPagedList = userService.userList(userQry);

        List<UserVo> collect = userMapper.userToVoList(userPagedList.getList()).stream()
                .peek(item -> item.setOnline(redisTemplate.hasKey(AuthConst.TOKEN_JWT + item.getUsername())))
                .collect(Collectors.toList());

        return PageResult.ok(collect, userPagedList);
    }

    /**
     * 保存用户
     * @param user
     * @return
     */
    @PostMapping("/user")
    public Result<Boolean> save(@RequestBody @Validated(Create.class) UserDto user) {
        user.setPassword(AuthConst.DEFAULT_PASSWORD);
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
        return Result.ok(userMapper.userToInfoDto(userService.userinfo(authentication.getName())));
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
    @PutMapping("/password/reset")
    public Result<Boolean> resetPassWord(@RequestBody @Validated(ResetPassword.class) UserDto user) {
        user.setPassword(AuthConst.DEFAULT_PASSWORD);
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

    @GetMapping("/exData/{userId}")
    public Result<UserExtDto> getUserExt(@PathVariable("userId") Long userId) {
        return Result.ok(userExtMapper.userExtToDto(userExtService.findByUserId(userId)));
    }

    @PutMapping("/exData")
    public Result<UserExtDto> saveUserExt(@RequestBody UserExtDto userExt) {
        UserExt userext = userExtMapper.dtoToUserExt(userExt);
        if (userExt.getId() != null) {
            userExtService.update(userext);
        } else {
            userExtService.insert(userext);
        }
        return Result.ok();
    }



}
