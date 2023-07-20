package com.sang.flowable.listener;

import cn.hutool.extra.spring.SpringUtil;
import com.sang.common.constants.FlowableStatusEnum;
import com.sang.common.domain.leaveProcess.entity.LeaveProcess;
import com.sang.flowable.service.leaveProcess.LeaveProcessService;
import io.ebean.annotation.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.impl.el.FixedValue;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 自定义的监听器
 */
@Component
@Slf4j
public class LeaveProcessTaskEndListener implements ExecutionListener {

    private FixedValue test;

    private RuntimeService runtimeService = SpringUtil.getBean(RuntimeService.class);
    private LeaveProcessService leaveProcessService = SpringUtil.getBean(LeaveProcessService.class);

    @Transactional
    @Override
    public void notify(DelegateExecution execution) {

        runtimeService.updateBusinessStatus(execution.getProcessInstanceId(), FlowableStatusEnum.APPROVED.getCode());

        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(execution.getProcessInstanceId()).singleResult();

        LeaveProcess one = leaveProcessService.findOne(Long.valueOf(processInstance.getBusinessKey()));
        one.setStatus(FlowableStatusEnum.APPROVED.getCode());
        one.update();
    }
}
