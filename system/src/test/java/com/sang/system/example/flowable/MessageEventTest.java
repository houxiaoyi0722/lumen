package com.sang.system.example.flowable;

import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

//@SpringBootTest
public class MessageEventTest {


    @Resource
    private RepositoryService repositoryService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @Resource
    private HistoryService historyService;


    /**
     * 通过消息来启动一个流程实例
     */
    //@Test
    void startFlow() throws Exception{

        // runtimeService.startProcessInstanceById("event006:1:0532e730-af02-11ec-8cf3-c03c59ad2248");
        // 注意：发送消息发送的应该是消息的名称而不是消息的ID
        ProcessInstance test1 = runtimeService.startProcessInstanceByMessage("test1");
        System.out.println("启动时间：" + new Date());
        System.out.println("id：" + test1.getId());
        // 我们得保证容器的运行，所以需要阻塞
//        TimeUnit.MINUTES.sleep(1);
    }

    /**
     * 中间事件-发布消息
     */
    //@Test
    void recevedMsg(){
        // 需要查询到executionId
        // 我们需要根据流程实例编号找到对应的执行编号
        List<Execution> list = runtimeService.createExecutionQuery()
//                .messageEventSubscriptionName("test12")
                .messageEventSubscriptionName("test3")
                .list();

        for (Execution execution : list) {
            System.out.println("----------->"+execution.getId());
//            runtimeService.messageEventReceived("test12",execution.getId());
            runtimeService.messageEventReceived("test3",execution.getId());
        }

    }



}
