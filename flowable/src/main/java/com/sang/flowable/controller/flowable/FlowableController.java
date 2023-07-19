package com.sang.flowable.controller.flowable;

import com.sang.common.domain.flowable.dto.FlowableTaskInfoDto;
import com.sang.common.response.PageResult;
import com.sang.flowable.dto.ProcessDefinitionDto;
import com.sang.flowable.service.flowable.FlowableService;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RequestMapping("/flowable")
@RestController
public class FlowableController {


    @Resource
    private FlowableService flowableService;

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
            @RequestParam(value = "latestVersion",required = false) Boolean latestVersion,
            @RequestParam(value = "pageNumber",defaultValue = "1") Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize) {
        return flowableService.getProcessDefinitionDtoPageResult(name, startBy, active, latestVersion, pageNumber, pageSize);
    }

    /**
     * 我的待办
     * @param processDefineId 流程定义id
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GetMapping("/task/todo/page")
    public PageResult<FlowableTaskInfoDto> todoTaskByUser(@RequestParam(value = "processDefineId",required = false) String processDefineId,
                                     @RequestParam(value = "pageNumber",defaultValue = "1") Integer pageNumber,
                                     @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return flowableService.getFlowableTaskInfoDtoPageResult(authentication.getPrincipal().toString(), processDefineId, pageNumber, pageSize);
    }



    /**
     * 我发起的流程
     * @param processDefineId 流程定义id
     * @param finished 是否结束
     * @param pageNumber 页 从1开始
     * @param pageSize 页大小
     * @return
     */
    @GetMapping("/process/myProcess/page")
    public PageResult<HistoricProcessInstance> myProcessTask(@RequestParam(value = "processDefineId",required = false) String processDefineId,
                                            @RequestParam(value = "finished") Boolean finished,
                                            @RequestParam(value = "pageNumber",defaultValue = "1") Integer pageNumber,
                                            @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return flowableService.getHistoricProcessInstancePageResult(authentication.getPrincipal().toString(), finished, pageNumber, pageSize);
    }

    /**
     * 我处理的
     * @param processDefineId 流程定义id
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GetMapping("/task/myComplete/page")
    public PageResult<HistoricTaskInstance> myCompleteTask(@RequestParam(value = "processDefineId",required = false) String processDefineId,
                                                           @RequestParam(value = "page",defaultValue = "1") Integer pageNumber,
                                                           @RequestParam(value = "size",defaultValue = "10") Integer pageSize) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return flowableService.getHistoricTaskInstancePageResult(authentication.getPrincipal().toString(), pageNumber, pageSize);
    }

}
