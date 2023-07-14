package com.sang.service.leaveProcess.impl;

import com.sang.common.domain.leaveProcess.entity.LeaveProcess;
import com.sang.common.domain.leaveProcess.param.LeaveProcessQry;
import com.sang.common.domain.leaveProcess.repo.LeaveProcessRepository;
import com.sang.service.base.FlowableBaseService;
import com.sang.service.leaveProcess.LeaveProcessService;
import io.ebean.PagedList;
import io.ebean.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
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

        leaveProcess.save();
        ProcessInstance processInstance = startProcessById(
                leaveProcess
                ,leaveProcess.getProcessDefinitionId()
        );
        leaveProcess.setProcessInstanceId(processInstance.getProcessInstanceId());
        leaveProcess.update();
        return leaveProcess;
    }

}
