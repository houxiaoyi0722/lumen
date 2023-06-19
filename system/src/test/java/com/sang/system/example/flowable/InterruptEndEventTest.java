package com.sang.system.example.flowable;

import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class InterruptEndEventTest {


    @Resource
    private RepositoryService repositoryService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @Resource
    private HistoryService historyService;


    /**
     * 启动流程实例
     *
     */
    @Test
    public void startProcessInstanceByKey()  throws Exception{

        Map<String, Object> variables = new HashMap<>();
        variables.put("condition",false);

        runtimeService
                .startProcessInstanceByKey("Interrupt-end-event",variables);
        System.out.println("开始启动的时间：" + LocalDateTime.now().toString());
    }

    /**
     * 完成任务
     */
    @Test
    public void completeTask(){
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("Interrupt-end-event")
                .taskAssignee("cc")
                .singleResult();

        Map<String, Object> variables = new HashMap<>();
        variables.put("condition",false);

        if(task != null){
            // 完成任务
            taskService.complete(task.getId(),variables);
            System.out.println("完成Task");
        }
    }





}
