package com.sang.flowable.service.flowable.impl;

import com.sang.flowable.param.SuspendedActiveParam;
import com.sang.flowable.service.flowable.FlowableProcessManageService;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.impl.db.SuspensionState;
import org.flowable.engine.IdentityService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.idm.api.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@Slf4j
@Service
public class FlowableProcessManageServiceImpl implements FlowableProcessManageService {

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private IdentityService identityService;

    @CacheEvict(value = "processExtend",key = "'*'")
    @Override
    public Deployment getDeployment(MultipartFile file, String resourceName, String name) throws IOException {
        // 部署流程 获取RepositoryService对象
        Deployment deployment = repositoryService.createDeployment()// 创建Deployment对象
                .addInputStream(resourceName, file.getInputStream())// 添加流程部署文件
                .name(name) // 设置部署流程的名称
                .deploy(); // 执行部署操作
        return deployment;
    }

    @CacheEvict(value = "processExtend",key = "'*'")
    @Override
    public void suspendedOrActiveProcess(SuspendedActiveParam suspendedActiveParam) {
        // 表示被挂起
        if(SuspensionState.SUSPENDED.getStateCode() == suspendedActiveParam.getSuspensionState()) {
            // 激活流程定义
            repositoryService.activateProcessDefinitionById(suspendedActiveParam.getProcessDefinitionId(),true,null);
            // 表示激活状态
        }else if (SuspensionState.ACTIVE.getStateCode() == suspendedActiveParam.getSuspensionState()){
            // 挂起流程
            repositoryService.suspendProcessDefinitionById(suspendedActiveParam.getProcessDefinitionId(),true,null);
        }
    }

    @CacheEvict(value = "processExtend",key = "'*'")
    @Override
    public void deleteProcess(String deploymentId) {
        repositoryService.deleteDeployment(deploymentId,true);
    }

    @Override
    public void updateUser(com.sang.common.domain.auth.authentication.user.entity.User userinfo) {
        // 通过 IdentityService 完成相关的用户和组的管理
        User user = identityService.createUserQuery().userId(userinfo.getUsername()).singleResult();
        user = user == null ? identityService.newUser(userinfo.getUsername()) : user;
        user.setId(userinfo.getUsername());
        user.setDisplayName(userinfo.getName());
        identityService.saveUser(user);

        // 将用户分配给对应的组
        if (userinfo.getUserGroup() != null) {
            identityService.deleteMembership(user.getId(),userinfo.getUserGroup().getGroupCode());
            identityService.createMembership(user.getId(),userinfo.getUserGroup().getGroupCode());
        }
    }




}
