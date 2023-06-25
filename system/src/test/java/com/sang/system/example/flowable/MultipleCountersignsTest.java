package com.sang.system.example.flowable;

import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//@SpringBootTest
public class MultipleCountersignsTest {


    @Resource
    private RepositoryService repositoryService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @Resource
    private HistoryService historyService;

    //@Test
    public void startFlow() throws Exception{
        Map<String,Object> map = new HashMap<>();
        // 设置多人会签的数据
        map.put("persons", Arrays.asList("张三","李四","王五","赵六"));
        ProcessInstance processInstance = runtimeService
                .startProcessInstanceByKey("multiple-countersigns",map);
    }

    //@Test
    void completeTask1(){
        Map<String,Object> map = new HashMap<>();
//        map.put("flag",false);
        map.put("flag",true);
//        taskService.complete("884fd3c6-040e-11ee-bcab-e2d4e83f9995",map);
        taskService.complete("88592299-040e-11ee-bcab-e2d4e83f9995",map);
//        taskService.complete("884e7430-040e-11ee-bcab-e2d4e83f9995",map);
        System.out.println("complete ....");
    }

}
