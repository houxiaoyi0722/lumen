package com.sang.system.example.flowable;

import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class ErrorEventTest {


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

        runtimeService
                .startProcessInstanceByKey("error-event-example");
        System.out.println("开始启动的时间：" + LocalDateTime.now().toString());
        // 需要在此阻塞比等待长的时间
        TimeUnit.MINUTES.sleep(3);
    }




}
