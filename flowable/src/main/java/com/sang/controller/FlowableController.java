package com.sang.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.sang.common.response.PageResult;
import com.sang.common.response.Result;
import com.sang.dto.ProcessDefinitionDto;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricProcessInstanceQuery;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.flowable.idm.api.Group;
import org.flowable.idm.api.User;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.api.history.HistoricTaskInstanceQuery;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/flowable")
@RestController
public class FlowableController {

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private IdentityService identityService;

    @Resource
    private TaskService taskService;

    @Resource
    private HistoryService historyService;

    /**
     * 分页查询当前流程定义
     * @param name 模糊查询流程名称
     * @param startBy 当前发起人id
     * @param page
     * @param size
     * @return PageResult
     */
    @GetMapping("/process/page")
    public PageResult<ProcessDefinitionDto> processDefinitionPageList(
            @RequestParam(value = "name",required = false) String name,
            @RequestParam(value = "startBy",required = false) String startBy,
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "size",defaultValue = "10") Integer size) {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();

        if (StrUtil.isNotBlank(name))
            processDefinitionQuery.processDefinitionNameLike("%".concat(name).concat("%"));

        if (StrUtil.isNotBlank(startBy)) {
            List<Group> list = identityService.createGroupQuery().groupMember(startBy).list();
            processDefinitionQuery.startableByUserOrGroups(startBy,list.stream().map(Group::getId).collect(Collectors.toList()));
        }

        List<ProcessDefinitionDto> processDefinitions = processDefinitionQuery
                .active()
                .latestVersion()
                .listPage(page-1, size).stream()
                .map(item -> BeanUtil.copyProperties(item, ProcessDefinitionDto.class))
                .collect(Collectors.toList());

        long count = processDefinitionQuery.count();

        return PageResult.ok(processDefinitions,page,size,(int) count);
    }

    /**
     * 我的待办
     * @param userId 用户id
     * @param processDefineId 流程定义id
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/task/todo/page")
    public PageResult<Task> todoTaskByUser(@RequestParam(value = "userId") String userId,
                                     @RequestParam(value = "processDefineId",required = false) String processDefineId,
                                     @RequestParam(value = "page",defaultValue = "1") Integer page,
                                     @RequestParam(value = "size",defaultValue = "10") Integer size) {
        TaskQuery taskQuery = taskService.createTaskQuery();

        if (StrUtil.isNotBlank(processDefineId))
            taskQuery.processDefinitionId(processDefineId);

        List<Task> tasks = taskQuery
                .taskCandidateOrAssigned(userId)
                .orderByTaskCreateTime().asc().listPage(page - 1, size);

        long count = taskQuery.count();

        return PageResult.ok(tasks,page,size,(int)count);
    }


    /**
     * 我发起的流程
     * @param userId 用户id
     * @param finished 是否结束
     * @param page 页 从1开始
     * @param size 页大小
     * @return
     */
    @GetMapping("/process/myProcess/page")
    public PageResult<HistoricProcessInstance> myProcessTask(@RequestParam(value = "userId") String userId,
                                                             @RequestParam(value = "finished") Boolean finished,
                                           @RequestParam(value = "page",defaultValue = "1") Integer page,
                                           @RequestParam(value = "size",defaultValue = "10") Integer size) {

        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery();

        //流程是否结束
        historicProcessInstanceQuery = finished ? historicProcessInstanceQuery.finished() : historicProcessInstanceQuery.unfinished();

        List<HistoricProcessInstance> historicProcessInstances = historicProcessInstanceQuery
                .startedBy(userId)
                .listPage(page - 1, size);

        long count = historicProcessInstanceQuery.count();
        return PageResult.ok(historicProcessInstances,page,size,(int)count);
    }

    /**
     * 我处理的
     * @param userId
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/task/myComplete/page")
    public PageResult<HistoricTaskInstance> myCompleteTask(@RequestParam(value = "userId") String userId,
                                                             @RequestParam(value = "page",defaultValue = "1") Integer page,
                                                             @RequestParam(value = "size",defaultValue = "10") Integer size) {

        // 查询已经结束的task任务,切由userid处理
        HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery();
        List<HistoricTaskInstance> historicTaskInstances = historicTaskInstanceQuery.taskAssignee(userId).finished().listPage(page - 1, size);

        long count = historicTaskInstanceQuery.count();
        return PageResult.ok(historicTaskInstances,page,size,(int)count);
    }




}
