package com.sang.system.example.flowable;

import org.apache.commons.io.FileUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.impl.DefaultProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class FlowableTest {


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
    @Test
    public void testDeploy(){
        // 配置数据库相关信息 获取 ProcessEngineConfiguration
//        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
//                .setJdbcUrl("jdbc:mysql://localhost:3306/flowable-learn2?serverTimezone=UTC&nullCatalogMeansCurrent=true")
//                .setJdbcUsername("root")
//                .setJdbcPassword("123456")
//                .setJdbcDriver("com.mysql.cj.jdbc.Driver")
//                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
//         获取流程引擎对象
//        ProcessEngine processEngine = cfg.buildProcessEngine();
//         部署流程 获取RepositoryService对象
//        RepositoryService repositoryService = processEngine.getRepositoryService();
//        Deployment deployment = repositoryService.createDeployment()// 创建Deployment对象
//                .addClasspathResource("holiday-request.bpmn20.xml") // 添加流程部署文件
//                .name("请求流程") // 设置部署流程的名称
//                .deploy(); // 执行部署操作
//        System.out.println("deployment.getId() = " + deployment.getId());
//        System.out.println("deployment.getName() = " + deployment.getName());

    }

    /**
     * 查看流程定义
     */
    @Test
    public void testDeployQuery(){

//        ProcessDefinition oneTaskProcess = repositoryService.createProcessDefinitionQuery().processDefinitionKey("oneTaskProcess").singleResult();
//
//        System.out.println("1: " + oneTaskProcess.getId());
//        System.out.println("2: " + oneTaskProcess.getName());
//        System.out.println("3: " + oneTaskProcess.getDeploymentId());
//        System.out.println("4: " + oneTaskProcess.getDescription());

        // 获取流程定义对象
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("holidayRequest")
                .singleResult();
        System.out.println("processDefinition.getId() = " + processDefinition.getId());
        System.out.println("processDefinition.getName() = " + processDefinition.getName());
        System.out.println("processDefinition.getDeploymentId() = " + processDefinition.getDeploymentId());
        System.out.println("processDefinition.getDescription() = " + processDefinition.getDescription());

    }

    /**
     * 启动流程实例
     */
    @Test
    public void testRunProcess(){

        // 启动流程实例通过 RuntimeService 对象
        // 构建流程变量
        Map<String,Object> variables = new HashMap<>();
        variables.put("employee","张三") ;// 谁申请请假
        variables.put("nrOfHolidays",3); // 请几天假
        variables.put("description","工作累了，想出去玩玩"); // 请假的原因
        // 启动流程实例，第一个参数是流程定义的id
        ProcessInstance processInstance = runtimeService
//                .startProcessInstanceById("holidayRequest:1:6fb660c4-fb72-11ed-8dfa-e2d4e83f9995",variables);
                .startProcessInstanceByKey("holidayRequest", variables);// 启动流程实例
        // 输出相关的流程实例信息
        System.out.println("流程定义的ID：" + processInstance.getProcessDefinitionId());
        System.out.println("流程实例的ID：" + processInstance.getId());
        System.out.println("当前活动的ID：" + processInstance.getActivityId());
    }


    /**
     * 查看任务
     */
    @Test
    public void testQueryTask(){

        List<Task> list = taskService.createTaskQuery()
                .processDefinitionKey("holidayRequest")
                .taskAssignee("lisi")
                .list();
        for (Task task : list) {
            System.out.println("task.getProcessDefinitionId() = " + task.getProcessDefinitionId());
            System.out.println("task.getId() = " + task.getId());
            System.out.println("task.getAssignee() = " + task.getAssignee());
            System.out.println("task.getName() = " + task.getName());
        }
    }


    /**
     * 完成任务
     */
    @Test
    public void testCompleteTask(){
        // 获取流程引擎对象
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("holidayRequest")
                .taskAssignee("lisi")
                .singleResult();
        // 添加流程变量
        Map<String,Object> variables = new HashMap<>();
        variables.put("approved",false); // 拒绝请假
        // 完成任务
        taskService.complete(task.getId(),variables);
    }


    /**
     * 删除流程
     */
    @Test
    public void testDeleteProcess(){

        // 删除流程定义，如果该流程定义已经有了流程实例启动则删除时报错
        // repositoryService.deleteDeployment("1");
        // 设置为TRUE 级联删除流程定义，及时流程有实例启动，也可以删除，设置为false 非级联删除操作。
        repositoryService.deleteDeployment("8d6f973d-fb96-11ed-84a6-e2d4e83f9995",true);
        repositoryService.deleteDeployment("e1d875c6-00ee-11ee-b5e5-e2d4e83f9995",true);

    }

    /**
     * 查看历史
     */
    @Test
    public void testQueryHistory(){
        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery()
                .processDefinitionId("holidayRequest:3:6ec25516-fb9d-11ed-a184-e2d4e83f9995")
                .finished()
                .orderByHistoricActivityInstanceEndTime().asc()
                .list();
        for (HistoricActivityInstance historicActivityInstance : list) {
            System.out.println(historicActivityInstance.getActivityId() + " took "
                    + historicActivityInstance.getDurationInMillis() + " milliseconds");
        }

    }

    /**
     * 挂起流程
     * 流程定义为挂起状态，该流程定义将不允许启动新的流程实例，同时该流程定义下的所有的流程实例都将全部挂起暂停执行。
     *
     */
    @Test
    public void test05(){
        // 获取流程引擎对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId("holidayRequest:3:6ec25516-fb9d-11ed-a184-e2d4e83f9995")
                .singleResult();
        // 获取流程定义的状态
        boolean suspended = processDefinition.isSuspended();
        System.out.println("suspended = " + suspended);
        if(suspended){
            // 表示被挂起
            System.out.println("激活流程定义");
            repositoryService.activateProcessDefinitionById("holidayRequest:3:6ec25516-fb9d-11ed-a184-e2d4e83f9995",true,null);
        }else{
            // 表示激活状态
            System.out.println("挂起流程");
            repositoryService.suspendProcessDefinitionById("holidayRequest:3:6ec25516-fb9d-11ed-a184-e2d4e83f9995",true,null);
        }
    }
    // 挂起/激活流程实例
    @Test
    public void test06() {
        // 3.获取流程实例对象
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId("f5e33867-fb8e-11ed-a11a-e2d4e83f9995")
                .singleResult();

        // 4.获取相关的状态操作
        boolean suspended = processInstance.isSuspended();
        String id = processInstance.getId();
        if (suspended) {
            // 挂起--》激活
            runtimeService.activateProcessInstanceById(id);
            System.out.println("流程定义：" + id + "，已激活");
        } else {
            // 激活--》挂起
            runtimeService.suspendProcessInstanceById(id);
            System.out.println("流程定义：" + id + "，已挂起");
        }

    }

    @Test
    public void buildProcessImage() throws IOException {
        ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().processDefinitionKey("TimeEvent-Example").latestVersion().singleResult();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(pd.getId());
        DefaultProcessDiagramGenerator generator = new DefaultProcessDiagramGenerator();
        InputStream inputStream = generator.generatePngDiagram(bpmnModel, 1.0, true);
        FileUtils.copyInputStreamToFile(inputStream, new File("D:/1.png"));
    }



}
