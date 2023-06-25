package com.sang.system.example.flowable;

import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@SpringBootTest
public class MyHolidayFlowableTest {


    @Resource
    private RepositoryService repositoryService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @Resource
    private HistoryService historyService;

    /**
     * 查看流程定义
     */
    //@Test
    public void testDeployQuery(){

//        ProcessDefinition oneTaskProcess = repositoryService.createProcessDefinitionQuery().processDefinitionKey("oneTaskProcess").singleResult();
//
//        System.out.println("1: " + oneTaskProcess.getId());
//        System.out.println("2: " + oneTaskProcess.getName());
//        System.out.println("3: " + oneTaskProcess.getDeploymentId());
//        System.out.println("4: " + oneTaskProcess.getDescription());

        // 获取流程定义对象
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("my-holiday-test")
                .singleResult();
        System.out.println("processDefinition.getId() = " + processDefinition.getId()); // my-holiday-test:1:8d2005fe-fec2-11ed-bd4d-e2d4e83f9995
        System.out.println("processDefinition.getName() = " + processDefinition.getName()); // my-holiday-test
        System.out.println("processDefinition.getDeploymentId() = " + processDefinition.getDeploymentId()); // 8c6fa1b9-fec2-11ed-bd4d-e2d4e83f9995
        System.out.println("processDefinition.getDescription() = " + processDefinition.getDescription());

    }

    /**
     * 启动流程实例
     * 这里启动流程实例到第一个节点
     */
    //@Test
    public void testRunProcess(){

        // 启动流程实例通过 RuntimeService 对象
        // 构建流程变量
        Map<String,Object> variables = new HashMap<>();
        variables.put("systemAttribute","111") ;// 谁申请请假
        variables.put("systemAttribute2",222); // 请几天假
        variables.put("myProperty","zhangsan"); // 任务分配人，这里设置的会被监听器设置的覆盖
        variables.put("myProperty2","lisi"); // 任务分配人
        // 启动流程实例，第一个参数是流程定义的id
        ProcessInstance processInstance = runtimeService
                .startProcessInstanceByKey("my-holiday-test", variables);// 启动流程实例

        // 输出相关的流程实例信息
        System.out.println("流程定义的ID：" + processInstance.getProcessDefinitionId());
        System.out.println("流程实例的ID：" + processInstance.getId());
        System.out.println("当前活动的ID：" + processInstance.getActivityId());
    }


    /**
     * 修改，添加task流程变量
     */
    //@Test
    public void testTaskVariable(){

        List<Task> list = taskService.createTaskQuery()
                .processDefinitionKey("my-holiday-test")
                .taskAssignee("lisi") // 流程走到李四后查询李四的任务
                .list();

        for (Task task : list) {
            taskService.setVariable(task.getId(),"userForm1","aaaa"); // 覆盖全局变量
            taskService.setVariableLocal(task.getId(),"userForm1","bbbb"); // 本地变量名称可以重复
        }
    }

    /**
     * 查看任务
     */
    //@Test
    public void testQueryTask(){

        List<Task> list = taskService.createTaskQuery()
                .processDefinitionKey("my-holiday-test")
//                .taskAssignee("小张")
                .taskAssignee("lisi") // 流程走到李四后查询李四的任务
                .list();

        Map<String, Object> variables = runtimeService.getVariables("40608b53-fec3-11ed-837f-e2d4e83f9995");
        System.out.println("variables = " + variables);

        for (Task task : list) {
            System.out.println("task.getProcessDefinitionId() = " + task.getProcessDefinitionId());
            System.out.println("task.getId() = " + task.getId());
            System.out.println("task.getAssignee() = " + task.getAssignee());
            System.out.println("task.getName() = " + task.getName());
            System.out.println("task.getVariables() = " + taskService.getVariables(task.getId()));
            System.out.println("task.getVariablesLocal() = " + taskService.getVariablesLocal(task.getId()));
        }
    }


    /**
     * 完成任务
     */
    //@Test
    public void testCompleteTask(){
        // 获取流程引擎对象
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("my-holiday-test")
//                .taskAssignee("小张")
                .taskAssignee("lisi")
                .singleResult();


        Map<String,Object> variables = new HashMap<>();
        variables.put("userForm1","333");
        variables.put("userForm2",444);

        // 完成任务
        taskService.complete(task.getId(),variables);
    }

}
