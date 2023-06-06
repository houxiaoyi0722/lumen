package com.sang.system.example.flowable;

import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class ErrorEndEventTest {


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
        variables.put("flag",0);

        runtimeService
                .startProcessInstanceByKey("error-end-event",variables);
        System.out.println("开始启动的时间：" + LocalDateTime.now().toString());
    }





}
