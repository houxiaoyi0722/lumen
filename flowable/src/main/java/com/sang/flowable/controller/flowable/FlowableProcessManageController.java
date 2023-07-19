package com.sang.flowable.controller.flowable;

import cn.hutool.core.util.StrUtil;
import com.sang.common.constants.FlowableConst;
import com.sang.common.constants.StringConst;
import com.sang.common.domain.auth.authentication.user.repo.UserGroupRepository;
import com.sang.common.domain.auth.authentication.user.repo.UserRepository;
import com.sang.common.response.Result;
import com.sang.common.utils.ResponseUtil;
import com.sang.flowable.param.SuspendedActiveParam;
import com.sang.flowable.service.flowable.FlowableProcessManageService;
import com.sang.flowable.service.flowable.FlowableService;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.impl.db.SuspensionState;
import org.flowable.engine.IdentityService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.idm.api.Group;
import org.flowable.idm.api.GroupQuery;
import org.flowable.idm.api.User;
import org.flowable.idm.api.UserQuery;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * flowable 流程管理controller
 */
@Slf4j
@RequestMapping("/flowable")
@RestController
public class FlowableProcessManageController {

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private IdentityService identityService;

    @Resource
    private UserRepository userRepository;

    @Resource
    private UserGroupRepository userGroupRepository;

    @Resource
    private FlowableProcessManageService flowableProcessManageService;


    /**
     * 部署流程
     * @param file 文件
     * @param resourceName 资源名称
     * @param name 流程名称
     * @return
     * @throws IOException
     */
    @PutMapping("/deployProcess")
    public Result deployProcess(@RequestParam("file") MultipartFile file
                                ,@RequestParam("resourceName") String resourceName
                                ,@RequestParam("name") String name
                                ) throws IOException {

        if (!StrUtil.endWith(file.getOriginalFilename(), FlowableConst.BPMN_20_XML)) {
             throw new FlowableException("流程定义文件后缀错误");
        }
        Deployment deployment = flowableProcessManageService.getDeployment(file, resourceName, name);

        return Result.ok(deployment.getId());
    }


    /**
     * 挂起或者激活流程
     * @param suspendedActiveParam
     */
    @PutMapping("/suspensionState")
    public Result<String> suspendedOrActiveProcess(@Validated @RequestBody SuspendedActiveParam suspendedActiveParam) {
        flowableProcessManageService.suspendedOrActiveProcess(suspendedActiveParam);
        return Result.ok();
    }



    /**
     * 级联删除流程
     * @param deploymentId
     * @return
     */
    @DeleteMapping("/deleteProcess")
    public Result deleteProcess(@RequestParam(value = "deploymentId") @NotNull(message = "deploymentId不能为空") String deploymentId) {
        flowableProcessManageService.deleteProcess(deploymentId);
        return Result.ok();
    }



    /**
     * 获取流程定义xml文件
     * @param deploymentId 部署id
     * @param resourceName 资源名称
     * @throws IOException
     */
    @GetMapping("/processXmlResource")
    public void processXmlResourceFile(
            @RequestParam(value = "deploymentId",required = false) String deploymentId,
            @RequestParam(value = "resourceName",required = false) String resourceName
    ) throws IOException {
        InputStream resourceAsStream = repositoryService.getResourceAsStream(deploymentId,resourceName);
        ResponseUtil.sendStream(resourceAsStream, StringConst.APPLICATION_XML, FlowableConst.PROCESS_DEFINE_XML);
    }

    /**
     * 用户列表
     * @param name 名字 模糊匹配
     * @return
     */
    @GetMapping("/user/list")
    public Result<List<User>> userList(@RequestParam(value = "name") String name) {

        UserQuery userQuery = identityService.createUserQuery();
        if (StrUtil.isNotBlank(name)) {
            userQuery.userDisplayNameLike(StringConst.PERCENT_SIGN.concat(name).concat(StringConst.PERCENT_SIGN));
        }

        return Result.ok(userQuery.list());
    }

    /**
     * 用户列表
     * @param name 名字 模糊匹配
     * @return
     */
    @GetMapping("/group/list")
    public Result<List<Group>> groupList(@RequestParam(value = "name") String name) {

        GroupQuery groupQuery = identityService.createGroupQuery();
        if (StrUtil.isNotBlank(name)) {
            groupQuery.groupNameLikeIgnoreCase(StringConst.PERCENT_SIGN.concat(name).concat(StringConst.PERCENT_SIGN));
        }

        return Result.ok(groupQuery.list());
    }

    /**
     * 同步系统\flowable用户
     * @return
     */
    @GetMapping("/user/sync")
    public Result syncUsers(@RequestParam(value = "username") String username) {

        if (StrUtil.isNotBlank(username)) {
            flowableProcessManageService.updateUser(userRepository.userinfo(username));
            return Result.ok();
        }

        userGroupRepository.findAll().forEach(userGroup -> {
            Group group = identityService.createGroupQuery().groupId(userGroup.getGroupCode()).singleResult();

            group = group == null ? identityService.newGroup(userGroup.getGroupCode()) : group;
            // 创建Group对象并指定相关的信息
            group.setName(userGroup.getGroupName());
            group.setType(userGroup.getParentId() != null ? userGroup.getParentId().getGroupCode():null);
            // 创建或者更新Group对应的表结构数据
            identityService.saveGroup(group);
        });

        userRepository.findAll().forEach(flowableProcessManageService::updateUser);
        return Result.ok();
    }

}
