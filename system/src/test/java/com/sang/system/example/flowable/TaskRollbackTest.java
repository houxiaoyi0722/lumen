package com.sang.system.example.flowable;

import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TaskRollbackTest {


    @Resource
    private RepositoryService repositoryService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @Resource
    private HistoryService historyService;


    /**
     * 串行回退
     * 启动流程实例
     */
    @Test
    void startProcess(){
        ProcessInstance processInstance = runtimeService
                .startProcessInstanceByKey("TaskRollback-One");
        System.out.println("processInstance.getId() = " + processInstance.getId());
    }

    /**
     * 完成任务
     */
    @Test
    void completeTask(){
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("TaskRollback-One")
//                .taskAssignee("aa")
//                .taskAssignee("bb")
                .taskAssignee("cc")
                .singleResult();
        taskService.complete(task.getId());
    }


    /**
     * 回退操作
     */
    @Test
    void rollbackTask(){
        // 当前的Task对应的用户任务的Id
        List<String> currentActivityIds = new ArrayList<>();
        currentActivityIds.add("sid-63E59A34-A4E6-40A7-B43F-838A74A80CBB");
        // 需要回退的目标节点的用户任务Id
//        String newActivityId = "sid-63E59A34-A4E6-40A7-B43F-838A74A80CBB";
        String newActivityId = "sid-3B449232-67E0-4EC1-A4F9-59D5A4041EAC";
        // 回退操作
        runtimeService.createChangeActivityStateBuilder()
                .processInstanceId("f13e36aa-0438-11ee-adb5-e2d4e83f9995")
                .moveActivityIdsToSingleActivityId(currentActivityIds,newActivityId)
                .changeState();
    }

// --------------------------------------------------------------------------------------

    /**
     * 并行回退
     * 启动流程实例
     */
    @Test
    void startProcessTwo(){
        ProcessInstance processInstance = runtimeService
                .startProcessInstanceByKey("TaskRollback-Two");
        System.out.println("processInstance.getId() = " + processInstance.getId());
    }

    /**
     * 完成任务
     */
    @Test
    void completeTaskTwo(){
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("TaskRollback-Two")
//                .taskAssignee("aa")
//                .taskAssignee("bb")
                .taskAssignee("cc")
                .singleResult();
        taskService.complete(task.getId());
    }

    /**
     * 回退操作
     *   业务副总驳回到到用户审批处  那么行政审批的也应该要返回
     */
    @Test
    void rollbackTaskTwo(){
        // 当前的Task对应的用户任务的Id
        List<String> currentActivityIds = new ArrayList<>();
        currentActivityIds.add("sid-1EC08A1C-176C-44CA-945C-0F5E2A25930B"); // 行政副总
        currentActivityIds.add("sid-15293965-A54D-4FAA-AF9C-037AC7C50A25"); // 业务副总
        // 需要回退的目标节点的用户任务Id
        String newActivityId = "sid-DE3FB30B-D40A-4A8B-A2FB-A791249F57F5"; // 用户审批01
        // 回退操作
        runtimeService.createChangeActivityStateBuilder()
                .processInstanceId("9d9a4977-0446-11ee-951c-e2d4e83f9995")
                .moveActivityIdsToSingleActivityId(currentActivityIds,newActivityId)
                .changeState();
    }

    @Test
    void rollbackTaskTwo2(){
        // 当前的Task对应的用户任务的Id
        List<String> currentActivityIds = new ArrayList<>();
        currentActivityIds.add("sid-DA6095D0-89FA-468C-B4D8-982CFEC7CF4F"); // 行政副总
        // 需要回退的目标节点的用户任务Id
        String newActivityId = "sid-DE3FB30B-D40A-4A8B-A2FB-A791249F57F5"; // 用户审批01
        // 回退操作
        runtimeService.createChangeActivityStateBuilder()
                .processInstanceId("9d9a4977-0446-11ee-951c-e2d4e83f9995")
                .moveActivityIdsToSingleActivityId(currentActivityIds,newActivityId)
                .changeState();
    }

    @Test
    void rollbackTaskTwo3(){
        // 当前的Task对应的用户任务的Id
        List<String> currentActivityIds = new ArrayList<>();
        currentActivityIds.add("sid-1EC08A1C-176C-44CA-945C-0F5E2A25930B"); // dd
        // 需要回退的目标节点的用户任务Id
        String newActivityId = "sid-DA6095D0-89FA-468C-B4D8-982CFEC7CF4F"; // cc
        // 回退操作
        runtimeService.createChangeActivityStateBuilder()
                .processInstanceId("9d9a4977-0446-11ee-951c-e2d4e83f9995")
                .moveActivityIdsToSingleActivityId(currentActivityIds,newActivityId)
                .changeState();
    }

    // --------------------------------------------------------------------------------------------


    /**
     * 子流程回退
     * 启动流程实例
     */
    @Test
    void startProcessThree(){
        ProcessInstance processInstance = runtimeService
                .startProcessInstanceByKey("TaskRollback-Three");
        System.out.println("processInstance.getId() = " + processInstance.getId());
    }

    /**
     * 完成任务
     */
    @Test
    void completeTaskThree(){
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("TaskRollback-Three")
//                .taskAssignee("user1")
                .taskAssignee("user2")
//                .taskAssignee("user3")
                .singleResult();
        taskService.complete(task.getId());
    }

    /**
     * 回退操作
     *   从子流程回退到主流程操作
     */
    @Test
    void rollbackMainTask(){

        // 回退操作
        runtimeService.createChangeActivityStateBuilder()
                .processInstanceId("fd85ac00-044e-11ee-a56b-e2d4e83f9995")
                .moveActivityIdTo("usertask2","usertask1")
                .changeState();
    }

    /**
     * 回退操作
     *   从子流程回退到主流程操作：moveExecutionToActivityId不关心当前的节点
     */
    @Test
    void rollbackMainTask1(){

        // 回退操作
        runtimeService.createChangeActivityStateBuilder()
                .processInstanceId("fd85ac00-044e-11ee-a56b-e2d4e83f9995")
                .moveExecutionToActivityId("5f034b12-0450-11ee-b509-e2d4e83f9995","usertask1")
                .changeState();
    }

    /**
     * 回退操作
     *   从主流程回退到子流程操作
     */
    @Test
    void rollbackMainTask2(){

        // 回退操作
        runtimeService.createChangeActivityStateBuilder()
                .processInstanceId("fd85ac00-044e-11ee-a56b-e2d4e83f9995")
                .moveActivityIdTo("usertask3","usertask2")
                .changeState();
    }







}
