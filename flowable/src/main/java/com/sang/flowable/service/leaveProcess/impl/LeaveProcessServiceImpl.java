package com.sang.flowable.service.leaveProcess.impl;

import cn.hutool.core.collection.CollUtil;
import com.sang.common.constants.FlowableStatusEnum;
import com.sang.common.domain.leaveProcess.entity.LeaveProcess;
import com.sang.common.domain.leaveProcess.param.LeaveProcessQry;
import com.sang.common.domain.leaveProcess.repo.LeaveProcessRepository;
import com.sang.common.utils.SnowIdUtils;
import com.sang.flowable.service.leaveProcess.LeaveProcessService;
import com.sang.flowable.service.flowable.impl.FlowableBaseService;
import io.ebean.PagedList;
import io.ebean.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.FlowNode;
import org.flowable.bpmn.model.SequenceFlow;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.impl.util.ExecutionGraphUtil;
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
    public LeaveProcess moveActivityBusinessProcessing(LeaveProcess param, String taskDefinitionKey,String executionId, String action) {

        String newActivityId = "";
        if (FlowableStatusEnum.ACTION_RETREAT.getCode().equals(action)) {
            // 退回获取上一个节点
            BpmnModel bpmnModel = repositoryService.getBpmnModel(param.getProcessDefinitionId());
            FlowNode flowElement = (FlowNode)bpmnModel.getFlowElement(taskDefinitionKey);
            List<SequenceFlow> incomingFlows = flowElement.getIncomingFlows();
            SequenceFlow sequenceFlow = incomingFlows.get(0);
            newActivityId = sequenceFlow.getSourceRef();
        } else if (FlowableStatusEnum.ACTION_REJECT.getCode().equals(action)) {
            // 退回第一个节点
            BpmnModel bpmnModel = repositoryService.getBpmnModel(param.getProcessDefinitionId());
            FlowNode flowElement = (FlowNode)bpmnModel.getFlowElement(taskDefinitionKey);
            List<SequenceFlow> incomingFlows = flowElement.getIncomingFlows();
            SequenceFlow last = CollUtil.getLast(incomingFlows);
            newActivityId = last.getSourceRef();
        }

        // 判断目标节点是否能走到当前节点
//        ExecutionGraphUtil.isReachable()

        moveActivityByExecution(param,param.getProcessInstanceId(),executionId,newActivityId);
        return null;
    }

    @Override
    public LeaveProcess completeTaskBusinessProcessing(LeaveProcess leaveProcess, String taskId) {
        completeTask(leaveProcess,taskId);
        return leaveProcess;
    }

    @Override
    public Boolean deleteProcessInstanceBusinessProcessing(LeaveProcess param) {
        deleteProcessInstance(param,param.getProcessInstanceId(),"test");
        param.delete();
        return true;
    }
}
