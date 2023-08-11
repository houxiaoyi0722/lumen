package com.sang.flowable.service.leaveProcess;

import com.sang.common.domain.leaveProcess.dto.LeaveProcessDto;
import com.sang.common.domain.leaveProcess.entity.LeaveProcess;
import com.sang.common.domain.leaveProcess.param.LeaveProcessQry;
import com.sang.flowable.service.flowable.FlowableBaseInterface;
import io.ebean.PagedList;

import java.util.List;

/**
 * 请假流程
 * 请假流程
 *
 * hxy 2023-06-09 14:32:57
 */
public interface LeaveProcessService extends FlowableBaseInterface<LeaveProcess> {

    PagedList<LeaveProcess> leaveProcessList(LeaveProcessQry qry);

    LeaveProcess findOne(Long id);

    void save(LeaveProcess leaveProcess);

    void saveAll(List<LeaveProcess> leaveProcesss);

    void insert(LeaveProcess leaveProcess);

    void update(LeaveProcess leaveProcess);

    void delete(LeaveProcess leaveProcess);

    void deleteAll(List<LeaveProcess> leaveProcesss);

    void moveActivityBusinessProcessing(LeaveProcessDto param);

    void completeTaskBusinessProcessing(LeaveProcessDto leaveProcess);

    Boolean deleteProcessInstanceBusinessProcessing(LeaveProcess leaveProcess, LeaveProcessDto leaveProcessDto);
}
