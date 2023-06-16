package com.sang.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.sang.common.constants.StringConst;
import com.sang.common.domain.storage.dto.StorageDto;
import com.sang.common.response.PageResult;
import com.sang.common.response.Result;
import com.sang.common.utils.ResponseUtil;
import com.sang.dto.ProcessDefinitionDto;
import com.sang.param.SuspendedActiveParam;
import io.minio.errors.MinioException;
import org.flowable.common.engine.impl.db.SuspensionState;
import org.flowable.engine.HistoryService;
import org.flowable.engine.IdentityService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricProcessInstanceQuery;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.flowable.idm.api.Group;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.api.history.HistoricTaskInstanceQuery;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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
     * @param pageNumber
     * @param pageSize
     * @return PageResult
     */
    @GetMapping("/process/page")
    public PageResult<ProcessDefinitionDto> processDefinitionPageList(
            @RequestParam(value = "name",required = false) String name,
            @RequestParam(value = "startBy",required = false) String startBy,
            @RequestParam(value = "active",required = false) Boolean active,
            @RequestParam(value = "pageNumber",defaultValue = "1") Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize) {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();

        if (StrUtil.isNotBlank(name))
            processDefinitionQuery.processDefinitionNameLike("%".concat(name).concat("%"));

        if (StrUtil.isNotBlank(startBy)) {
            List<Group> list = identityService.createGroupQuery().groupMember(startBy).list();
            processDefinitionQuery.startableByUserOrGroups(startBy,list.stream().map(Group::getId).collect(Collectors.toList()));
        }

        if(active != null) {
            processDefinitionQuery = (active) ? processDefinitionQuery.active() : processDefinitionQuery.suspended();
        }

        List<ProcessDefinitionDto> processDefinitions = processDefinitionQuery
                .latestVersion()
                .listPage(pageNumber - 1, pageSize).stream()
                .map(item -> BeanUtil.copyProperties(item, ProcessDefinitionDto.class))
                .collect(Collectors.toList());

        long count = processDefinitionQuery.count();

        return PageResult.ok(processDefinitions,pageNumber,pageSize,(int) count);
    }

    @PutMapping("/deployProcess")
    public Result deployProcess(@RequestParam("file") MultipartFile file
                                ,@RequestParam("resourceName") String resourceName
                                ,@RequestParam("name") String name
                                ) throws IOException {

        // 部署流程 获取RepositoryService对象
        Deployment deployment = repositoryService.createDeployment()// 创建Deployment对象
                .addInputStream(resourceName,file.getInputStream())// 添加流程部署文件
                .name(name) // 设置部署流程的名称
                .deploy(); // 执行部署操作
        return Result.ok(deployment.getId());
    }


    /**
     * 挂起或者激活流程
     * @param suspendedActiveParam
     */
    @PutMapping("/suspensionState")
    public Result<String> suspendedOrActiveProcess(@Validated @RequestBody SuspendedActiveParam suspendedActiveParam) {
        // 表示被挂起
        if(SuspensionState.SUSPENDED.getStateCode() == suspendedActiveParam.getSuspensionState()) {
            // 激活流程定义
            repositoryService.activateProcessDefinitionById(suspendedActiveParam.getProcessDefinitionId(),true,null);
        // 表示激活状态
        }else if (SuspensionState.ACTIVE.getStateCode() == suspendedActiveParam.getSuspensionState()){
            // 挂起流程
            repositoryService.suspendProcessDefinitionById(suspendedActiveParam.getProcessDefinitionId(),true,null);
        }
        return Result.ok();
    }

    /**
     * 级联删除流程
     * @param deploymentId
     * @return
     */
    @DeleteMapping("/deleteProcess")
    public Result deleteProcess(@RequestParam(value = "deploymentId") @NotNull(message = "deploymentId不能为空") String deploymentId) {
        repositoryService.deleteDeployment(deploymentId,true);
        return Result.ok();
    }

    /**
     * 获取流程定义xml文件
     * @param deploymentId 部署id
     * @param resourceName 资源名称
     * @throws IOException
     */
    @GetMapping("/processXmlResource")
    public void processXmlResourceFile(
            @RequestParam(value = "deploymentId",required = false) String deploymentId,
            @RequestParam(value = "resourceName",required = false) String resourceName
    ) throws IOException {
        InputStream resourceAsStream = repositoryService.getResourceAsStream(deploymentId,resourceName);
        ResponseUtil.sendStream(resourceAsStream, StringConst.APPLICATION_XML, StringConst.PROCESS_DEFINE_XML);
    }

    /**
     * 我的待办
     * @param userId 用户id
     * @param processDefineId 流程定义id
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GetMapping("/task/todo/page")
    public PageResult<Task> todoTaskByUser(@RequestParam(value = "userId") String userId,
                                     @RequestParam(value = "processDefineId",required = false) String processDefineId,
                                     @RequestParam(value = "pageNumber",defaultValue = "1") Integer pageNumber,
                                     @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize) {
        TaskQuery taskQuery = taskService.createTaskQuery();

        if (StrUtil.isNotBlank(processDefineId))
            taskQuery.processDefinitionId(processDefineId);

        List<Task> tasks = taskQuery
                .taskCandidateOrAssigned(userId)
                .orderByTaskCreateTime().asc().listPage(pageNumber - 1, pageSize);

        long count = taskQuery.count();

        return PageResult.ok(tasks,pageNumber,pageSize,(int)count);
    }


    /**
     * 我发起的流程
     * @param userId 用户id
     * @param finished 是否结束
     * @param pageNumber 页 从1开始
     * @param pageSize 页大小
     * @return
     */
    @GetMapping("/process/myProcess/page")
    public PageResult<HistoricProcessInstance> myProcessTask(@RequestParam(value = "userId") String userId,
                                                             @RequestParam(value = "finished") Boolean finished,
                                           @RequestParam(value = "pageNumber",defaultValue = "1") Integer pageNumber,
                                           @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize) {

        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery();

        //流程是否结束
        historicProcessInstanceQuery = finished ? historicProcessInstanceQuery.finished() : historicProcessInstanceQuery.unfinished();

        List<HistoricProcessInstance> historicProcessInstances = historicProcessInstanceQuery
                .startedBy(userId)
                .listPage(pageNumber - 1, pageSize);

        long count = historicProcessInstanceQuery.count();
        return PageResult.ok(historicProcessInstances,pageNumber,pageSize,(int)count);
    }

    /**
     * 我处理的
     * @param userId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GetMapping("/task/myComplete/page")
    public PageResult<HistoricTaskInstance> myCompleteTask(@RequestParam(value = "userId") String userId,
                                                             @RequestParam(value = "page",defaultValue = "1") Integer pageNumber,
                                                             @RequestParam(value = "size",defaultValue = "10") Integer pageSize) {

        // 查询已经结束的task任务,切由userid处理
        HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery();
        List<HistoricTaskInstance> historicTaskInstances = historicTaskInstanceQuery.taskAssignee(userId).finished().listPage(pageNumber - 1, pageSize);

        long count = historicTaskInstanceQuery.count();
        return PageResult.ok(historicTaskInstances,pageNumber,pageSize,(int)count);
    }




}
