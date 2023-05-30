package com.sang.system.example.flowable;

import org.flowable.engine.*;
import org.flowable.idm.api.Group;
import org.flowable.idm.api.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class FlowableUserGroupTest {

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @Resource
    private HistoryService historyService;

    @Resource
    private IdentityService identityService;


    /**
     * 维护用户
     */
    @Test
    public void createUser(){
        // 通过 IdentityService 完成相关的用户和组的管理
        User user = identityService.newUser("hxy");
        user.setFirstName("hou");
        user.setLastName("xiaoyi");
        user.setEmail("houxiaoyi@qq.com");
        identityService.saveUser(user);
    }

    /**
     * 创建用户组
     */
    @Test
    public void createGroup(){
        // 创建Group对象并指定相关的信息
        Group group = identityService.newGroup("group1");
        group.setName("开发部");
        group.setType("type1");
        // 创建Group对应的表结构数据
        identityService.saveGroup(group);

    }

    /**
     * 将用户分配给对应的Group
     */
    @Test
    public void userGroup(){
        // 根据组的编号找到对应的Group对象
        Group group = identityService.createGroupQuery().groupId("group1").singleResult();
        List<User> list = identityService.createUserQuery().list();
        for (User user : list) {
            // 将用户分配给对应的组
            identityService.createMembership(user.getId(),group.getId());
        }
    }



}
