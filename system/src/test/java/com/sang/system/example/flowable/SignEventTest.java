package com.sang.system.example.flowable;

import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class SignEventTest {


    @Resource
    private RepositoryService repositoryService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @Resource
    private HistoryService historyService;


    // 信号边界事件类似,不在赘述
    /**
     * 通过信号发送来触发信号启动事件的执行
     * 全局的信息
     */
    @Test
    void signalReceived() throws Exception {
        runtimeService.signalEventReceived("start");
        // 我们得保证容器的运行，所以需要阻塞
        TimeUnit.MINUTES.sleep(1);
    }



}
