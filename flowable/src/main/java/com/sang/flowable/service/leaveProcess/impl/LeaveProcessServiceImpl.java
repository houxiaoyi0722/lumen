package com.sang.flowable.service.leaveProcess.impl;

import cn.hutool.core.collection.CollUtil;
import com.sang.common.constants.FlowableStatusEnum;
import com.sang.common.domain.flowable.dto.FlowableTaskVariableDto;
import com.sang.common.domain.leaveProcess.dto.LeaveProcessDto;
import com.sang.common.domain.leaveProcess.entity.LeaveProcess;
import com.sang.common.domain.leaveProcess.param.LeaveProcessQry;
import com.sang.common.domain.leaveProcess.repo.LeaveProcessRepository;
import com.sang.common.utils.SnowIdUtils;
import com.sang.flowable.service.leaveProcess.LeaveProcessService;
import com.sang.flowable.service.flowable.impl.FlowableBaseService;
import io.ebean.PagedList;
import io.ebean.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.*;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 请假流程
 * 请假流程
 * hxy 2023-06-09 14:32:57
 */
@Slf4j
@Service
public class LeaveProcessServiceImpl extends FlowableBaseService<LeaveProcess> implements LeaveProcessService {

    @Resource
    private LeaveProcessRepository repository;

    @Resource
    private RepositoryService repositoryService;

    @Override
    public PagedList<LeaveProcess> leaveProcessList(LeaveProcessQry qry) {
        return repository.getPage(qry);
    }

    @Override
    public LeaveProcess findOne(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public void save(LeaveProcess leaveProcess) {
        leaveProcess.save();
    }

    @Override
    @Transactional
    public void insert(LeaveProcess leaveProcess) {
        leaveProcess.insert();
    }

    @Override
    @Transactional
    public void update(LeaveProcess leaveProcess) {
        leaveProcess.update();
    }

    @Override
    @Transactional
    public void delete(LeaveProcess leaveProcess) {
        repository.delete(leaveProcess);
    }

    @Override
    @Transactional
    public void saveAll(List<LeaveProcess> LeaveProcesss) {
        repository.saveAll(LeaveProcesss);
    }

    @Override
    @Transactional
    public void deleteAll(List<LeaveProcess> leaveProcesss) {
        repository.deleteAll(leaveProcesss);
    }

    @Override
    public LeaveProcess startBusinessProcessing(LeaveProcess leaveProcess) {

        leaveProcess.setId(SnowIdUtils.uniqueLong());
        // 发起流程
        ProcessInstance processInstance = startProcessById(leaveProcess,leaveProcess.getProcessDefinitionId());
        // 插入流程实例id
        leaveProcess.setProcessInstanceId(processInstance.getProcessInstanceId());
        /// 保存数据
        leaveProcess.setStatus(FlowableStatusEnum.APPROVAL.getCode());
        leaveProcess.insert();
        return leaveProcess;
    }

    @Override
    public void moveActivityBusinessProcessing(LeaveProcessDto leaveProcessDto) {

        String newActivityId = "";
        if (FlowableStatusEnum.ACTION_RETREAT.getCode().equals(leaveProcessDto.getAction())) {
            // 退回获取上一个节点
            BpmnModel bpmnModel = repositoryService.getBpmnModel(leaveProcessDto.getProcessDefinitionId());
            FlowNode flowElement = (FlowNode)bpmnModel.getFlowElement(leaveProcessDto.getTaskDefinitionKey());
            List<SequenceFlow> incomingFlows = flowElement.getIncomingFlows();
            SequenceFlow sequenceFlow = incomingFlows.get(0);
            String sourceRef = sequenceFlow.getSourceRef();
            FlowNode task = (FlowNode)bpmnModel.getFlowElement(sourceRef);
            if (task instanceof Task || task instanceof StartEvent) {// 如果上一个节点是处理节点，获取id 非处理节点再向上找 todo 改递归
                newActivityId = task.getId();
            } else {
                newActivityId = task.getIncomingFlows().get(0).getSourceRef();
            }

        } else if (FlowableStatusEnum.ACTION_REJECT.getCode().equals(leaveProcessDto.getAction())) {
            // 退回第一个节点 todo 递归寻找节点 目前有bug
            BpmnModel bpmnModel = repositoryService.getBpmnModel(leaveProcessDto.getProcessDefinitionId());
            FlowNode flowElement = (FlowNode)bpmnModel.getFlowElement(leaveProcessDto.getTaskDefinitionKey());
            List<SequenceFlow> incomingFlows = flowElement.getIncomingFlows();
            SequenceFlow last = CollUtil.getLast(incomingFlows);
            newActivityId = last.getSourceRef();
        }

        // 判断目标节点是否能走到当前节点
//        ExecutionGraphUtil.isReachable()

        moveActivityByExecution(
                FlowableTaskVariableDto
                        .builder()
                        .action(leaveProcessDto.getAction())
                        .actionReason(leaveProcessDto.getActionReason())
                        .build()
                ,leaveProcessDto.getProcessInstanceId(),leaveProcessDto.getExecutionId(),newActivityId
        );
    }

    @Override
    public void completeTaskBusinessProcessing(LeaveProcessDto leaveProcessDto) {
        completeTask(
                FlowableTaskVariableDto
                        .builder()
                        .action(leaveProcessDto.getAction())
                        .actionReason(leaveProcessDto.getActionReason())
                        .build(),
                leaveProcessDto.getTaskId()
        );
    }

    @Override
    public Boolean deleteProcessInstanceBusinessProcessing(LeaveProcess leaveProcess, LeaveProcessDto leaveProcessDto) {
        deleteProcessInstance(leaveProcessDto.getProcessInstanceId(),leaveProcessDto.getActionReason());
        leaveProcess.delete();
        return true;
    }
}
