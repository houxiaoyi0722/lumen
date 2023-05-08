package com.sang.system.controller.user;

import cn.hutool.core.collection.CollUtil;
import com.sang.common.domain.auth.authentication.user.dto.UserGroupDto;
import com.sang.common.domain.auth.authentication.user.entity.UserGroup;
import com.sang.common.domain.auth.authentication.user.mapper.UserGroupMapper;
import com.sang.common.domain.auth.authentication.user.param.UserGroupQry;
import com.sang.common.domain.auth.authentication.user.vo.UserGroupVo;
import com.sang.common.domain.base.dto.CommonKeyValueDto;
import com.sang.common.domain.base.dto.CommonTableDataDto;
import com.sang.common.response.PageResult;
import com.sang.common.response.Result;
import com.sang.common.validate.Create;
import com.sang.common.validate.Delete;
import com.sang.common.validate.Update;
import com.sang.system.service.user.UserGroupService;
import io.ebean.PagedList;
import io.ebean.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户模块用户组
 * @author hxy
 * @date 2022-11-02 17:04:18
 */
@Validated
@RestController
@RequestMapping("/user/group")
public class UserGroupController {

    private final UserGroupMapper userGroupMapper = UserGroupMapper.mapper;

    @Resource
    private UserGroupService userGroupService;

    /**
     * 分页查询
     *
     * @param qry
     * @return
     */
    @PostMapping("/userGroups/qry")
    public PageResult<UserGroupDto> list(@RequestBody UserGroupQry qry) {
        PagedList<UserGroup> pagedList = userGroupService.userGroupList(qry);
        // 查询全部字段时可不转换直接给pagedList
        return PageResult.ok(userGroupMapper.userGroupToDtoList(pagedList.getList()), pagedList);
    }

    /**
     * 查询用户组树
     * @return
     */
    @GetMapping("/userGroupTree")
    public Result<List<UserGroupVo>> userGroupTree() {
        return Result.ok(userGroupMapper.userGroupToVoList(userGroupService.findAll()));
    }

    /**
     * 获取键值对角色树
     * @return
     */
    @GetMapping("/userGroupKVTree")
    public Result<List<CommonKeyValueDto>> rolesKVTree() {
        return Result.ok(userGroupMapper.toKeyValueList(userGroupService.findTop()));
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @GetMapping("/userGroup")
    public Result<UserGroup> findOne(@RequestParam("id") Long id) {
        return Result.ok(userGroupService.findOne(id));
    }

    /**
     * 保存
     *
     * @param userGroup
     * @return
     */
    @PostMapping("/userGroup")
    public Result<Boolean> save(@RequestBody @Validated(Create.class) UserGroupDto userGroup) {
        userGroupService.save(userGroupMapper.dtoToUserGroup(userGroup));
        return Result.ok();
    }

    /**
    * 批量保存
    * @param userGroups
    * @return
    */
    @PostMapping("/userGroups")
    public Result<Boolean> saveAll(@RequestBody @Validated(Create.class) List<UserGroupDto> userGroups) {
        userGroupService.saveAll(userGroupMapper.dtoToUserGroupList(userGroups));
        return Result.ok();
    }

    /**
     * 通过id更新
     *
     * @param userGroup
     * @return
     */
    @PutMapping("/userGroup")
    public Result<Boolean> update(@RequestBody @Validated(Update.class) UserGroupDto userGroup) {
        userGroupService.update(userGroupMapper.dtoToUserGroup(userGroup));
        return Result.ok();
    }

    /**
     * 通过id删除
     *
     * @param userGroup
     * @return
     */
    @DeleteMapping("/userGroup")
    public Result<Boolean> delete(@RequestBody @Validated(Delete.class) UserGroupDto userGroup) {
        userGroupService.delete(userGroupMapper.dtoToUserGroup(userGroup));
        return Result.ok();
    }

    /**
     * 列表批量删除
     *
     * @param userGroups
     * @return
     */
    @DeleteMapping("/userGroups")
    public Result<Boolean> delete(@RequestBody @Validated(Delete.class) List<UserGroupDto> userGroups) {
        userGroupService.deleteAll(userGroupMapper.dtoToUserGroupList(userGroups));
        return Result.ok();
    }

    /**
     * 批量保存/更新/删除数据 配合 vxe table
     * @param tableDataDto
     * @return
     */
    @Transactional
    @PostMapping("/userGroupListUpdate")
    public Result<Boolean> userGroupListUpdate(@RequestBody @Validated CommonTableDataDto<UserGroupDto> tableDataDto) {
        if (CollUtil.isNotEmpty(tableDataDto.getInsertList())) {
            userGroupService.saveAll(userGroupMapper.dtoToUserGroupList(tableDataDto.getInsertList()));
        }
        if (CollUtil.isNotEmpty(tableDataDto.getUpdateList())) {
            userGroupService.updateAll(userGroupMapper.dtoToUserGroupList(tableDataDto.getUpdateList()));
        }
        if (CollUtil.isNotEmpty(tableDataDto.getRemoveList())) {
            userGroupService.deleteAll(userGroupMapper.dtoToUserGroupList(tableDataDto.getRemoveList()));
        }

        return Result.ok();
    }
}
