package com.sang.system.example.flowable;

import org.flowable.engine.*;
import org.flowable.idm.api.Group;
import org.flowable.idm.api.User;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 启动流程实例
     */
    @Test
    public void runProcess(){
        Group group = identityService.createGroupQuery().groupId("group1").singleResult();
        // 给流程定义中的UEL表达式赋值
        Map<String,Object> variables = new HashMap<>();
        variables.put("testGroup1",group.getId()); // 给流程定义中的UEL表达式赋值
        runtimeService.startProcessInstanceByKey("candidate-group-example",variables);
    }

    /**
     * 根据登录的用户查询对应的可以拾取的任务
     *
     */
    @Test
    public void queryTaskCandidateGroup(){
        // 根据当前登录的用户找到对应的组
        // 当前用户所在的组
        Group group = identityService.createGroupQuery().groupMember("hxy").singleResult();

        List<Task> list = taskService.createTaskQuery()
                .processDefinitionId("candidate-group-example:1:69d0ef90-ff8f-11ed-a81c-e2d4e83f9995")
                .taskCandidateGroup(group.getId())
                .list();
        for (Task task : list) {
            System.out.println("task.getId() = " + task.getId());
            System.out.println("task.getName() = " + task.getName());
        }
    }

    /**
     * 拾取任务
     *    一个候选人拾取了这个任务之后其他的用户就没有办法拾取这个任务了
     *    所以如果一个用户拾取了任务之后又不想处理了，那么可以退还
     */
    @Test
    public void claimTaskCandidate(){
        String userId = "hxy";
        // 根据当前登录的用户找到对应的组
        // 当前用户所在的组
        Group group = identityService.createGroupQuery().groupMember(userId).singleResult();
        Task task = taskService.createTaskQuery()
                .processDefinitionId("candidate-group-example:1:69d0ef90-ff8f-11ed-a81c-e2d4e83f9995")
                .taskCandidateGroup(group.getId())
                .singleResult();
        if(task != null) {
            // 任务拾取
            taskService.claim(task.getId(),userId);
            System.out.println("任务拾取成功");
        }
    }
    /**
     * 完成任务
     */
    @Test
    public void completeTask(){
        Task task = taskService.createTaskQuery()
                .processDefinitionId("candidate-group-example:1:69d0ef90-ff8f-11ed-a81c-e2d4e83f9995")
                .taskAssignee("hxy")
                .singleResult();
        if(task != null){
            // 完成任务
            taskService.complete(task.getId());
            System.out.println("完成Task");
        }
    }


}
