package com.sang.system.example.flowable;

import org.flowable.engine.*;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@SpringBootTest
public class CandidateTest {


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
    public void runProcess(){
        // 给流程定义中的UEL表达式赋值
        Map<String,Object> variables = new HashMap<>();
        variables.put("candidate1","张三");
        variables.put("candidate2","李四");
        variables.put("candidate3","王五");
        runtimeService.startProcessInstanceByKey("candidate-example",variables);
    }

    /**
     * 根据登录的用户查询对应的可以拾取的任务
     *
     */
    //@Test
    public void queryTaskCandidate(){
        List<Task> list = taskService.createTaskQuery()
                .processDefinitionId("candidate-example:1:67e0033f-fed0-11ed-b4f4-e2d4e83f9995")
                .taskCandidateUser("李四") // 注意
                .list();
        for (Task task : list) {
            System.out.println("task.getId() = " + task.getId());
            System.out.println("task.getName() = " + task.getName());
        }
    }

    /**
     * 拾取任务
     *    一个候选人拾取了这个任务之后其他的用户就没有办法拾取这个任务了
     *    所以如果一个用户拾取了任务之后又不想处理了，那么可以退还
     */
    //@Test
    public void claimTaskCandidate(){
        Task task = taskService.createTaskQuery()
                .processDefinitionId("candidate-example:1:67e0033f-fed0-11ed-b4f4-e2d4e83f9995")
                .taskCandidateUser("李四")
                .singleResult();
        if(task != null){
            // 拾取对应的任务
            taskService.claim(task.getId(),"李四");
            System.out.println("任务拾取成功");
        }
    }

    /**
     * 退还任务
     *    一个候选人拾取了这个任务之后其他的用户就没有办法拾取这个任务了
     *    所以如果一个用户拾取了任务之后又不想处理了，那么可以退还
     */
    //@Test
    public void unclaimTaskCandidate(){
        Task task = taskService.createTaskQuery()
                .processDefinitionId("candidate-example:1:67e0033f-fed0-11ed-b4f4-e2d4e83f9995")
                .taskAssignee("李四")
                .singleResult();
        if(task != null){
            // 拾取对应的任务
            taskService.unclaim(task.getId());
            System.out.println("归还拾取成功");
        }
    }

    /**
     * 任务的交接
     *    如果我获取了任务，但是不想执行，那么我可以把这个任务交接给其他的用户
     *    未获取任务的情况下也可以直接交接
     */
    //@Test
    public void taskCandidate(){
        Task task = taskService.createTaskQuery()
                .processDefinitionId("candidate-example:1:67e0033f-fed0-11ed-b4f4-e2d4e83f9995")
                .taskCandidateUser("李四")
                .singleResult();
        if(task != null){
            // 任务的交接
            taskService.setAssignee(task.getId(),"王五");
            System.out.println("任务交接给了王五");
        }
    }

    /**
     * 完成任务
     */
    //@Test
    public void completeTask(){
        Task task = taskService.createTaskQuery()
                //.processInstanceId("2501")
                .processDefinitionId("candidate-example:1:67e0033f-fed0-11ed-b4f4-e2d4e83f9995")
                .taskAssignee("王五")
                .singleResult();
        if(task != null){
            // 完成任务
            taskService.complete(task.getId());
            System.out.println("完成Task");
        }
    }

}
