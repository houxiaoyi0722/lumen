package com.sang.system.controller.user;

import com.sang.common.domain.user.entity.User;
import com.sang.common.response.PageResult;
import com.sang.common.response.Result;
import com.sang.system.domain.user.param.UserParam;
import com.sang.system.service.user.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hxy
 * @date 2022/1/7 15:10
 **/
@RestController
@RequestMapping("/lumen/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/users")
    public PageResult<User> list(@RequestBody UserParam userParam) {
        return PageResult.ok(userService.userList(userParam));
    }


    @PostMapping("/user")
    public Result<Boolean> save(@RequestBody User user) {
        userService.save(user);
        return Result.ok();
    }

    @PutMapping("/user")
    public Result<Boolean> update(@RequestBody User user) {
        userService.update(user);
        return Result.ok();
    }

    @DeleteMapping("/user")
    public Result<Boolean> delete(@RequestBody User user) {
        userService.delete(user);
        return Result.ok();
    }

    @DeleteMapping("/users")
    public Result<Boolean> delete(@RequestBody List<User> users) {
        userService.deleteAll(users);
        return Result.ok();
    }



}
