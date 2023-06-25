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
import java.util.Map;

//@SpringBootTest
public class CompensateEventTest {


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
     */
    //@Test
    public void startProcessInstanceByKey()  throws Exception{
        runtimeService
                .startProcessInstanceByKey("compensate-event");
    }

}
