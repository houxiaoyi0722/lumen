package com.sang.system.example.flowable;

import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class CancleEventTest {


    @Resource
    private RepositoryService repositoryService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @Resource
    private HistoryService historyService;

    /**
     * 启动流程实例,设置流程变量中的flag=0触发取消结束事件
     */
    @Test
    public void startProcessInstanceByKey()  throws Exception{
        // 设置对应的流程变量的值
        Map<String,Object> map = new HashMap<>();
        map.put("flag",0);// 设置flag为0触发流程结束事件
        runtimeService
                .startProcessInstanceByKey("cancle-event-example",map);
    }

    /**
     * 完成任务
     */
    @Test
    public void completeTask(){
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("cancle-event-example")
                .taskAssignee("zhangsan")
                .singleResult();
        taskService.complete(task.getId());
    }

}
