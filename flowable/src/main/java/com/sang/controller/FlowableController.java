package com.sang.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.smallbun.screw.core.util.CollectionUtils;
import com.sang.common.constants.FlowableConst;
import com.sang.common.constants.StringConst;
import com.sang.common.domain.auth.authentication.user.repo.UserGroupRepository;
import com.sang.common.domain.auth.authentication.user.repo.UserRepository;
import com.sang.common.domain.flowable.dto.FlowableTaskInfoDto;
import com.sang.common.response.PageResult;
import com.sang.common.response.Result;
import com.sang.common.utils.ResponseUtil;
import com.sang.dto.ProcessDefinitionDto;
import com.sang.param.SuspendedActiveParam;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.*;
import org.flowable.bpmn.model.Process;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.impl.db.SuspensionState;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricProcessInstanceQuery;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.idm.api.Group;
import org.flowable.idm.api.GroupQuery;
import org.flowable.idm.api.User;
import org.flowable.idm.api.UserQuery;
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
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Flow;
import java.util.stream.Collectors;

@Slf4j
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

    @Resource
    private UserRepository userRepository;

    @Resource
    private UserGroupRepository userGroupRepository;

    @Resource
    private RuntimeService runtimeService;

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
            processDefinitionQuery.processDefinitionNameLike(StringConst.PERCENT_SIGN.concat(name).concat(StringConst.PERCENT_SIGN));

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
                .peek(item -> item.setProcessDisposePath(getMainProcessExtendParamByName(item))) // 设置流程处理页面路径
                .collect(Collectors.toList());

        long count = processDefinitionQuery.count();

        return PageResult.ok(processDefinitions,pageNumber,pageSize,(int) count);
    }

    @GetMapping("/process/test")
    public void test () {
        getCustomProperty("Activity_0bu60pr","Process_1111:15:fa7c76e8-1bdc-11ee-85cd-e2d4e83f9995","bbbb");
    }


    public FlowElement getFlowElementByActivityIdAndProcessDefinitionId(String taskDefinedKey, String processDefinitionId) {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        List<Process> processes = bpmnModel.getProcesses();
        if (CollectionUtils.isNotEmpty(processes)) {
            for (Process process : processes) {
                FlowElement flowElement = process.getFlowElement(taskDefinedKey, true);
                if (Objects.nonNull(flowElement)) {
                    return flowElement;
                }
            }
        }
        return null;
    }

    public List<ExtensionElement> getCustomProperty(String taskDefinedKey, String processDefinitionId, String customPropertyName) {
        FlowElement flowElement = this.getFlowElementByActivityIdAndProcessDefinitionId(taskDefinedKey, processDefinitionId);
        if (flowElement != null && flowElement instanceof UserTask) {
            UserTask userTask = (UserTask) flowElement;
            Map<String, List<ExtensionElement>> extensionElements = userTask.getExtensionElements();
            if (MapUtil.isNotEmpty(extensionElements)) {
                List<ExtensionElement> values = extensionElements.get(customPropertyName);
                if (CollectionUtils.isNotEmpty(values)) {
                    return values;
                }
            }
        }
        return null;
    }


    /**
     * 部署流程
     * @param file 文件
     * @param resourceName 资源名称
     * @param name 流程名称
     * @return
     * @throws IOException
     */
    @PutMapping("/deployProcess")
    public Result deployProcess(@RequestParam("file") MultipartFile file
                                ,@RequestParam("resourceName") String resourceName
                                ,@RequestParam("name") String name
                                ) throws IOException {

        if (!StrUtil.endWith(file.getOriginalFilename(), FlowableConst.BPMN_20_XML)) {
             throw new FlowableException("流程定义文件后缀错误");
        }
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
        ResponseUtil.sendStream(resourceAsStream, StringConst.APPLICATION_XML, FlowableConst.PROCESS_DEFINE_XML);
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
    public PageResult<FlowableTaskInfoDto> todoTaskByUser(@RequestParam(value = "userId") String userId,
                                     @RequestParam(value = "processDefineId",required = false) String processDefineId,
                                     @RequestParam(value = "pageNumber",defaultValue = "1") Integer pageNumber,
                                     @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize) {
        TaskQuery taskQuery = taskService.createTaskQuery();

        if (StrUtil.isNotBlank(processDefineId))
            taskQuery.processDefinitionId(processDefineId);

        List<Task> tasks = taskQuery
                .taskCandidateOrAssigned(userId)
                .orderByTaskCreateTime().asc().listPage(pageNumber - 1, pageSize);

        List<FlowableTaskInfoDto> taskDtos = tasks.stream()
            .map(item -> BeanUtil.copyProperties(item, FlowableTaskInfoDto.class))
            .peek(this::setTaskExtendField).collect(Collectors.toList());


        long count = taskQuery.count();

        return PageResult.ok(taskDtos,pageNumber,pageSize,(int)count);
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

    /**
     * 用户列表
     * @param name 名字 模糊匹配
     * @return
     */
    @GetMapping("/user/list")
    public Result<List<User>> userList(@RequestParam(value = "name") String name) {

        UserQuery userQuery = identityService.createUserQuery();
        if (StrUtil.isNotBlank(name)) {
            userQuery.userDisplayNameLike(StringConst.PERCENT_SIGN.concat(name).concat(StringConst.PERCENT_SIGN));
        }

        return Result.ok(userQuery.list());
    }

    /**
     * 用户列表
     * @param name 名字 模糊匹配
     * @return
     */
    @GetMapping("/group/list")
    public Result<List<Group>> groupList(@RequestParam(value = "name") String name) {

        GroupQuery groupQuery = identityService.createGroupQuery();
        if (StrUtil.isNotBlank(name)) {
            groupQuery.groupNameLikeIgnoreCase(StringConst.PERCENT_SIGN.concat(name).concat(StringConst.PERCENT_SIGN));
        }

        return Result.ok(groupQuery.list());
    }


    /**
     * 同步系统\flowable用户
     * @return
     */
    @GetMapping("/user/sync")
    public Result syncUsers(@RequestParam(value = "username") String username) {

        if (StrUtil.isNotBlank(username)) {
            updateUser(userRepository.userinfo(username));
            return Result.ok();
        }

        userGroupRepository.findAll().forEach(userGroup -> {
            Group group = identityService.createGroupQuery().groupId(userGroup.getGroupCode()).singleResult();

            group = group == null ? identityService.newGroup(userGroup.getGroupCode()) : group;
            // 创建Group对象并指定相关的信息
            group.setName(userGroup.getGroupName());
            group.setType(userGroup.getParentId() != null ? userGroup.getParentId().getGroupCode():null);
            // 创建或者更新Group对应的表结构数据
            identityService.saveGroup(group);
        });

        userRepository.findAll().forEach(this::updateUser);
        return Result.ok();
    }

    private void updateUser(com.sang.common.domain.auth.authentication.user.entity.User userinfo) {
        // 通过 IdentityService 完成相关的用户和组的管理
        User user = identityService.createUserQuery().userId(userinfo.getUsername()).singleResult();
        user = user == null ? identityService.newUser(userinfo.getUsername()) : user;
        user.setId(userinfo.getUsername());
        user.setDisplayName(userinfo.getName());
        identityService.saveUser(user);

        // 将用户分配给对应的组
        if (userinfo.getUserGroup() != null) {
            identityService.deleteMembership(user.getId(),userinfo.getUserGroup().getGroupCode());
            identityService.createMembership(user.getId(),userinfo.getUserGroup().getGroupCode());
        }
    }

    /**
     * 获取流程扩展参数
     * @param item
     * @return
     */
    private String getMainProcessExtendParamByName(ProcessDefinitionDto item) {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(item.getId());
        Process mainProcess = bpmnModel.getMainProcess();
        Map<String, List<ExtensionAttribute>> attributes = mainProcess.getAttributes();
        if (MapUtil.isNotEmpty(attributes)) {
            List<ExtensionAttribute> extensionAttributes = attributes.get(FlowableConst.PROCESS_DISPOSE_PATH);
            if (CollUtil.isNotEmpty(extensionAttributes)) {
                return extensionAttributes.get(0).getValue();
            }
        }

        return null;
    }

    /**
     * 设置task相关扩展字段
     * @param item
     */
    private void setTaskExtendField(FlowableTaskInfoDto item) {

        BpmnModel bpmnModel = repositoryService.getBpmnModel(item.getProcessDefinitionId());
        Process mainProcess = bpmnModel.getMainProcess();
        // 流程定义相关字段
        if (CollUtil.isNotEmpty(mainProcess.getAttributes().get(FlowableConst.PROCESS_DISPOSE_PATH)))
            item.setProcessDisposePath(mainProcess.getAttributes().get(FlowableConst.PROCESS_DISPOSE_PATH).get(0).getValue());

        // task定义相关字段
        FlowElement flowElement = mainProcess.getFlowElement(item.getTaskDefinitionKey(), true);
        Map<String, List<ExtensionAttribute>> attributes = flowElement.getAttributes();

        if (CollUtil.isNotEmpty(attributes.get(FlowableConst.IS_BATCH_APPROVAL)))
            item.setIsBatchApproval(Boolean.valueOf(attributes.get(FlowableConst.IS_BATCH_APPROVAL).get(0).getValue()));

        if (CollUtil.isNotEmpty(attributes.get(FlowableConst.TASK_DISPOSE_PATH)))
            item.setTaskDisposePath(attributes.get(FlowableConst.TASK_DISPOSE_PATH).get(0).getValue());

        // 流程实例相关字段
        List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().processInstanceId(item.getProcessInstanceId()).list();
        item.setBusinessId(list.get(0).getBusinessKey());
        item.setBusinessStatus(list.get(0).getBusinessStatus());

        item.setStartUserId(list.get(0).getStartUserId());
        item.setStartTime(list.get(0).getStartTime());
        item.setProcessName(list.get(0).getProcessDefinitionName());

        List<User> userList = identityService.createUserQuery().userId(list.get(0).getStartUserId()).list();
        if (CollUtil.isNotEmpty(userList))
            item.setStartUserName(userList.get(0).getDisplayName());
    }
}
