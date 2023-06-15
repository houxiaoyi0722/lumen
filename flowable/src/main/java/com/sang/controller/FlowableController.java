package com.sang.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.sang.common.response.PageResult;
import com.sang.dto.ProcessDefinitionDto;
import org.flowable.engine.HistoryService;
import org.flowable.engine.IdentityService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricProcessInstanceQuery;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.flowable.idm.api.Group;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.api.history.HistoricTaskInstanceQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import static cn.hutool.core.util.CharsetUtil.UTF_8;
import static cn.hutool.jwt.JWTHeader.CONTENT_TYPE;
import static org.apache.tomcat.util.http.fileupload.FileUploadBase.CONTENT_DISPOSITION;

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

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse httpServletResponse = requestAttributes.getResponse();
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        try {
            // 设置信息给客户端不解析
            // 设置CONTENT_TYPE，即告诉客户端所发送的数据属于什么类型
            httpServletResponse.setHeader(CONTENT_TYPE, "application/xml");

            // 设置扩展头，当Content-Type 的类型为要下载的类型时 , 这个信息头会告诉浏览器这个文件的名字和类型。
            httpServletResponse.setCharacterEncoding(UTF_8);
            httpServletResponse.setHeader("Content-Disposition", StrUtil.format("attachment;filename={}", URLEncoder.encode("aaa", StandardCharsets.UTF_8)));
            IoUtil.copy(resourceAsStream, outputStream);
        } finally {
            if (resourceAsStream != null) {
                IoUtil.close(resourceAsStream);
            }
            if (outputStream != null) {
                IoUtil.close(outputStream);
            }
        }
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
