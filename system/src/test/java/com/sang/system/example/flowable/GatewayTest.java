package com.sang.system.example.flowable;

import org.flowable.engine.*;
import org.flowable.engine.repository.Deployment;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

//@SpringBootTest
public class GatewayTest {


    @Resource
    private RepositoryService repositoryService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @Resource
    private HistoryService historyService;

    /**
     * 部署流程
     */
//    //@Test
//    public void deploy(){
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//        RepositoryService repositoryService = processEngine.getRepositoryService();
//
//        Deployment deploy = repositoryService.createDeployment()
//                .addClasspathResource("请假流程-排他网关.bpmn20.xml")
//                .name("请求流程-排他网关")
//                .deploy();
//        System.out.println("deploy.getId() = " + deploy.getId());
//        System.out.println(deploy.getName());
//    }

    /**
     * 排他网关
     * 启动流程实例
     */
    //@Test
    public void runProcess(){
        // 给流程定义中的UEL表达式赋值
        Map<String,Object> variables = new HashMap<>();
        variables.put("aParam",3); // 给流程定义中的UEL表达式赋值
        runtimeService.startProcessInstanceByKey("exclusive-gateway-example",variables);
    }


    /**
     * 排他网关
     * 当排他网关没有符合条件的流程时,会回退到上一个节点
     * 启动流程实例
     */
    //@Test
    public void setVariables(){
        // 给流程定义中的UEL表达式赋值
        Map<String,Object> variables = new HashMap<>();
        variables.put("aParam",1); // 给流程定义中的UEL表达式赋值
        runtimeService.setVariables("29d068c9-00ec-11ee-9172-e2d4e83f9995",variables);
    }



    /**
     * 排他网关
     * 完成任务
     */
    //@Test
    public void completeTask(){
        Task task = taskService.createTaskQuery()
                .processDefinitionId("exclusive-gateway-example:1:25e94387-00ec-11ee-9172-e2d4e83f9995")
                .taskAssignee("aaa")
                .singleResult();
        if(task != null){
            // 完成任务
            taskService.complete(task.getId());
            System.out.println("完成Task");
        }
    }





//    -------------------------------------------------------------


    /**
     * 并行网管
     * 启动流程实例
     */
    //@Test
    public void runProcessParallel(){
        // 给流程定义中的UEL表达式赋值
        Map<String,Object> variables = new HashMap<>();
        runtimeService.startProcessInstanceByKey("parallel-gateway-example",variables);
    }


    /**
     * 并行网管
     * 完成任务
     */
    //@Test
    public void completeTaskParallel(){
        Task task = taskService.createTaskQuery()
                .processDefinitionId("parallel-gateway-example:1:e2d99224-00ee-11ee-b5e5-e2d4e83f9995")
//                .taskAssignee("aa")
//                .taskAssignee("bb")
//                .taskAssignee("cc")
//                .taskAssignee("dd")
                .taskAssignee("ee")
                .singleResult();
        if(task != null){
            // 完成任务
            taskService.complete(task.getId());
            System.out.println("完成Task");
        }
    }


//    ----------------------------------------------------------



    /**
     * 包容网关
     * 启动流程实例
     */
    //@Test
    public void runProcessInclude(){
        // 给流程定义中的UEL表达式赋值
        Map<String,Object> variables = new HashMap<>();
        variables.put("param",3);
        runtimeService.startProcessInstanceByKey("Include-gateway-example",variables);
    }


    /**
     * 包容网关
     * 完成任务
     */
    //@Test
    public void completeTaskInclude(){
        Task task = taskService.createTaskQuery()
                .processDefinitionId("Include-gateway-example:2:9eb1ec07-00f2-11ee-a1cc-e2d4e83f9995")
//                .taskAssignee("aa")
//                .taskAssignee("bb")
//                .taskAssignee("cc")
//                .taskAssignee("dd")
                .taskAssignee("ee")
                .singleResult();
        if(task != null){
            // 完成任务
            taskService.complete(task.getId());
            System.out.println("完成Task");
        }
    }



}
